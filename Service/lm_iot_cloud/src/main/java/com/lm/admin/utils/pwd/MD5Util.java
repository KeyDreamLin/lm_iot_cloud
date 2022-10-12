package com.lm.admin.utils.pwd;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

    public MD5Util() {
    }

    public static String md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            return bytesToHex(md5.digest());
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    /**
     * 字符串转md5
     * @param str
     * @return
     */
    public static String strToMd5s(String str) {
        return MD5Util.md5(MD5Util.md5("kuangstudy" + str + "202102170318!!!"));
    }

    public static String bytesToHex(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        String hashtext;
        for (hashtext = bigInt.toString(16); hashtext.length() < 32; hashtext = "0" + hashtext) {
        }
        return hashtext;
    }

    public static void main(String[] args) {
        System.out.println(strToMd5s("123456"));
    }
}