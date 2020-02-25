package com.boatfly.java8.date;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TestLocalDateTime {
    @Test
    public void test(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime of = LocalDateTime.of(2020, 2, 25, 23, 28, 30);
        System.out.println(of.toString());

        LocalDateTime ldt2 = ldt.plusYears(2);
        System.out.println(ldt2);
        LocalDateTime ldt3 = ldt.plusYears(-2);//ldt.minusYears(2)
        System.out.println(ldt3);

        System.out.println(Instant.now().atOffset(ZoneOffset.ofHours(8)));
    }
}
