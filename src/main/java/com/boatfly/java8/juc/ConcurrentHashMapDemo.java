package com.boatfly.java8.juc;

/**
 * HashMap 线程不安全
 * Hashtable 线程安全，但是每次都会锁住全部table
 * ConcurrentHashMap concurrentLevel==默认size=16，分成16个段（segment），每次只所一个segment，提高并发性
 * 锁分段 机制
 */
public class ConcurrentHashMapDemo {
}
