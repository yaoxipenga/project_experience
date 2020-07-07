package com.medcaptain.fastdfs.connection;
/*
 * 文件名：FastDFSClient.java 版权：Copyright by www.inhand.com 描述： 修改人：kokJuis 修改时间：2017年7月27日 跟踪单号：
 * 修改单号： 修改内容：
 */


import com.medcaptain.fastdfs.client.master.FileInfo;
import com.medcaptain.fastdfs.client.master.MyStorageClient;
import com.medcaptain.fastdfs.client.common.MyException;
import com.medcaptain.fastdfs.client.common.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class ConnectionManager
{
    /** 连接池 */
    static  private ConnectionPool connectionPool = new ConnectionPool();
//    @Autowired
//    static private ConnectionPool connectionPool;

    /**
     * 上传文件
     *
     * @param buff
     *            文件对象
     * @param fileName
     *            文件名
     * @return
     */
    static public String uploadFile(int code,byte[] buff, String fileName)
    {
        return uploadFile(code,buff, fileName, null, null);
    }

    static public String uploadFile(int code,byte[] buff, String fileName, String groupName)
    {
        return uploadFile(code,buff, fileName, null, groupName);
    }

    /**
     * 上传文件
     *
     * @param code
     *
     * @param buff
     *            文件对象
     * @param fileName
     *            文件名
     * @param metaList
     *            文件元数据
     * @return
     */
    static public String uploadFile(int code,byte[] buff, String fileName, Map<String, String> metaList,
                             String groupName)
    {

        try
        {

            /** 获取可用的tracker,并创建存储server */
            MyStorageClient storageClient = connectionPool.checkout();
            String path = null;
            if (!StringUtils.isEmpty(groupName))
            {
                // 上传到指定分组
                path = storageClient.upload_file1(code,groupName, buff,
                        ConnectionUtil.getExtensionName(fileName), null);
            }
            else
            {
                path = storageClient.upload_file1(code,buff, ConnectionUtil.getExtensionName(fileName),
                        null);
            }

            /** 上传完毕及时释放连接 */
            connectionPool.checkin(storageClient);

            return path;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件元数据
     *
     * @param fileId
     *            文件ID
     * @return
     */
    static public Map<String, String> getFileMetadata(String fileId)
    {

        try
        {

            /** 获取可用的tracker,并创建存储server */
            MyStorageClient storageClient = connectionPool.checkout();

            NameValuePair[] metaList = storageClient.get_metadata1(fileId);
            /** 上传完毕及时释放连接 */
            connectionPool.checkin(storageClient);

            if (metaList != null)
            {
                HashMap<String, String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList)
                {
                    map.put(metaItem.getName(), metaItem.getValue());
                }
                return map;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileId
     *            文件ID
     * @return 删除失败返回-1，否则返回0
     */
    static public int deleteFile(String fileId)
    {
        try
        {

            /** 获取可用的tracker,并创建存储server */
            MyStorageClient storageClient = connectionPool.checkout();

            int i = storageClient.delete_file1(fileId);
            /** 上传完毕及时释放连接 */
            connectionPool.checkin(storageClient);

            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     *
     * @param fileId
     *            文件ID（上传文件成功后返回的ID）
     * @return
     */
    static public byte[] downloadFile(String fileId)
    {
        try
        {

            /** 获取可用的tracker,并创建存储server */
            MyStorageClient storageClient = connectionPool.checkout();

            byte[] content = storageClient.download_file1(fileId);
            /** 上传完毕及时释放连接 */
            connectionPool.checkin(storageClient);

            return content;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (MyException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description:获取文件信息
     *
     * @param fileId
     * @return
     * @see
     */
    static public FileInfo getFileInfo(String fileId)
    {

        try
        {
            /** 获取可用的tracker,并创建存储server */
            MyStorageClient storageClient = connectionPool.checkout();
            FileInfo fileInfo = storageClient.get_file_info1(fileId);
            /** 上传完毕及时释放连接 */
            connectionPool.checkin(storageClient);

            return fileInfo;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (MyException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
