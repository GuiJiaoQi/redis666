package com.bjpowernode.crm.commons.utils;

/**
 * 张凯
 * 2021/6/25
 */
public class testMD5 {
    public static void main(String[] args) {
        String s = MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5(MD5Util.getMD5("ls"+"zk")))))))))))))))))));
        System.out.println(s);
    }
}
