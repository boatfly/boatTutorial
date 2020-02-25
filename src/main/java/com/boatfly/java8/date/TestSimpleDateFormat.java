package com.boatfly.java8.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestSimpleDateFormat {
    /**
     * 演示jdk8之前的simpledateformat线程不安全
     */
    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Callable<Date> callable = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sdf.parse("20200225");
            }
        };
        List<Future<Date>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(pool.submit(callable));
        }

        result.stream().forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        });

        pool.shutdown();
    }

    /**
     * refactor to threadlocal
     */
    @Test
    public void test2() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Callable<Date> callable = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20200225");
            }
        };
        List<Future<Date>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(pool.submit(callable));
        }

        result.stream().forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        });

        pool.shutdown();
    }

    /**
     * refactor to jdk8
     */
    @Test
    public void test3() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Callable<LocalDate> callable = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20200225", dtf);
            }
        };
        List<Future<LocalDate>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(pool.submit(callable));
        }

        result.stream().forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        });

        pool.shutdown();
    }
}
