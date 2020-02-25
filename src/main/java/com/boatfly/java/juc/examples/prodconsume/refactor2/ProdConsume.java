package com.boatfly.java.juc.examples.prodconsume.refactor2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.高聚低合前提下，线程操作资源类
 * 2.判断、干活、通知
 * 3.防止虚假唤醒
 */
public class ProdConsume {
    public static void main(String[] args) throws Exception {
        AirCondition airCondition = new AirCondition();
        new Thread(() -> {
            try {
                for (int i = 0; i <= 10; i++)
                    airCondition.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "a").start();
        new Thread(() -> {
            try {
                for (int i = 0; i <= 10; i++)
                    airCondition.decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "b").start();
        new Thread(() -> {
            try {
                for (int i = 0; i <= 10; i++)
                    airCondition.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "c").start();
        new Thread(() -> {
            try {
                for (int i = 0; i <= 10; i++)
                    airCondition.decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "d").start();
    }
}

class AirCondition {
    private int num = 0;
    private Lock lock = new ReentrantLock();//可重入非公平的递归锁
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            //1.判断
            while (num != 0) { //防止虚假唤醒，循环+判断（唤醒后重新判断）
                condition.await();//this.wait();
            }
            //2.干活
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            //3.通知
            condition.signalAll();//this.notifyAll();//唤醒
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1.判断
            while (num == 0) { //防止虚假唤醒，循环+判断（唤醒后重新判断）
                condition.await();//this.wait();
            }
            //2.干活
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            //3.通知
            condition.signalAll();//this.notifyAll();//唤醒
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
