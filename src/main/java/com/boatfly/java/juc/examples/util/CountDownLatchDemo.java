package com.boatfly.java.juc.examples.util;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" success.");
                countDownLatch.countDown();
            }, "th-" + i).start();
        }

        countDownLatch.await();
        System.out.println("main is over.");
    }
}
