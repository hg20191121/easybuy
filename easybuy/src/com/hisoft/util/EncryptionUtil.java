package com.hisoft.util;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 加密的工具类,支持MD5,SHA256,SHA512
 */
public class EncryptionUtil {

    private static StringBuilder bytes2HexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String s = Integer.toHexString(b & 0xff);
            if(s.length()==1){
                s = "0"+s;
            }
            sb.append(s);
        }
        return sb;
    }

    private static String encrypt(String source,String charsetName,String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        byte[] digest = md5.digest(source.getBytes(Charset.forName(charsetName)));
        StringBuilder builder;
        builder = bytes2HexString(digest);
        return builder.toString();
    }

    public static String str2Md5(String source,String charsetName) throws NoSuchAlgorithmException {
        return encrypt(source,charsetName,"md5");
    }
    public static String str2SHA256(String source,String charsetName) throws NoSuchAlgorithmException {
        return encrypt(source,charsetName,"sha-256");
    }
    public static String str2SHA512(String source,String charsetName) throws NoSuchAlgorithmException {
        return encrypt(source,charsetName,"sha-512");
    }

}
