package com.boatfly.java8.nio;

/**
 * 通道：channel  java.nio.channels
 * 只能和Buffer进行交互
 * - 负责源节点和目标节点的连接。负责缓冲区数据的传输。
 * java.nio.channels.Channel
 *  - FileChannel
 *  - SocketChannel -tcp
 *  - ServerSocketChannel -tcp
 *  - DatagramChannel -udp
 *
 *  获取通道：getChannel()
 *  本地io:
 *  - FileInputStream/FileOutputStream
 *  - RandomAccessFile
 *  网络io:
 *  - Socket
 *  - ServerSocket
 *  - DatagramSocket
 *
 */
public class TestChannel {
}
