package com.boatfly.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 基础语法：
 * 箭头操作符：->
 * (参数列表)->{功能方法体}
 *
 * @FunctionInterface 注解定义
 * <p>
 * 语法格式1：无参 无返回值
 * ()->System.out.println("ethereum!"); //@see test1()
 * 语法格式2：有1个参数 无返回值
 * (t)-> //@see test2()
 * 语法格式3：有1个参数 ()小括号可以不写
 * 语法格式3：有2++的参数，有返回值，并且lambda体有多条语句
 * (a,b,c)->{}
 */
public class TestLambda2 {
    @Test
    public void test1() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("ethereum!");
            }
        };
        runnable.run();
        Runnable runnable1 = () -> System.out.println("ethereum!");
        runnable1.run();
    }

    @Test
    public void test2() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s + ":abc");
            }
        };
        Consumer<String> consumer1 = (t) -> System.out.println(t + ":abc");
        consumer1.accept("boat-");
    }

    @Test
    public void test3() {
        Comparator<Integer> comparator = (x, y) -> {
            return Integer.compare(x, y);
        };
    }
}
