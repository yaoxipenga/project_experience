package com.medcaptain.productservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.FileType;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.constant.CommonConstant;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.DevicelogMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceuploadfileMapper;
import com.medcaptain.productservice.entity.deivce.JsonData;
import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import com.medcaptain.productservice.entity.mybatis.Deviceuploadfile;
import com.medcaptain.productservice.feign.FileDFSClientFeign;
import com.medcaptain.productservice.feign.ProductTopicService;
import com.medcaptain.productservice.redis.RedisOption;
import com.medcaptain.productservice.redis.entity.FileUploadTokenEntity;
import com.medcaptain.productservice.service.LogFileService;
import com.medcaptain.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class LogFileServiceImpl implements LogFileService {
    @Autowired
    private ProductTopicService productTopicService;

    @Autowired
    private DevicelogMapper devicelogMapper;

    @Autowired
    private DeviceinfoMapper deviceinfoMapper;

    @Autowired
    private DeviceuploadfileMapper deviceuploadfileMapper;

    @Autowired
    private FileDFSClientFeign fileDFSClientFeign;

    @Override
    public RestResult getDeviceLogs(String productKey, String deviceName, Integer page, Integer size) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        Integer deviceId = deviceinfo.getDeviceTripleId();
        returnData.put("deviceLogList", devicelogMapper.selectByKeyAndName(productKey, deviceId, (page - 1) * size, size));
        returnData.put("total", devicelogMapper.countByProductKeyAndDeviceName(productKey, deviceId));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public RestResult getUploadLogs(Integer userId, String productKey, String deviceName, Integer page, Integer size) {
        Integer start = (page - 1) * size;
        Integer deviceId = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName).getDeviceTripleId();
        Map<String, Object> returnData = new HashMap<String, Object>();
        //放token
        List<Map<String, Object>> tempList = deviceuploadfileMapper.selectLogFileList(deviceId, userId, start, size);
//        for (Map<String, Object> logMap : tempList) {
//            String token = UUIDUtil.getUUID();
//            fileDFSClientFeign.downloadFile(token, logMap.get("fileName").toString(), logMap.get("localPath").toString());
//            logMap.put("token", token);
//        }
        returnData.put("logList", tempList);
        returnData.put("total", deviceuploadfileMapper.countByUserIdAndDeviceId(deviceId, userId));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public RestResult getLogs(String productKey, String deviceName, Integer type) {
        String returnmessage = null;

        String topic = "/file/device/query/" + productKey + "/" + deviceName;
        JsonData message = new JsonData();
        Integer deviceId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        if (null == deviceId)
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        message.setId(RedisOption.increment(CommonConstant.DEVICE_MESSAGE_ID + deviceId));
        message.setVersion("1.0.0");

        Map params = new HashMap();
        params.put("type", type);
        String params_string = JSON.toJSONString(params);
        message.setParams(JSONObject.parseObject(params_string));

        Long timestamp = System.currentTimeMillis();
        message.setTimestamp(timestamp.toString());

        String replyTopic = "/file/device/post/" + productKey + "/" + deviceName;
        RestResult deviceResponse = productTopicService.issueOrders(topic, JSON.toJSONString(message), replyTopic);
        if (deviceResponse.getMsg().equals(HttpResponseCode.ERROR_LOG_IS_OBTAINING.getMsg()))
            throw new ApiRuntimeException(HttpResponseCode.ERROR_LOG_IS_OBTAINING, null);
        returnmessage = deviceResponse.getData().toString();

        if ("doing".equals(returnmessage)) throw new ApiRuntimeException(HttpResponseCode.ERROR_LOG_IS_OBTAINING, null);
        else if ("too late".equals(returnmessage))
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NO_RESPONSE, null);

        if (null == returnmessage)
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_NO_RESPONSE, null);
        try {
            JSONObject jsonObject = JSONObject.parseObject(returnmessage, JsonData.class).getParams();

            Map<String, Object> returnData = new HashMap<String, Object>();
            List<String> logFileList = (List<String>) jsonObject.get("files");
            List<Map> returnLogFileList = new ArrayList<>();
            Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
            for (String logFile : logFileList) {
                //判断是否已经同步过或正在同步该日志
                if (0 == deviceuploadfileMapper.getDeviceUploadFileStatus(deviceTripleId, logFile).size()) {
                    Map<String, Object> logMap = new HashMap<>();
                    logMap.put("fileName", logFile);
                    returnLogFileList.add(logMap);
                }
            }
            //TODO 分页
            returnData.put("logList", returnLogFileList);
            returnData.put("total", returnLogFileList.size());
            return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_ERROR_RETURN, null);
        }
    }

    @Override
    public RestResult getLogFile(String productKey, String deviceName, String fileName, Integer type, Integer userId) {
        //获取文件的name、md5、size信息
        String topic = "/file/device/fetch/" + productKey + "/" + deviceName;

        JsonData message = new JsonData();
        Integer deviceId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        if (null == deviceId)
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);

        message.setId(RedisOption.increment(CommonConstant.DEVICE_MESSAGE_ID + deviceId));
        message.setVersion("1.0.0");

        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("file", fileName);

        String params_string = JSON.toJSONString(params);
        message.setParams(JSONObject.parseObject(params_string));

        Long timestamp = System.currentTimeMillis();
        message.setTimestamp(timestamp.toString());

        String replyTopic = "/file/device/inform/" + productKey + "/" + deviceName;
        RestResult deviceResponse = productTopicService.issueOrders(topic, JSON.toJSONString(message), replyTopic);
        String fileMessage = deviceResponse.getData().toString();

        JSONObject jsonObject = JSON.parseObject(fileMessage, JsonData.class).getParams();

        //TODO 套用模板解析数据
        String md5 = jsonObject.getString("md5");
        Long size = jsonObject.getLong("size");
        String name = jsonObject.getString("name");
        Integer file_type = jsonObject.getInteger("type");

        String token = UUIDUtil.getUUID();

        FileUploadTokenEntity file = new FileUploadTokenEntity();
        file.setUserId(userId);
        file.setTime(new Date());
        file.setMd5(md5);
        file.setFileName(name);
        file.setCode(FileType.PRIVATE.getCode());

        RedisOption.setFileUploadInfo(token, file);

        //下发url
        topic = "/file/device/url/" + productKey + "/" + deviceName;
        message.setId(RedisOption.increment(CommonConstant.DEVICE_MESSAGE_ID + deviceId));

        message.setVersion("1.0.0");

        params.clear();
        params.put("type", type);
        params.put("file", fileName);
        params.put("url", CommonConstant.HOSTNAME + "/api/file/file?token=" + token + "&type=log");
        params.put("host", CommonConstant.HOST);
        params.put("port", CommonConstant.PORT);
        params.put("size", size);
        params_string = JSON.toJSONString(params);
        message.setParams(JSONObject.parseObject(params_string));

        timestamp = System.currentTimeMillis();
        message.setTimestamp(timestamp.toString());

        //将文件存入设备同步文件表中，设置状态为未完成
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        Deviceuploadfile deviceuploadfile = new Deviceuploadfile();
        deviceuploadfile.setFileMd5(md5);
        deviceuploadfile.setDeviceTripleId(deviceTripleId);
        deviceuploadfile.setIsDel(0);
        deviceuploadfile.setStatus(0);
        deviceuploadfile.setFileName(fileName);
        deviceuploadfile.setFileSize(size);
        deviceuploadfile.setUserId(userId);
        deviceuploadfileMapper.insertSelective(deviceuploadfile);

        //在redis中初始化同步进度
        RedisOption.initFileProgress(deviceTripleId, 0, fileName);

        productTopicService.publishMessage(topic, JSONObject.toJSONString(message), 0);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @Override
    public RestResult getAlarmlogs(String productKey, String deviceName, Integer page, Integer size) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public void synchronizeLog(String md5, String path, boolean successful) {
        //更新数据库状态
        deviceuploadfileMapper.updateByMd5(md5, path, successful);
        //删除redis中的升级记录
        Map<String, Object> fileInfo = deviceuploadfileMapper.getDeviceTripleIdAndDeviceNameByMd5(md5);
        Integer deviceTripleId = (int) fileInfo.get("deviceTripleId");
        String fileName = (String) fileInfo.get("fileName");
        RedisOption.cleanFileProgress(deviceTripleId, fileName);
    }

    @Override
    public Map<String, Object> getDeviceUploadProgress(String productKey, String deviceName) {
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        List<Map<String, Object>> returnList = RedisOption.getFileUploadProgress(deviceTripleId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("logList", returnList);
        returnMap.put("total", returnList.size());
        return returnMap;
    }

    @Override
    public Map<String, Object> getFileDownloadUrl(String productKey, String deviceName, String fileName) {
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        String path = deviceuploadfileMapper.getFilePath(deviceTripleId, fileName);
        String token = UUIDUtil.getUUID();
        fileDFSClientFeign.downloadFile(token, fileName, path);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("url", path);
        return resultMap;
    }
}
