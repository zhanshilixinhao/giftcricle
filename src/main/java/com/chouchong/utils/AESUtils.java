package com.chouchong.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author yichenshanren
 * @date 2018/6/19
 */

public class AESUtils {

    public static final String TAG = "AESUtils";
    public static byte[] t = new byte[]{48, 48, 49, 55, 68, 67, 49, 66, 69, 50, 50, 53, 56, 53, 53, 52, 67, 70, 48, 50, 67, 53, 55, 66, 55, 56, 69, 55, 52, 48, 65, 53};

    public AESUtils() {
    }

    public static String encrypt(String seed, String clearText) {
        byte[] result = null;

        try {
            byte[] rawkey = getRawKey(seed.getBytes());
            result = encrypt(rawkey, clearText.getBytes());
        } catch (Exception var4) {
            ;
        }

        if(result != null) {
            String content = toHex(result);
            return content;
        } else {
            return null;
        }
    }

    public static String decrypt(String seed, String encrypted) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            byte[] enc = toByte(encrypted);
            byte[] result = decrypt(rawKey, enc);
            String coentn = new String(result);
            return coentn;
        } catch (Exception var6) {
            return null;
        }
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        return toByte(new String(t, 0, 32));
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for(int i = 0; i < len; ++i) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }

    public static String toHex(byte[] buf) {
        if(buf == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer(2 * buf.length);

            for(int i = 0; i < buf.length; ++i) {
                appendHex(result, buf[i]);
            }

            return result.toString();
        }
    }

    private static void appendHex(StringBuffer sb, byte b) {
        String HEX = "0123456789ABCDEF";
        sb.append("0123456789ABCDEF".charAt(b >> 4 & 15)).append("0123456789ABCDEF".charAt(b & 15));
    }

    public static void main(String[] args) {

    }

}
