package com.kevinyin.lnetty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by kevinyin on 2017/7/14.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent>{
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(logEvent.getReceived());
        builder.append("[");
        builder.append(logEvent.getSource().toString());
        builder.append("] [");
        builder.append(logEvent.getLogFile());
        builder.append("]");
        builder.append(logEvent.getMsg());

        System.out.println(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
