package com.knowledge.base.util;

import com.knowledge.base.config.EnvBeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;



public class AESUtil {
    private static final String ENCODING = "utf-8";
    private static final String KEY_ALGORITHM = "AES";
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static String aes_key;
    
    private static String ECB_PREFIX = "ECB:";
    
    static {
        aes_key = StringUtils.defaultString(EnvBeanUtil.getString("aes.encryptor.password"), "4e8dec21a70d1e0891539b487009b04f");
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String ecbEncrypt(String content) {
        if(StringUtils.isBlank(content)){
            return content;
        }
        if(content.startsWith(ECB_PREFIX)){
            return content;
        }
        // 拼接前缀
        return ECB_PREFIX + ecbEncrypt(content,aes_key);
    }
    /**
     * AES 解密操作
     *
     * @param content
     * @return
     */
    public static String ecbDecrypt(String content) {
        if(StringUtils.isBlank(content)){
            return content;
        }
        if(content.startsWith(ECB_PREFIX)){
            // 去除前缀
            content = content.replace(ECB_PREFIX,"");
        }
        return ecbDecrypt(content,aes_key);
    }
    
    
    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String ecbEncrypt(String content, String key) {
        byte[] data = null;
        try {
            byte[] contentBytes = content.getBytes(ENCODING);
            data = encryptOrDecrypt(Cipher.ENCRYPT_MODE, contentBytes, key, null, EncodeType.AES_ECB_PKCS5Padding);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return data == null ? null : Base64.encodeBase64String(data);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String cbcEncrypt(String content, String key, String iv) {
        byte[] data = null;
        try {
            byte[] contentBytes = content.getBytes(ENCODING);
            data = encryptOrDecrypt(Cipher.ENCRYPT_MODE, contentBytes, key, iv, EncodeType.AES_CBC_PKCS5Padding);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return data == null ? null : Base64.encodeBase64String(data);
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String ecbDecrypt(String content, String key) {
        try {
            byte[] contentBytes = Base64.decodeBase64(content);
            byte[] data = encryptOrDecrypt(Cipher.DECRYPT_MODE, contentBytes, key, null, EncodeType.AES_ECB_PKCS5Padding);
            return new String(data, ENCODING);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String cbcDecrypt(String content, String key, String iv) {
        try {
            byte[] contentBytes = Base64.decodeBase64(content);
            byte[] data = encryptOrDecrypt(Cipher.DECRYPT_MODE, contentBytes, key, iv, EncodeType.AES_CBC_PKCS5Padding);
            return new String(data, ENCODING);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据时间戳13位，生成aes cbc 加密向量 vi
     * 生成规则先把时间戳反向，用零补足16位
     *
     * @param timestamp
     * @return
     */
    public static String genTimestampIV(String timestamp) {
        return StringUtils.reverse(timestamp) + "000";
    }

    private static byte[] encryptOrDecrypt(int mode, byte[] contentBytes, String key, String iv, String modeAndPadding) throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] keyBytes = key.getBytes(ENCODING);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(modeAndPadding);// 创建密码器
        if (null != iv) {
            //指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            byte[] ivBytes = iv.getBytes(ENCODING);
            cipher.init(mode, keySpec, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(mode, keySpec);
        }
        return cipher.doFinal(contentBytes);
    }

    public class EncodeType {
        //    算法/模式/填充                 16字节加密后数据长度       不满16字节加密后长度
        //    AES/CBC/NoPadding                   16                          不支持
        //    AES/CBC/PKCS5Padding                32                          16
        //    AES/CBC/ISO10126Padding             32                          16
        //    AES/CFB/NoPadding                   16                          原始数据长度
        //    AES/CFB/PKCS5Padding                32                          16
        //    AES/CFB/ISO10126Padding             32                          16
        //    AES/ECB/NoPadding                   16                          不支持
        //    AES/ECB/PKCS5Padding                32                          16
        //    AES/ECB/ISO10126Padding             32                          16
        //    AES/OFB/NoPadding                   16                          原始数据长度
        //    AES/OFB/PKCS5Padding                32                          16
        //    AES/OFB/ISO10126Padding             32                          16
        //    AES/PCBC/NoPadding                  16                          不支持
        //    AES/PCBC/PKCS5Padding               32                          16
        //    AES/PCBC/ISO10126Padding            32                          16
        //    默认为 ECB/PKCS5Padding
        public final static String AES_DEFAULT = "AES";
        public final static String AES_CBC_NoPadding = "AES/CBC/NoPadding";
        public final static String AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
        public final static String AES_CBC_ISO10126Padding = "AES/CBC/ISO10126Padding";
        public final static String AES_CFB_NoPadding = "AES/CFB/NoPadding";
        public final static String AES_CFB_PKCS5Padding = "AES/CFB/PKCS5Padding";
        public final static String AES_CFB_ISO10126Padding = "AES/CFB/ISO10126Padding";
        public final static String AES_ECB_NoPadding = "AES/ECB/NoPadding";
        public final static String AES_ECB_PKCS5Padding = "AES/ECB/PKCS5Padding";
        public final static String AES_ECB_ISO10126Padding = "AES/ECB/ISO10126Padding";
        public final static String AES_OFB_NoPadding = "AES/OFB/NoPadding";
        public final static String AES_OFB_PKCS5Padding = "AES/OFB/PKCS5Padding";
        public final static String AES_OFB_ISO10126Padding = "AES/OFB/ISO10126Padding";
        public final static String AES_PCBC_NoPadding = "AES/PCBC/NoPadding";
        public final static String AES_PCBC_PKCS5Padding = "AES/PCBC/PKCS5Padding";
        public final static String AES_PCBC_ISO10126Padding = "AES/PCBC/ISO10126Padding";
    }

    public static void main(String[] args) {
        String key = "4e8dec21a70d1e0891539b487009b04f";
        String iv = key.substring(0, 16);
        String content = "hello,您好";//ce214b206f650f32

        String encrypt = cbcEncrypt(content, key, iv);
        logger.info("cbc encrypt:{}", encrypt);
        String decrypt = cbcDecrypt(encrypt, key, iv);
        logger.info("cbc decrypt:{}", decrypt);

        encrypt = ecbEncrypt(content, key);
        logger.info("cbc encrypt:{}", encrypt);
        decrypt = ecbDecrypt(encrypt, key);
        logger.info("cbc decrypt:{}", decrypt);

        for (int i = 0; i < 5; i++) {
            iv = StringUtils.reverse(String.valueOf(System.currentTimeMillis())) + "000";
            encrypt = cbcEncrypt(content, key, iv);
            logger.info("for cbc encrypt:{}", encrypt);
            decrypt = cbcDecrypt(encrypt, key, iv);
            logger.info("for cbc decrypt:{}", decrypt);
        }


    }
}
