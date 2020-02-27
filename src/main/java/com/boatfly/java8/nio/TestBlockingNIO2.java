package com.boatfly.java8.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 相较于TestBlockingNIO,多了client要接收server的反馈
 */
public class TestBlockingNIO2 {
    //客户端
    @Test
    public void client() {
        SocketChannel socketChannel = null;
        FileChannel inChannel = null;
        //1.获取通道
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9898));
            inChannel = FileChannel.open(Paths.get("img/jvm.png"), StandardOpenOption.READ);

            //2.分配指定大小的缓冲区域
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //3.发送数据：读取本地文件，并发送到服务器端
            while (inChannel.read(buffer)!=-1){
                buffer.flip();//切换到读模式
                socketChannel.write(buffer);
                buffer.clear();
            }

            socketChannel.shutdownOutput();

            //接收服务端的反馈
            int len=0;
            while ((len = socketChannel.read(buffer))!=-1){
                buffer.flip();
                System.out.println(new String(buffer.array(),0,len));
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(socketChannel.isConnected())
                    socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(inChannel.isOpen())
                    inChannel.close();
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
            //绑定端口号
            ssChannel.bind(new InetSocketAddress(9898));
            //获取客户端连接
            SocketChannel client = ssChannel.accept();
            //读取客户端发送的数据
            outChannel = FileChannel.open(Paths.get("img/jvm3.png"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);
            ByteBuffer buffer= ByteBuffer.allocate(1024);
            while (client.read(buffer)!=-1){
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            // 发送反馈给客户端
            buffer.put("数据接收成功！".getBytes());
            buffer.flip();
            client.write(buffer);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ssChannel.isOpen())
                    ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outChannel.isOpen())
                    outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
