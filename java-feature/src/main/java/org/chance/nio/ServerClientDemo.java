package org.chance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-18 15:17:07
 */
public class ServerClientDemo {

    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8090;
    private static final int TIMEOUT = 3000;

    private static class Server {

        public static void main(String[] args) {
            selector();
        }

        public static void selector() {
            Selector selector = null;
            ServerSocketChannel ssc = null;
            try {
                // 打开一个Slectore
                selector = Selector.open();
                // 打开一个Channel
                ssc = ServerSocketChannel.open();
                // 将Channel绑定端口
                ssc.socket().bind(new InetSocketAddress(PORT));
                // 设置Channel为非阻塞，如果设置为阻塞，其实和BIO差不多了。
                ssc.configureBlocking(false);
                // 向selector中注册Channel和感兴趣的事件
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // selector监听事件，select会被阻塞，直到selector监听的channel中有事件发生或者超时，会返回一个事件数量
                    //TIMEOUT就是超时时间，selector初始化的时候会添加一个用于主动唤醒的pipe，待会源码分析会说
                    if (selector.select(TIMEOUT) == 0) {
//                        System.out.println("==");
                        continue;
                    }
                    /**
                     * SelectionKey的组成是selector和Channel
                     * 有事件发生的channel会被包装成selectionKey添加到selector的publicSelectedKeys属性中
                     * publicSelectedKeys是SelectionKey的Set集合
                     *下面这一部分遍历，就是遍历有事件的channel
                     */
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }
                        if (key.isReadable()) {
                            handleRead(key);
                        }
                        if (key.isValid() && key.isWritable()) {
                            handleWrite(key);
                        }
                        if (key.isValid() && key.isConnectable()) {
                            System.out.println("isConnectable = true");
                        }
                        //每次使用完，必须将该SelectionKey移除，否则会一直存储在publicSelectedKeys中
                        //下一次遍历又会重复处理
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (selector != null) {
                        selector.close();
                    }
                    if (ssc != null) {
                        ssc.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void handleAccept(SelectionKey key) throws IOException {
            System.out.println("客户端连接事件");
            ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssChannel.accept();
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
            if (sc.finishConnect()) {
                System.out.println("客户端完成连接");
            }
        }

        public static void handleRead(SelectionKey key) throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
//            ByteBuffer buf = (ByteBuffer) key.attachment();
//            long bytesRead = sc.read(buf);
//            while (bytesRead > 0) {
//                buf.flip();
//                while (buf.hasRemaining()) {
//                    System.out.print((char) buf.get());
//                }
//                System.out.println();
//                buf.clear();
//                bytesRead = sc.read(buf);
//            }

            ByteBuffer buf = ByteBuffer.allocate(1024);

            try {
                int read = sc.read(buf);
                if (read == -1) {
                    // 当客户端主动切断链接的时候，读事件仍然起作用，该情况下返回-1
                    sc.close();
                }
                System.out.println("收到的消息:" + new String(buf.array(), 0, read));

                // 响应客户端  这里可有可无
                buf.clear();
                buf.put("已收到消息".getBytes());
                // 将缓冲区各标志复位，因为向里面put了数据标志被改变要想从中读取数据发向服务器，就要复位
                buf.flip();
                sc.write(buf);
                // 设置监听事件的集合 这里把写入事件加入
//              key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
//              System.out.println("服务器向客户端发送已确认消息");
            } catch (IOException e) {
                sc.close();
            }
        }

        public static void handleWrite(SelectionKey key) throws IOException {

            System.out.println("触发往客户端写入数据事件");

//            ByteBuffer buf = (ByteBuffer) key.attachment();
//            buf.flip();
//            SocketChannel sc = (SocketChannel) key.channel();
//            while (buf.hasRemaining()) {
//                sc.write(buf);
//            }
//            buf.compact();

            // 发送完了就取消监听写事件，否则会无限循环触发写事件
            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);

        }
    }

    private static class Client {

        public static void main(String[] args) {
            client();
        }

        public static void client() {
            // 申请一块空间
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = null;
            Thread.currentThread().setName("client");
            try {
                // 打开一个Channel
                socketChannel = SocketChannel.open();
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                //连接IP和端口号
                socketChannel.connect(new InetSocketAddress("127.0.0.1", PORT));
                if (socketChannel.finishConnect()) {
                    int i = 0;
                    Random random = new Random();
                    while (true) {
                        // 为了不让消息发送太快，每发一条睡1s
                        TimeUnit.SECONDS.sleep(random.nextInt(60));
                        String info = Thread.currentThread().getName() + ":I'm " + i++ + "-th information from client";
                        //清空Buffer
                        buffer.clear();
                        //写入到Buffer中
                        buffer.put(info.getBytes());
                        //进行flip操作，为了下面可以将buffer中数据读取到channel中。
                        buffer.flip();
                        // 将buffer中的数据写入到channel中
                        while (buffer.hasRemaining()) {
                            System.out.println(Thread.currentThread().getName() + ":" + buffer);
                            int write = socketChannel.write(buffer);
                            System.out.println(Thread.currentThread().getName() + ":" + write);
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socketChannel != null) {
                        socketChannel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static class AcceptableClient {

        public static void main(String[] args) throws IOException {

            //打开选择器
            Selector selector = Selector.open();
            //打开通道
            SocketChannel socketChannel = SocketChannel.open();
            //配置非阻塞模型
            socketChannel.configureBlocking(false);
            //连接远程主机
            socketChannel.connect(new InetSocketAddress("127.0.0.1", PORT));
            //注册事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

            //循环处理
            new Thread(() -> {
                while (true) {
                    try {
                        selector.select();
                        Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = keys.iterator();
                        while (iter.hasNext()) {
                            SelectionKey key = iter.next();
                            if (key.isConnectable()) {
                                //连接建立或者连接建立不成功
                                SocketChannel channel = (SocketChannel) key.channel();
                                //完成连接的建立
                                if (channel.finishConnect()) {
                                    System.out.println("完成连接");
                                }
                            } else if (key.isReadable()) {
                                SocketChannel channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.clear();
                                channel.read(buffer);
                                System.out.println("客户端收到消息:" + new String(buffer.array()));
                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            } else if (key.isWritable()) {
                                System.out.println("客户端向服务端写入数据");
                                // 设置监听事件的集合 这里把写入事件加入
                                key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                            }
                            iter.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请输入要发送的字符串");
                String str = scanner.nextLine();
                String writeStr = "acceptable_client:" + str;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(writeStr.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
            }
        }
    }

}
