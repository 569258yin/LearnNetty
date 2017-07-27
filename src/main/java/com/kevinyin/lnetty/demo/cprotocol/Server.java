package com.kevinyin.lnetty.demo.cprotocol;

import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMessageDecoder;
import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMessageEncoder;
import com.kevinyin.lnetty.demo.cprotocol.handler.HeartBeatReqHandler;
import com.kevinyin.lnetty.demo.cprotocol.handler.HeartBeatRespHandler;
import com.kevinyin.lnetty.demo.cprotocol.handler.LoginAuthReqHandler;
import com.kevinyin.lnetty.demo.cprotocol.handler.LoginAuthRespHandler;
import com.kevinyin.lnetty.demo.cprotocol.params.NettyConstant;
import com.kevinyin.lnetty.serializable.javaSeria.SubReqServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by kevinyin on 2017/7/3.
 */
public class Server {

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
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler())
                    .option(ChannelOption.SO_BACKLOG, 128);
            //绑定和开始接受连接
            ChannelFuture f = b.bind(NettyConstant.REMOTEIP,NettyConstant.PORT).sync();

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
            socketChannel.pipeline().addLast(new NettyMessageDecoder(1024 * 1024,
                    4,4,-8,0));
            socketChannel.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
            socketChannel.pipeline().addLast("ReadTimeOutHandler",new ReadTimeoutHandler(50));
            socketChannel.pipeline().addLast("LoginAuthHandler",new LoginAuthRespHandler());
            socketChannel.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
        }
    }


    public static void main(String[] args) throws Exception {
        new Server().run();
    }
}
