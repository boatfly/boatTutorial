package com.boatfly.java8.juc.volatiles;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子性
 * i++的原子性问题
 *
 * java.util.concurrent.atomic 提供了大量的原子变量
 * - AtomicLong 中的value，都用volatile修饰，保证内存可见性
 * - CAS（compare and swap 硬件对于并发操作共享数据的支持） 算法 保证数据的原子性
 *   - CAS包含了三个操作数：
 *     - 内存值 V
 *     - 预估值 A
 *     - 更新值 B
 *     - 当且仅当 V==A 时V=8,否则将什么操作都不做。  很像乐观锁
 */
public class TestAtomic {
//    AtomicLong
}
