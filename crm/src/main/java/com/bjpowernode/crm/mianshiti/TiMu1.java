package com.bjpowernode.crm.mianshiti;

/**
 * 张凯
 * 2021/7/14
 */
public class TiMu1 {
    public static void main(String[] args) {
        cuString();
    }
    private static void cuString(){
        String greeting = "Hello,Word!";
        String s = greeting.substring(1,7)+"I'm coming.";
        //s = ello,W I'm coming.
        System.out.println(s.split("o")[2]);
    }
}
