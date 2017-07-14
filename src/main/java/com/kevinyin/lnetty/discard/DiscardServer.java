package com.kevinyin.lnetty.discard;

import com.kevinyin.lnetty.discard.handler.TimeDecoder;
import com.kevinyin.lnetty.discard.handler.TimeClientHandler;
import com.kevinyin.lnetty.discard.handler.TimeEncoder;
import com.kevinyin.lnetty.discard.handler.TimeServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by kevinyin on 2017/7/3.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception{
        //主程序   boss  用于接受所有的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程  用于处理每个连接
        EventLoopGroup workGroup = new NioEventLoopGroup();

//        ChannelFactory

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //加入自己的处理连接方式
//                            ch.pipeline().addLast(new DiscardServerHandler());
//                            ch.pipeline().addLast(new TimeServerHandle());
                            ch.pipeline().addLast(new TimeEncoder(),new TimeServerHandle());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            //绑定和开始接受连接
            ChannelFuture f = b.bind(port).sync();

            //主要关闭服务
            f.channel().closeFuture().sync();

        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
