package com.boatfly.java.juc.examples.lock;

import java.util.concurrent.TimeUnit;

public class LockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "a").start();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "b").start();
    }
}

class Phone {
    public synchronized void sendSMS() throws Exception {
        System.out.println("**** send sms");
    }

    public synchronized void sendEmail() throws Exception {
        TimeUnit.SECONDS.sleep(4);//Thread.sleep(4000);
        System.out.println("**** send email");
    }
}