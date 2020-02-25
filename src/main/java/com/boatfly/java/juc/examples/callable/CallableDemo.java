package com.boatfly.java.juc.examples.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Runnable -> RunnableFuture -> FutureTask
 * <p>
 * Runnable Callable Adapter Design parttern
 * <p>
 * ALI DRUID DATABASE CONNECTION POOL
 * EexcutorService thread pool
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask(new MyThread());
        new Thread(futureTask, "aa").start();
        String result = futureTask.get();//有返回值的线程方法
        System.out.println(result);
    }
}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("come in call method.");
        return "juc";
    }
}