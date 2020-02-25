package com.boatfly.java8.optional;

import com.boatfly.java8.bean.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * java.util.Optional 是一个容器类，代表一个值存在或者不存在，可以避免空指针异常
 */
public class TestOptional {
    @Test
    public void test(){
        Optional<Employee> employee = Optional.of(new Employee());
        System.out.println(employee.get());

        Optional<Employee> employee1 = Optional.of(null);
        System.out.println(employee1.get());
    }
}
