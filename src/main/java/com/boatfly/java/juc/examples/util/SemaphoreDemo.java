package com.boatfly.java.juc.examples.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号灯
 * 两个目的：
 * - 用户多个共享资源的互斥使用
 * - 用于并发线程数的控制
 * 在信号上我们定义了两种操作：
 * - acquire() 获取，如果没有成功获取，则一直等下去，直到有线程释放信号灯。
 * - release() 释放
 *
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//模拟有3个空车位
        for (int i = 0; i < 6; i++) { // 模拟6辆车抢车位
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" accquire a carport.");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+" leave a carport.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }, "th-" + i).start();
        }
    }
}
