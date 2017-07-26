package com.kevinyin.lnetty.demo.ssl;

import io.netty.channel.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel>{

    private final File file;
    private final SSLContext sslContext;

    public ChunkedWriteHandlerInitializer(File file, SSLContext sslContext) {
        this.file = file;
        this.sslContext = sslContext;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new SslHandler(sslContext.createSSLEngine()));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WriteStreamHandler());
    }

    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
