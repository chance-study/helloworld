package org.chance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-16 16:12:57
 */
public class NioDemo {

    private static final int port = 8088;

    private static Selector selector;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10, 1000, TimeUnit.MILLISECONDS, new LinkedTransferQueue<>(), new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {

        try {
            // 创建通道ServerSocketChannel
            ServerSocketChannel open = ServerSocketChannel.open();
            // 将通道设置为非阻塞
            open.configureBlocking(false);

            // 绑定到指定的端口上
            open.bind(new InetSocketAddress(port));

            // 通道管理器(Selector)
            selector = Selector.open();

            /**
             * 将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_ACCEPT事件
             * 注册该事件后，当事件到达的时候，selector.select()会返回，
             * 如果事件没有到达selector.select()会一直阻塞。
             */
            open.register(selector, SelectionKey.OP_ACCEPT);

            // 循环处理
            while (true) {
                /**
                 * 当注册事件到达时，方法返回，否则该方法会一直阻塞
                 * 该Selector的select()方法将会返回大于0的整数，该整数值就表示该Selector上有多少个Channel具有可用的IO操作
                 */
                int select = selector.select();
                System.out.println("当前有 " + select + " 个channel可以操作");
                if (select == 0) {
                    continue;
                }

                // 一个SelectionKey对应一个就绪的通道
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    // 获取事件
                    SelectionKey key = iterator.next();

                    // 移除事件，避免重复处理
                    iterator.remove();

                    // 客户端请求连接事件，接受客户端连接就绪
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        // 监听到读事件，对读事件进行处理
                        threadPoolExecutor.submit(new NioServerHandler(key));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理客户端连接成功事件
     *
     * @param key
     */
    public static void accept(SelectionKey key) {
        try {
            // 获取客户端连接通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);

            // 给通道设置读事件，客户端监听到读事件后，进行读取操作
            sc.register(selector, SelectionKey.OP_READ);
            System.out.println("accept a client : " + sc.socket().getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听到读事件，读取客户端发送过来的消息
     */
    private static class NioServerHandler implements Runnable {

        private SelectionKey selectionKey;

        public NioServerHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            try {
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    // 从通道读取数据到缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    // 输出客户端发送过来的消息
                    socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println("收到客户端" + socketChannel.socket().getInetAddress().getHostName() + "的数据：" + new String(buffer.array()));

                    //将数据添加到key中
                    ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());

                    // 将消息回送给客户端
                    socketChannel.write(outBuffer);
                    selectionKey.cancel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
