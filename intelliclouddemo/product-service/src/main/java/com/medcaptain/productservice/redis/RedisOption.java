package com.medcaptain.productservice.redis;

import com.medcaptain.productservice.constant.CommonConstant;
import com.medcaptain.productservice.redis.entity.FileDownloadTokenEntity;
import com.medcaptain.productservice.redis.entity.FileInfoEntity;
import com.medcaptain.productservice.redis.entity.FileUploadTokenEntity;
import com.medcaptain.utils.JSONUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 文件服务器中对redis使用的方法类
 *
 * @author Adam.Chen
 */

@Component
public class RedisOption {
    /**
     * 载入模板供静态方法调用
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplateHelp;
    static private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    void init() {
        redisTemplate = redisTemplateHelp;
    }

    /**
     * 获取redis中存储的文件上传参数
     *
     * @param token
     * @return
     */
    static public FileUploadTokenEntity getFileUploadInfo(String token) {
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        String jsonString = redis.get("FileUploadToken:" + token);
        JSONObject jsonObject = JSONUtil.toJSONObject(jsonString);
        if (jsonObject == null) {
            return null;
        }
        FileUploadTokenEntity fileUploadTokenEntity = JSONUtil.toBean(jsonObject,
                FileUploadTokenEntity.class);
        return fileUploadTokenEntity;
    }

    /**
     * 获取redis中存储的文件下载参数
     *
     * @param token
     * @return
     */
    static public FileDownloadTokenEntity getFileDownloadInfo(String token) {
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        String jsonString = redis.get("FileDownloadToken:" + token);
        JSONObject jsonObject = JSONUtil.toJSONObject(jsonString);
        if (jsonObject == null) {
            return null;
        }
        FileDownloadTokenEntity fileDownloadTokenEntity = JSONUtil.toBean(jsonObject,
                FileDownloadTokenEntity.class);
        return fileDownloadTokenEntity;
    }

    /**
     * 设置redis中的文件上传参数
     *
     * @param token
     * @param data
     * @return
     */
    static public boolean setFileUploadInfo(String token, FileUploadTokenEntity data) {
        try {
            ValueOperations<String, String> redis = redisTemplate.opsForValue();
            String upload_token = JSONObject.fromObject(data).toString();
            redis.set("FileUploadToken:" + token, upload_token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置redis中的文件下载参数
     *
     * @param token
     * @param data
     * @return
     */
    static public boolean setFileDownloadInfo(String token, FileDownloadTokenEntity data) {
        try {
            ValueOperations<String, String> redis = redisTemplate.opsForValue();
            String download_token = JSONObject.fromObject(data).toString();
            redis.set("FileDownloadToken:" + token, download_token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置redis中的文件上传完毕参数
     *
     * @param md5
     * @param data
     * @return
     */
    static public boolean setFileInfo(String md5, FileInfoEntity data) {
        try {
            ValueOperations<String, String> redis = redisTemplate.opsForValue();
            String fileInfo = JSONObject.fromObject(data).toString();
            redis.set("FileMD5:" + md5, fileInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除上传token的方法
     *
     * @param token
     * @return
     */
    static public boolean deleteUploadToken(String token) {
        try {
            redisTemplate.delete("FileUploadToken:" + token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static public Integer increment(String key) {
        RedisAtomicLong count = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = count.incrementAndGet();
        Integer id = increment.intValue();
        if (id.equals(CommonConstant.DEVICE_MESSAGE_ID_MAX)) {
            return RedisOption.KeyReset(key);
        }
        return id;
    }

    static public Integer decrement(String key) {
        RedisAtomicLong count = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = count.decrementAndGet();
        return increment.intValue();
    }

    static public Boolean keyCheck(String key) {
        if (!redisTemplate.hasKey(key)) {
            decrement(key);
            return false;
        }
        return true;
    }

    static public Integer KeyReset(String key) {
        try {
            redisTemplate.delete(key);
            keyCheck(key);
            return increment(key).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    static public List<Map<String, Object>> getFileUploadProgress(Integer deviceTripleId) {
        String pattern = "deviceUploadLogProgress:" + deviceTripleId + "." + "*";
        Set<String> fileKeys = redisTemplate.keys(pattern);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String key : fileKeys) {
            Map<String, Object> resultMap = new HashMap<>();
            int pos1 = key.indexOf(".");
            String fileName = key.substring(pos1 + 1);
            resultMap.put("fileName", fileName);
            resultMap.put("percentage", redisTemplate.opsForValue().get(key));
            resultList.add(resultMap);
        }
        return resultList;
    }

    static public void initFileProgress(Integer deviceTripleId, Integer progress, String fileName) {
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        redis.set("deviceUploadLogProgress:" + deviceTripleId + "." + fileName, progress.toString(), 1800, TimeUnit.SECONDS);
    }

    static public void cleanFileProgress(Integer deviceTripleId, String fileName) {
        redisTemplate.delete("deviceUploadLogProgress:" + deviceTripleId + "." + fileName);
    }
}
