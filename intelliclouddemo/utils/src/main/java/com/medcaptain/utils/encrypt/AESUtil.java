package com.medcaptain.utils.encrypt;


import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密工具类
 */
public class AESUtil {
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    private static final int DEFAULT_ENCRYPT_SIZE = 256;

    private static final String DEFAULT_ENCRYPT_ALGORITHM = "AES";

    private static byte[] doAlgorithm(byte[] sourceBytes, String key, boolean isDecryptMode) throws Exception {
        if (sourceBytes == null || sourceBytes.length == 0) return null;

        if (StringUtils.isEmpty(key))
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");

        int cipherMethod = Cipher.ENCRYPT_MODE;
        if (isDecryptMode)
            cipherMethod = Cipher.DECRYPT_MODE;

        KeyGenerator keyGenerator = KeyGenerator.getInstance(DEFAULT_ENCRYPT_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        secureRandom.setSeed(key.getBytes(CHARSET_UTF8));
        keyGenerator.init(DEFAULT_ENCRYPT_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), DEFAULT_ENCRYPT_ALGORITHM);
        Cipher aes = Cipher.getInstance(DEFAULT_ENCRYPT_ALGORITHM);
        aes.init(cipherMethod, secretKeySpec);
        byte[] encryptedBytes = aes.doFinal(sourceBytes);

        return encryptedBytes;
    }

    /**
     * 加密字符传
     *
     * @param contentToEncrypt 需加密的字符串
     * @param key              密码
     * @return 加密后的base64格式字符串
     * @throws Exception
     */
    public static String encrypt(String contentToEncrypt, String key) throws Exception {
        if (StringUtils.isEmpty(contentToEncrypt)) return null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");
        }

        byte[] sourceBytes = contentToEncrypt.getBytes(CHARSET_UTF8);
        byte[] encryptedBytes = doAlgorithm(sourceBytes, key, false);
        return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密字符传
     *
     * @param contentToDecrypt 需解密的字符串
     * @param key              密码
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String contentToDecrypt, String key) throws Exception {
        if (StringUtils.isEmpty(contentToDecrypt)) return null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");
        }

        byte[] encryptedBytes = Base64.getDecoder().decode(contentToDecrypt);
        byte[] decryptedBytes = doAlgorithm(encryptedBytes, key, true);
        String decrypted = new String(decryptedBytes, CHARSET_UTF8);
        return decrypted;
    }
}
