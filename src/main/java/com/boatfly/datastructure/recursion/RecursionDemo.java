package com.boatfly.datastructure.recursion;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RecursionDemo {
    public static void main(String[] args) {
        test(10);
        System.out.println("-----------------");
        System.out.println(factorial(4));
        System.out.println("-----------------");
        Map<Integer,Integer> map = new HashMap<>();
        putBallsToBaskets(2,2,2,map);

    }

    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }

    /**
     *
     * @param balls 球的数量
     * @param baskets 篮子数量
     * @param capacity 篮子可放球的数量
     */
    public static void putBallsToBaskets(int balls,int baskets,int capacity,Map<Integer,Integer> map){
        for (int i = 0; i <baskets ; i++) {
            //篮子

        }
    }
}
