package com.boatfly.java.juc.examples.notsafe;

import com.sun.jersey.client.impl.CopyOnWriteHashMap;
import com.univocity.parsers.annotations.Copy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 故障现象
 * java.util.concurrentModificationException
 * 导致原因
 * 解决方法
 * 1.new Vector<>();
 * 2.Collections.synchronizedList(new ArrayList<>());
 * 3.new CopyOnWriteArrayList<>();//线程安全,写时复制
 * 优化建议
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        //listNotSafe();
        //setNotSafe();
        mapNotSafe();
    }

    private static void mapNotSafe() {
        Map<String,String> map = new ConcurrentHashMap<>();
//        list.add("a");
//        list.add("a");
//        list.add("a");
//        list.forEach(System.out::println);
        for (int i = 1; i <= 30; i++){
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            }).start();
        }
    }

    private static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();
//        list.add("a");
//        list.add("a");
//        list.add("a");
//        list.forEach(System.out::println);
        for (int i = 1; i <= 30; i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            }).start();
        }
    }

    private static void listNotSafe() {
        //ArrayList 初始size 10 -> 扩容1 15（10+5） Array.copyof ->扩容2 22（15+7） 50%
        //线程不安全
        //Hashmap 16 32 64

        //List<String> list = new ArrayList<>();线程不安全
        //List<String> list = new Vector<>();//线程安全
        //List<String> list = Collections.synchronizedList(new ArrayList<>());//线程安全
        List<String> list = new CopyOnWriteArrayList<>();//线程安全,写时复制
//        list.add("a");
//        list.add("a");
//        list.add("a");
//        list.forEach(System.out::println);
        for (int i = 1; i <= 30; i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
