package com.boatfly.java.juc.examples.lamdda;

/**
 * 1.拷贝小括号，写死右箭头，落地大括号
 * 2.@FunctionalInterface
 * 3.default
 * 4.static
 * <p>
 * 解决了匿名内部类代码冗余的场景
 */
public class LambdaExpress {
    public static void main(String[] args) {
//        Foo foo = new Foo() {
//            @Override
//            public void hello() {
//                System.out.println("****h 2020");
//            }
//
//            @Override
//            public int add(int x, int y) {
//                return 0;
//            }
//        };
//        foo.hello();

        Foo foo = (int x, int y) -> {
            System.out.println("****h 2020 from lambda");
            return x + y;
        };
        System.out.println(foo.add(3, 4));


    }
}

@FunctionalInterface //只有函数接口才能写lambda表达式
interface Foo {
    //public void hello();

    int add(int x, int y);

    default int mul(int x, int y) {
        return x * y;
    }

    static int div(int x, int y) {
        return x / y;
    }
}
