package com.boatfly.datastructure.stack;

public class Calculator {
    public static void main(String[] args) {

    }
}

class ArrayStack2 {
    private int maxSize;
    private int[] stack;
    private int top = -1;

    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmplty() {
        return top == -1;
    }

    public void push(int number) {
        if (isFull()) {
            System.out.println("stack is full.");
            return;
        }
        stack[++top] = number;
        ;
    }

    public int pop() {
        if (isEmplty()) {
            System.out.println("static is empty.");
            throw new RuntimeException("static is empty.");
        }
        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if (isEmplty()) {
            System.out.println("static is empty.");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    //操作符优先级
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;//other
        }
    }

    //判断是否是运算符
    public boolean isAction(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    public int cal(int x,int y,int oper){
        int res = 0;


        return res;
    }
}
