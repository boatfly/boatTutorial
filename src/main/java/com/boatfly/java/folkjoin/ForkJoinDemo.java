package com.boatfly.java.folkjoin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 分支合并框架
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CountTask countTask = new CountTask(0, 1040);
//        ForkJoinPool threadpool = new ForkJoinPool();
//        ForkJoinTask<Integer> forkJoinTask = threadpool.submit(countTask);
//        System.out.println(forkJoinTask.get());
//        threadpool.shutdown();

        List<CBto> list = new ArrayList<>();
        list.add(new CBto("FFHK", new BigDecimal(1244442)));
        list.add(new CBto("FFHK1", new BigDecimal(133)));
        list.add(new CBto("FFHK2", new BigDecimal(134)));
        list.add(new CBto("FFHK3", new BigDecimal(1234234)));
        list.add(new CBto("FFHK4", new BigDecimal(1211111134)));
        list.add(new CBto("FFHK5", new BigDecimal(13234)));

        ChannelBalanceTask countTask = new ChannelBalanceTask(list);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<CBto> forkJoinTask = forkJoinPool.submit(countTask);
        System.out.println(forkJoinTask.get());
        forkJoinPool.shutdown();

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

@Data
@AllArgsConstructor
class CBto {
    String channeltag;
    BigDecimal balance;
}

class ChannelBalanceTask extends RecursiveTask<CBto> {

    List<CBto> channeltags = null;

    public ChannelBalanceTask(List<CBto> _channeltags) {
        this.channeltags = _channeltags;
    }

    static CBto cto = null;

    @Override
    protected CBto compute() {

        if (channeltags.size() > 0) {
            CBto cBto = channeltags.get(0);
            if (cto == null || cto.getBalance().compareTo(cBto.getBalance()) < 0) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cto = cBto;
            }
            channeltags.remove(0);
            if (channeltags.size() > 0) {
                ChannelBalanceTask c1 = new ChannelBalanceTask(channeltags);
                c1.fork();
                cto = c1.join();
            }
        } else {
            System.out.println("---------");
        }

        return cto;
    }
}