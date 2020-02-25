package com.boatfly.java.jvm.classloader;

public class BoatObject {
    public static void main(String[] args) {
        BoatObject boatObject = new BoatObject();
        System.out.println(boatObject.getClass().getClassLoader().getParent().getParent()); // Bootstrap c++编写 java打印null
        System.out.println(boatObject.getClass().getClassLoader().getParent()); // ExtClassLoader 扩展装载器 java ：pageage:javax.xxx.xxx
        System.out.println(boatObject.getClass().getClassLoader()); // AppClassLoader 系统装载器
        System.out.println("----------------------------------------");

        Object o = new Object();
        System.out.println(o.getClass().getClassLoader());//Bootstrap c++编写 java打印null


    }
}
