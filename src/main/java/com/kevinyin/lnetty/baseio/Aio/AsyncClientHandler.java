package com.kevinyin.lnetty.baseio.Aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class AsyncClientHandler implements CompletionHandler<Void,AsyncClientHandler>,Runnable{

    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        latch = new CountDownLatch(1);
        client.connect(new InetSocketAddress(host,port),this,this);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void completed(Void result, AsyncClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuff = ByteBuffer.allocate(req.length);
        writeBuff.put(req);
        writeBuff.flip();
        client.write(writeBuff, writeBuff, new CompletionHandler<Integer, ByteBuffer>() {
            public void completed(Integer result, final ByteBuffer buffer) {
                if (buffer.hasRemaining()){
                    client.write(buffer,buffer,this);
                }else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        public void completed(Integer result, ByteBuffer attachment) {
                            if (attachment.hasRemaining()){
                                attachment.flip();
                                byte[] bytes = new byte[attachment.remaining()];
                                attachment.get(bytes);
                                String body = "";
                                try {
                                    body = new String(bytes,"utf-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Now id :" +body);
                                latch.countDown();
                            }
                        }

                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                client.close();
                                latch.countDown();
                            } catch (IOException e) {
                            }
                        }
                    });
                }
            }

            public void failed(Throwable exc, ByteBuffer buffer) {
                try {
                    client.close();
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void failed(Throwable exc, AsyncClientHandler attachment) {
        exc.printStackTrace();
        try {
            client.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
