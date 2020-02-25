package com.boatfly.java8.lambda;

import com.boatfly.java8.bean.Employee;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * 方法引用：
 * 有三种语法格式：
 * 1.
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名
 *
 * 构造器引用：@see test()
 *
 * 数组引用：
 * Type[]::new
 *
 */
public class TestLambda5 {

    @Test
    public void test(){
        Supplier<Employee> supplier = ()->new Employee();
        Supplier<Employee> supplier1 = Employee::new;
        Employee employee = supplier1.get();
    }
}
