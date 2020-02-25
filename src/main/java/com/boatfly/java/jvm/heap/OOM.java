package com.boatfly.java.jvm.heap;

import java.util.Random;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails
 */
public class OOM {
    public static void main(String[] args) {
        //1.oom
//        String str = "github.com";
//        while (true) {
//            str += str + new Random().nextInt(99999999) + new Random().nextInt(99999999);
//        }
        //2.oom
//        byte[] oom = new byte[40 * 1024 * 1024];

    }
}
