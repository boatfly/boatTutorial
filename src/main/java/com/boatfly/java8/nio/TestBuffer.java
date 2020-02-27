package com.boatfly.java8.nio;

import org.junit.Test;
import scala.util.control.Exception;

import java.nio.ByteBuffer;

/**
 * JDK8之前是面向流
 * 面向缓冲区Buffer（操作缓冲区对数据进行处理），通过管道Channel进行传输
 * <p>
 * 缓冲区:Buufer
 * - 负责数据的存取
 * - 数组，用于存储不同数据类型的数据
 * - 根据不同类型（boolean除外）的数据，提供了不同类型的缓冲区
 * - ByteBuffer
 * - LongBuffer
 * - ...
 * <p>
 * 处理方式均一样，通过allocate()获取缓冲区
 * <p>
 * 缓冲区存取数据的两个核心方法：
 * get() 获取缓冲区的数据
 * put() 存入数据到缓冲区
 * <p>
 * 缓冲区的核心属性：
 * - capacity:容量，表示缓冲区中最大存储数据的容量，一旦声明不能改变；
 * - limit：界限，表示缓冲区中可以操作数据的大小；（limit后面的数据我们不能进行读写）
 * - position：位置，表示缓冲区中正在操作数据的位置；
 * - mark：标记，表示记录当前position的位置，可以通过reset()来恢复到mark的位置
 * <p>
 * 0<=mark<=position<=limit<=capacity
 *
 * 直接缓冲区allocateDirect() - os & 非直接缓冲区allocate() - jvm
 */
public class TestBuffer {

    @Test
    public void test2(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.isDirect());
    }

    @Test
    public void test() {
        String str = "boat";
        //1 分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("-------------allocate()----------------");
        System.out.println("position:" + byteBuffer.position());
        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("capacity:" + byteBuffer.capacity());

        System.out.println("-----------------put----------------------");
        byteBuffer.put(str.getBytes());
        System.out.println("position:" + byteBuffer.position());
        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("capacity:" + byteBuffer.capacity());

        System.out.println("-----------------flip：切换读数据模式----------------------");
        byteBuffer.flip();
        System.out.println("position:" + byteBuffer.position());
        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("capacity:" + byteBuffer.capacity());

        System.out.println("-----------------get 读数据模式----------------------");
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println("读取的数据为：" + new String(bytes, 0, bytes.length));
        System.out.println("position:" + byteBuffer.position());
        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("capacity:" + byteBuffer.capacity());

        //rewind 可重复读数据
        byteBuffer.rewind();

        //clear 清空缓冲区 ,只是指针归为，但是里面的数据还存在
        byteBuffer.clear();

    }
}
