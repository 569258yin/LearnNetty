package com.kevinyin.lnetty.baseio.Aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 *
 * Created by kevinyin on 2017/7/24.
 */
public class AsyncServerHandler implements Runnable{

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel
                    .open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void doAccept() {
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
