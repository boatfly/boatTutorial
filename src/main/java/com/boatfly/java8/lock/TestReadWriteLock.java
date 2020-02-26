package com.boatfly.java8.lock;

import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：ReadWriteLock
 * <p>
 * 写写、读写，需要互斥（独占）
 * <p>
 * 读读不需要互斥
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                readWriteLockDemo.write(finalI);
            }, "write-" + i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                readWriteLockDemo.read();
            }, "read-" + i).start();
        }
    }

}

class ReadWriteLockDemo {

    private int num = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        lock.readLock().lock();
        try {
            try {
                System.out.println(Thread.currentThread().getName()+"\tenter read");
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write(int newvalue) {
        lock.writeLock().lock();
        try {
            try {
                System.out.println(Thread.currentThread().getName()+"\tenter write");
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + newvalue);
            num = newvalue;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
