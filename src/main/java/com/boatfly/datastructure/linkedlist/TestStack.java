package com.boatfly.datastructure.linkedlist;

import org.junit.Test;

import java.util.Stack;

public class TestStack {
    @Test
    public void test() {
        Stack<String> strings = new Stack<>();
        strings.add("spark");
        strings.add("scala");
        strings.add("ethereum");
        strings.add("go");
        while (strings.size() > 0) {
            System.out.println(strings.pop());
        }
    }
}
