package com.boatfly.java.stream.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 内置4大核心函数式接口
 * - Consumer<T>
 * 消费型接口
 * - Suppliier<T>
 * 供给型接口
 * - Function<T>
 * 函数型接口
 * - Predicate<T>
 * 断定型接口
 */
public class FunctionDemo {
    public static void main(String[] args) {
        Function<String, Integer> function = s -> {
            return s.length();
        };
        System.out.println(function.apply("abcd"));

        Predicate<String> predicate=s -> {
            return s.contains("s");
        };
        System.out.println(predicate.test("abcs"));

        Consumer<String> consumer = s -> {
            System.out.println(s);
        };
        consumer.accept("abcde");

        Supplier<String> supplier = () -> {
            return "abcdefg";
        };
        System.out.println(supplier.get());
    }
}

interface Myinterface {
    int myint(int x);

    String mystring(String x);

    boolean isok(String x);
}
