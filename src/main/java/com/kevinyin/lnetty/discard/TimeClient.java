package com.kevinyin.lnetty.discard;

import com.kevinyin.lnetty.discard.handler.TimeClientHandler;
import com.kevinyin.lnetty.discard.handler.TimeDecoder;
import com.kevinyin.lnetty.discard.handler.TimeServerHandle;
import com.kevinyin.lnetty.discard.handler.TimeEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8080;
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());
//                    socketChannel.pipeline().addLast(new TimeClientHandler());
                }
            });
            //start client
            ChannelFuture f = b.connect(host,port).sync();
            //wait until the connection is closed.
            f.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
