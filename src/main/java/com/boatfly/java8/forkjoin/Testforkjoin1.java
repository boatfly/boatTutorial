package com.boatfly.java8.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalLong;
import java.util.stream.LongStream;

public class Testforkjoin1 {
    @Test
    public void test(){
        Instant start = Instant.now();
        OptionalLong reduce = LongStream.rangeClosed(1, 10000000000L).parallel().reduce(Long::sum);//parallel底层使用的是forkjoin
        System.out.println(reduce.getAsLong());
        Instant end = Instant.now();
        System.out.println("耗时："+ Duration.between(start,end).toMillis());
    }
}
