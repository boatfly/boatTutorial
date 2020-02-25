package com.boatfly.java.jvm.jmm;


/**
 * JMM
 * 可见性- 通知机制
 */
public class JMM {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myNumber.updateTo12();
            System.out.println(Thread.currentThread().getName() + ",num=" + myNumber.num);
        },"aa").start();

        while (myNumber.num==10){
            //需要有一种机制告诉main线程，此时num已经修改为12
        }
        System.out.println(Thread.currentThread().getName()+"，mission is over.");
    }
}

class MyNumber {
    int num = 10;//主内存有一个变量

    public void updateTo12() {
        num = 12;
    }
}
