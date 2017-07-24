package com.kevinyin.lnetty.msgPackage.delimiterPackage;

import com.kevinyin.lnetty.msgPackage.unsafePackage.UnsafeTimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by kevinyin on 2017/7/3.
 */
public class Server {

    public static final String SPLIT = "$_";

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //主程序   boss  用于接受所有的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程  用于处理每个连接
        EventLoopGroup workGroup = new NioEventLoopGroup();

//        ChannelFactory

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    //unsafe
//                    .childandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            //加入自己的处理连接方式
//                            ch.pipeline().addLast(new UnsafeTimeServerHandler());
//                        }
//                    })
                    .childHandler(new ChildChannelHandler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定和开始接受连接
            ChannelFuture f = b.bind(port).sync();

            //主要关闭服务
            f.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    //增加LineBaseFrameDecoder
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ByteBuf delimiter = Unpooled.copiedBuffer(SPLIT.getBytes());
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024
                    , delimiter));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new DelimiterServerHandler());
        }
    }


    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new Server(port).run();
    }
}
