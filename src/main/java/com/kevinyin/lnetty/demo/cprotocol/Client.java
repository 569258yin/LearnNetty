package com.kevinyin.lnetty.demo.cprotocol;

import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMessageDecoder;
import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMessageEncoder;
import com.kevinyin.lnetty.demo.cprotocol.handler.HeartBeatReqHandler;
import com.kevinyin.lnetty.demo.cprotocol.handler.LoginAuthReqHandler;
import com.kevinyin.lnetty.demo.cprotocol.params.NettyConstant;
import com.kevinyin.lnetty.serializable.javaSeria.SubReqClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class Client {


    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    EventLoopGroup workGroup = new NioEventLoopGroup();
    public void connet(String host,int port) throws Exception{
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY,true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyMessageDecoder(1024 * 1024,
                            4,4,-8,0));
                    socketChannel.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
                    socketChannel.pipeline().addLast("ReadTimeOutHandler",new ReadTimeoutHandler(50));
                    socketChannel.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
                    socketChannel.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());

                }
            });
            //start client
            ChannelFuture f = b.connect(new InetSocketAddress(host,port)).sync();
            //wait until the connection is closed.
            f.channel().closeFuture().sync();

        } finally {

            //清除资源
            Thread.sleep(1000);

            //重新发起请求
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try {
                            connet(NettyConstant.REMOTEIP,NettyConstant.PORT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        new Client().connet(NettyConstant.REMOTEIP,NettyConstant.PORT);
    }

}
