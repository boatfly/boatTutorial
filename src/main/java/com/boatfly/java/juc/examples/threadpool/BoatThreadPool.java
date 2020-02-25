package com.boatfly.java.juc.examples.threadpool;

import java.util.concurrent.*;

/**
 * Executor -> ExecutorService [Executors(线程池的工具类) | Arrays | Collections]
 * <p>
 * - 固定长度
 * - 1个
 * - 可扩容
 * <p>
 * ThreadPoolExecutor
 * this(
 * corePoolSize, 线程中的常驻核心线程数
 * maximumPoolSize, 线程池中能够容纳同时执行的最大线程数，>=1，当常驻的用完，并且阻塞队列也满了的时候，需要扩容，扩则一下扩到最大！
 * keepAliveTime,多余的空闲线程的存活时间，当前线程池线程数量corePoolSize时，当空闲时间达到keepAliveTime时，多余线程会被销毁直到只剩下corePoolSize
 * unit,keepAliveTime的单位
 * workQueue, 任务队列，被提交但尚未被执行的任务
 * Executors.defaultThreadFactory(),用于创建工作线程
 * defaultHandler);拒绝策略，表示当队列满了，如何拒绝q请求执行的Runnable测策略。当已达最大线程数，并且阻塞队列也满了，此时就要用到拒绝策略
 * <p>
 * 工作原理：
 * 线程池拒绝策略：
 * - jdk内置的拒绝策略
 * - 以上内置策略均实现了RejectedExecutionHandle接口
 * - AbortPolicy 中断策略
 * - CallerRunsPolicy 回退
 * - DiscardPolicy 丢弃
 * - DiscardOldestPolicy
 *
 *
 * - cpu密集型
 *   最大核数+1就是最大线程数
 * - io密集型
 * <p>
 * <p>
 * <p>
 * <p>
 * SynchronousQueue：单项链表
 */
public class BoatThreadPool {
    public static void main(String[] args) {
        //useExecutors();
        ThreadPoolExecutor threadPool1 = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors(),
                3L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3), //不填，默认MAXVALUE
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());//丢弃等待队列中最久的
                //new ThreadPoolExecutor.DiscardPolicy());//丢弃
                //new ThreadPoolExecutor.CallerRunsPolicy());//回退
                //new ThreadPoolExecutor.AbortPolicy());//默认舍弃策略
        try {
            for (int i = 0; i < 20; i++) {
                threadPool1.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " is working.");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool1.shutdown();
        }
    }

    private static void useExecutors() {
        //        ExecutorService threadPool1 = Executors.newFixedThreadPool(3);//固定长度
//        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();//1
        ExecutorService threadPool1 = Executors.newCachedThreadPool();//可伸缩
        try {
            for (int i = 0; i < 10; i++) {
                threadPool1.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " is working.");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool1.shutdown();
        }
    }
}
