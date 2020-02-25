package com.boatfly.java.juc.examples.saletickets.refactor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个售票员 卖出 30张票
 * tips：
 * 在高内聚低耦合的前提下，线程操作资源类
 */
public class SaleTickets {
    public static void main(String[] args) {
        final Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.sale();
        }, "c").start();

    }
}

class Ticket { //资源类
    private int num = 30;

    Lock lock = new ReentrantLock();//可重入锁

    /**
     * 重锁
     * synchronized 加到方法上，同步方法
     * synchronized(this){
     * //同步代码块
     * }
     * 这里，用java.util.concurrent.locks 细粒度锁
     */
    public void sale() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第" + num-- + "张\t还剩下：" + num + "张"); //mythread =>Thread.currentThread().getName() 自定义代码块 Editor->Code Style->Live Template
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
