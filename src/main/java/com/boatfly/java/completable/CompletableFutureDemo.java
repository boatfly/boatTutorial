package com.boatfly.java.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步回调接口
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ",没有返回值得,update mysql ok");
        });
        //System.out.println(voidCompletableFuture.get());
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ",有返回值得,insert mysql ok");
            int age = 10 / 0;
            return 12;
        });
        //System.out.println(integerCompletableFuture.get());
        Integer integer = integerCompletableFuture.whenComplete((t, u) -> {
            System.out.println("****t:=" + t);
            System.out.println("****u:=" + u);
        }).exceptionally(f -> {
            System.out.println("******" + f.getMessage());
            return 444;
        }).get();
        System.out.println(integer);
    }
}
