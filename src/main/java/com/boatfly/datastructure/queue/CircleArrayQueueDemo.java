package com.boatfly.datastructure.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        // 测试数组模拟环形队列
        System.out.println("测试数组模拟环形队列");
        CircleArrayQueue queue = new CircleArrayQueue(4);
        char c;
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("a(add) : 队列添加数据");
            System.out.println("g(get) : 从队列取数据");
            System.out.println("h(head) : 获取队列头部数据");
            System.out.println("s(show) : 显示队列所有数据");
            System.out.println("e(exit) : 退出程序");
            // 接收字符
            c = scanner.next().charAt(0);
            switch (c) {
                case 'a':
                    System.out.println("请输入一个数：");
                    int val = scanner.nextInt();
                    queue.add(val);
                    System.out.printf("当前队列的头部指针：%d，尾部指针：%d\n", queue.getFront(), queue.getRear());
                    break;
                case 'g':
                    try {
                        int n = queue.get();
                        System.out.println("从队列取出数据：" + n);
                        System.out.printf("当前队列的头部指针：%d，尾部指针：%d\n", queue.getFront(), queue.getRear());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int n = queue.peek();
                        System.out.println("队列的头部数据：" + n);
                        System.out.printf("当前队列的头部指针：%d，尾部指针：%d\n", queue.getFront(), queue.getRear());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    queue.show();
                    System.out.printf("当前队列的头部指针：%d，尾部指针：%d\n", queue.getFront(), queue.getRear());
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("退出程序");
    }
}

/**
 * 环形队列
 */
class CircleArrayQueue {
    private int maxSize;
    private int front; //队列头
    private int rear; //队列尾
    private int[] arr;

    public CircleArrayQueue(int _maxSize) {
        this.maxSize = _maxSize;
        arr = new int[maxSize];
        front = 0;
        rear = 0;
    }

    public boolean isFull() {
        //return front == maxSize ? true : false;
        //return rear == maxSize - 1;
        //return (rear + 1) % maxSize == front;
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmplty() {
        //return front == -1;
        return front == rear;
    }

    public void add(Integer num) {
        if (!isFull()) {
            //front++;取数据时，才更新front值
            arr[rear] = num;
            rear = (rear + 1) % maxSize;
        } else {
            System.out.println("队列已满！");
            return;
        }
    }

    public int get() {
        if (isEmplty()) {
            throw new RuntimeException("队列为空，不能取数据！");
        }
        int temp = arr[front];
        front = (front + 1) % maxSize;
        return temp;
    }

    //队列有效数据个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    public int peek() {
        if (isEmplty()) {
            throw new RuntimeException("队列为空，不能取数据！");
        }
        return arr[front];
    }

    public void show() {
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }
}


