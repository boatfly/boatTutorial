package com.boatfly.java8.lambda;

/**
 * use in TestLambda3
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface MyFunction<T,R> {
    R getValue(T t1,T t2);
}
