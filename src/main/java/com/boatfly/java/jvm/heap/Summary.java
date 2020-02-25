package com.boatfly.java.jvm.heap;

public class Summary {
    public static void main(String[] args) {
        System.out.println("availableProcessors:" + Runtime.getRuntime().availableProcessors());

        long maxMemory = Runtime.getRuntime().maxMemory();// 返回java虚拟机试图使用的最大内存量
        long totalMemory = Runtime.getRuntime().totalMemory();// 返回java虚拟机中的内存总量
        System.out.println("-Xmx:MAX_MEMORY=" + maxMemory + "(字节)、" + maxMemory / (double) 1024 / 1024 + "（MB）");
        System.out.println("-Xms:TOTAL_MEMORY=" + maxMemory + "(字节)、" + totalMemory / (double) 1024 / 1024 + "（MB）");
    }
}
