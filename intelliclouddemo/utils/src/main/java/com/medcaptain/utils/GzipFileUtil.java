package com.medcaptain.utils;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 使用GZIP算法压缩文件工具类
 *
 * @author bingwen.ai
 */
public class GzipFileUtil {
    private static final int BUFFER_SIZE = 10 * 1024;

    /**
     * 压缩文件
     *
     * @param sourceFilePath     源文件路径
     * @param compressedFilePath 压缩后文件存放路径
     * @return true = 压缩成功；false = 压缩失败
     */
    public static boolean compress(String sourceFilePath, String compressedFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source File not exist");
        }
        File compressedFile = new File(compressedFilePath);
        if (compressedFile.exists()) {
            throw new FileAlreadyExistsException("Compress File already exist");
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream outputStream = new FileOutputStream(compressedFile);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        int readByteCount;
        while ((readByteCount = fileInputStream.read(buffer)) > 0) {
            gzipOutputStream.write(buffer, 0, readByteCount);
        }
        fileInputStream.close();
        gzipOutputStream.close();
        outputStream.close();
        return true;
    }

    /**
     * 解压缩文件
     *
     * @param sourceFilePath       源文件路径
     * @param uncompressedFilePath 解压后文件存放路径
     * @return true = 压缩成功；false = 压缩失败
     */
    public static boolean uncompress(String sourceFilePath, String uncompressedFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source File not exist");
        }
        File compressedFile = new File(uncompressedFilePath);
        if (compressedFile.exists()) {
            throw new FileAlreadyExistsException("Uncompress File already exist");
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream outputStream = new FileOutputStream(compressedFile);
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        int readByteCount;
        while ((readByteCount = gzipInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, readByteCount);
        }
        fileInputStream.close();
        outputStream.close();
        outputStream.close();
        return true;
    }

    /**
     * 将压缩文件读取为字符串列表
     *
     * @param compressedFilePath 压缩文件路径
     * @return 压缩文件内容字符串列表
     */
    public static List<String> readCompressLines(String compressedFilePath) {
        try {
            String tempFileName = UUIDUtil.getUUID();
            File tempFile = new File(tempFileName);
            if (uncompress(compressedFilePath, tempFile.getPath())) {
                List<String> contentLines = FileUtil.lines(tempFile);
                Files.deleteIfExists(tempFile.toPath());
                return contentLines;
            }
        } catch (Exception ex) {

        }
        return null;
    }
}
