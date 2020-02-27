package com.boatfly.java8.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 使用NIO完成网络通信的三个核心：
 * - channel
 *      java.nio.channels.Channel
 *        - SelectChannel
 *          - SocketChannel
 *          - ServerSocketChannel
 *          - DatagramChannel
 *
 *          - Pipe.SinkChannel
 *          - pipe.SourceChannel
 * - buffer
 * - selector 选择器，SelectChannle的多路复用器，用于监控SelectChanneld的IO状况。
 *
 * - Selectionkey:
 *  - 读 Selectionkey.OP_READ
 *  - 写 Selectionkey.OP_READ
 *  - 连接 Selectionkey.OP_READ
 *  - 接收 Selectionkey.OP_READ
 */
public class TestNonBlockingNIO {
    //客户端
    @Test
    public void client() {
        SocketChannel socketChannel = null;
        FileChannel inChannel = null;
        //1.获取通道
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9898));
            //切换成非阻塞模式
            socketChannel.configureBlocking(false);

            //2.分配指定大小的缓冲区域
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //3.发送数据：读取本地文件，并发送到服务器端
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String next = scanner.next();
//                String next = "abcd";
                System.out.println(next);
                buffer.put(("hello nio\n"+next).getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(socketChannel!=null&&socketChannel.isConnected())
                    socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //服务器端
    @Test
    public void server(){
        ServerSocketChannel ssChannel = null;
        FileChannel outChannel = null;
        try {
            //获取通道
            ssChannel = ServerSocketChannel.open();
            //切换到非阻塞模式
            ssChannel.configureBlocking(false);
            //绑定端口号
            ssChannel.bind(new InetSocketAddress(9898));

            //获取选择器
            Selector selector = Selector.open();
            //将通道注册到选择器上，并且制定监听事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT); //SelectionKey:

            //轮询式的获取选择器上已经"准备就绪"的事件
            while (selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if(next.isAcceptable()){ //连接就绪
                        SocketChannel clientChannel = ssChannel.accept();
                        clientChannel.configureBlocking(false);

                        //通道注册选择器
                        clientChannel.register(selector,SelectionKey.OP_WRITE);
                    }else if(next.isReadable()){ //读就绪

                    }else if(next.isWritable()){ //写就绪
                        SocketChannel channel = (SocketChannel)next.channel();
                        //读数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len=0;
                        while ((len=channel.read(buffer))>0){
                            buffer.flip();
                            System.out.println(new String(buffer.array(),0,len));
                            buffer.clear();
                        }
                    }
                }
                iterator.remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ssChannel.isOpen())
                    ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
