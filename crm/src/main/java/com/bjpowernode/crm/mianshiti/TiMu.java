package com.bjpowernode.crm.mianshiti;

/**
 * 张凯
 * 2021/7/14
 */
public class TiMu {
    public static void main(String[] args) {
        int n = 9;
        int m = (n & (1 << 3)) >> 3;
        System.out.println(m);
    }
}
