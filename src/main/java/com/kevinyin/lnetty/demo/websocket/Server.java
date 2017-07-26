package com.kevinyin.lnetty.demo.websocket;

import com.kevinyin.lnetty.demo.http.HttpFileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by kevinyin on 2017/7/3.
 */
public class Server {


    public static final String DEFAULT_URL = "/src/";

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
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定和开始接受连接
            ChannelFuture f = b.bind("127.0.0.1",port).sync();

            //主要关闭服务
            f.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            //http请求解码器
            socketChannel.pipeline().addLast("http-coder",new HttpServerCodec());
            //将多个消息转换成单一的FullHttpRequest  or FullHttpResponse
            socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
            //发送的的文件，而不占用较多内存
            socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
            socketChannel.pipeline().addLast("WebSocketServerHandler",new WebSocketServerHandler());
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
