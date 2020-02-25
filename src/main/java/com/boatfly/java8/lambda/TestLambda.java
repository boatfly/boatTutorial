package com.boatfly.java8.lambda;

import com.boatfly.java8.bean.Employee;
import com.boatfly.java8.filter.EmployeeAgeFilter;
import com.boatfly.java8.filter.MyFilter;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLambda {
    public static void main(String[] args) {

    }

    /**
     * 匿名内部类
     */
    @Test
    public void test() {
        Comparator<Integer> comparator = new Comparator<Integer>() { //@FunctionalInterface 函数接口 只有1个方法，可以有多个default
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
        treeSet.add(10);
        treeSet.add(2);
        treeSet.add(6);
        System.out.println(treeSet);
    }

    @Test
    public void test1() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(y, x);//@FunctionalInterface 定义的非default方法的样式进行改装
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
        treeSet.add(10);
        treeSet.add(2);
        treeSet.add(6);
        System.out.println(treeSet);
    }
    List<Employee> employees = Arrays.asList(
            new Employee("qinyy", 28, 16000),
            new Employee("guojh", 23, 13000),
            new Employee("may", 42, 13000)
    );
    //需求1.获取当前员工中年龄大于35岁的员工
    public List<Employee> filter(List<Employee> list) {
        List<Employee> ret = new ArrayList<>();
        for(Employee emp:list){
            if(emp.getAge()>35){
                ret.add(emp);
            }
        }
        return ret;
    }

    @Test
    public void test3(){
        List<Employee> ret = filter(employees);
        System.out.println(ret);
    }

    // 优化方式1 策略设计模式
    public List<Employee> refactor1(List<Employee> list, MyFilter<Employee> filter){
        List<Employee> ret = new ArrayList<>();
        for(Employee emp:list){
            if(filter.filter(emp)){
                ret.add(emp);
            }
        }
        return ret;
    }

    @Test
    public void test4(){
        List<Employee> ret = refactor1(employees,new EmployeeAgeFilter());
        System.out.println(ret);
    }

    //优化方式2 : stream + lambda
    @Test
    public void test5(){
        Stream<Employee> employeeStream = employees.stream().filter(t -> t.getAge() > 25).filter(t->t.getSalary()>15000);
        List<Employee> collect = employeeStream.collect(Collectors.toList());
        System.out.println(collect);
        List<Employee> collect1 = employees.stream().map(t -> t.setName(t.getName() + "abc")).collect(Collectors.toList());
        System.out.println(collect1);
    }

    @Test
    public void test6(){
        employees.stream().map(Employee::getName).forEach(System.out::println);
    }

}
