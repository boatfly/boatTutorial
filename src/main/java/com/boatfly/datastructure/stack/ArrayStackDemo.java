package com.boatfly.datastructure.stack;

public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(10);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(13);
        arrayStack.push(14);
        arrayStack.push(51);
        arrayStack.push(166);
        arrayStack.list();
    }
}

class ArrayStack {
    private int maxSize;
    private int[] stack;
    private int top = -1;

    public ArrayStack(int maxSize) {
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
}
