package com.boatfly.java.juc.examples.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁（相对于Lock来说，读的时候不需要锁）
 * 写独占，读共享
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Mycache mycache = new Mycache();
        for (int i = 0; i < 5; i++) {
            final int tempi = i;
            new Thread(() -> {
                mycache.put(tempi + "", tempi + "");
            }, "thW-" + i).start();
        }
        for (int i = 0; i < 5; i++) {
            final int tempi = i;
            new Thread(() -> {
                mycache.get(tempi + "");
            }, "thR-" + i).start();
        }

    }
}

class Mycache {
    private volatile Map<String, Object> map = new HashMap<>();//volatile 保证数据的可见性，但不保证原子性
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t---enter put.key=" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t---write complete.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Object get(String key) {
        readWriteLock.readLock().lock();
        Object o = null;
        try {
            System.out.println(Thread.currentThread().getName() + "\tenter get.key=" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\tread complete. value=" + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return o;
    }
}
