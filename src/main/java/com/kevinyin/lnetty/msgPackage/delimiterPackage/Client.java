package com.kevinyin.lnetty.msgPackage.delimiterPackage;

import com.kevinyin.lnetty.msgPackage.unsafePackage.UnsafeTimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class Client {
    public static final String SPLIT = "$_";

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
                    ByteBuf delimiter = Unpooled.copiedBuffer(SPLIT.getBytes());
                    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024
                        ,delimiter));
                    socketChannel.pipeline().addLast(new StringDecoder());

                    socketChannel.pipeline().addLast(new DelimiterClientHandler());
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
