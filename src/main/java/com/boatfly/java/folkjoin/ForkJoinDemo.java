package com.boatfly.java.folkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 分支合并框架
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountTask countTask = new CountTask(0, 1040);
        ForkJoinPool threadpool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = threadpool.submit(countTask);
        System.out.println(forkJoinTask.get());
        threadpool.shutdown();
    }
}

class CountTask extends RecursiveTask<Integer> {

    private final static Integer ADJUST_VALUE = 10;
    private int begin;
    private int end;
    private int result;

    CountTask(int _begin, int _end) {
        this.begin = _begin;
        this.end = _end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= ADJUST_VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
                System.out.println(Thread.currentThread().getName() + "," + i + "-:" + result);
            }
        } else {
            int middle = (end + begin) / 2;
            CountTask ct1 = new CountTask(begin, middle);
            CountTask ct2 = new CountTask(middle + 1, end);
            ct1.fork();
            ct2.fork();
            result = ct1.join() + ct2.join();
        }
        return result;
    }
}