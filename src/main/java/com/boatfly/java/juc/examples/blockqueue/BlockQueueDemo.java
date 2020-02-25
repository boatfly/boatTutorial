package com.boatfly.java.juc.examples.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列：
 * - 当队列是空的，从队列中获取元素，将会被阻塞
 * - 当队列是满的，从队列中添加元素，将会被阻塞
 * - Deque 双向的
 * <p>
 * 抛出异常组：add() remove()
 * 特殊值组：offer() poll()
 * 阻塞：put() take()
 * 超时退出：offer(e,time,unit) poll(time,unit)
 */
public class BlockQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        boolean ok = queue.add("a"); //当queue满的时候，会抛异常。  阻塞：put()
        System.out.println(queue.remove());//当queue空的时候，会抛异常。 阻塞：take()
//        queue.put("aas");
//        queue.offer("asdf");
//        queue.of
    }
}
