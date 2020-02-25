package com.boatfly.java.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.minidev.json.JSONUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 链式编程，流式计算
 * <p>
 * java.util.function
 * java.util.stream
 * <p>
 * Stream是数据渠道，用于操作数据源（集合、数组）所生成的元素序列。
 * 源头->中间流水线->结果  类似于spark里的操作算子和行动算子
 */
public class StreamDemo {
    public static void main(String[] args) {
        User user = new User();
        user.setId(1).setUserName("boat").setAge(21);//链式编程

        User u1 = new User(11, "a", 21);
        User u2 = new User(12, "b", 22);
        User u3 = new User(13, "c", 23);
        User u4 = new User(14, "d", 24);
        User u5 = new User(15, "e", 25);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        //流式计算，java就是sql，sql就是java
        Stream<User> userStream = list.stream()
                .filter(user1 -> user1.getId() % 2 == 0)
                .filter(user1 -> user1.getAge() > 21)
                .map(user1 -> user1.setUserName(user1.getUserName().toUpperCase()))
                .sorted((o1, o2) -> {
                    return o2.getUserName().compareTo(o1.getUserName());
                }).limit(1);
        //userStream.collect(Collectors.toList());//stream->list
        userStream.forEach(System.out::println);
    }
}

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
class User {
    private int id;
    private String userName;
    private int age;
}
