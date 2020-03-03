package com.boatfly.datastructure.queue;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.apache.commons.math3.fitting.leastsquares.EvaluationRmsChecker;
import org.junit.Test;

/**
 * 队列是一个有序列表，可以用数组和链表实现
 * FIFO
 */
public class ArrayQueueDemo {
    @Test
    public void test() {
        ArrayQueue arrayQueue = new ArrayQueue(10);
        for (int i = 0; i < 10; i++) {
            arrayQueue.add(i);
        }
        arrayQueue.show();
        System.out.println("队列头的数据=" + arrayQueue.peek());
        for (int i = 0; i < 10; i++) {
            System.out.println(arrayQueue.get());
        }
    }
}

class ArrayQueue {
    private int maxSize;
    private int front; //队列头
    private int rear; //队列尾
    private int[] arr;

    public ArrayQueue(int _maxSize) {
        this.maxSize = _maxSize;
        arr = new int[maxSize];
        front = -1;
        rear = -1;
    }

    public boolean isFull() {
        //return front == maxSize ? true : false;
        return rear == maxSize - 1;
    }

    public boolean isEmplty() {
        //return front == -1;
        return front == rear;
    }

    public void add(Integer num) {
        if (!isFull()) {
            //front++;取数据时，才更新front值
            rear++;
            arr[rear] = num;
        } else {
            System.out.println("队列已满！");
            return;
        }
    }

    public int get() {
        if (isEmplty()) {
            throw new RuntimeException("队列为空，不能取数据！");
        }
        front++;
        return arr[front];
    }

    public int peek() {
        if (isEmplty()) {
            throw new RuntimeException("队列为空，不能取数据！");
        }
        return arr[front + 1];
    }

    public void show() {
        for (int data : arr) {
            System.out.println(data);
        }
    }
}
