package com.kevinyin.lnetty.demo;

import io.netty.channel.*;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class FileChannelHandler extends ChannelOutboundHandlerAdapter{

    private File file;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        FileInputStream in = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(in.getChannel(),0,file.length());

        ctx.channel().writeAndFlush(region).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(!channelFuture.isSuccess()){
                    Throwable cause = channelFuture.cause();
                    //Do something
                }
            }
        });
    }
}
