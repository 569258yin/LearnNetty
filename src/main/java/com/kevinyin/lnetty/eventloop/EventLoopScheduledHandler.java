package com.kevinyin.lnetty.eventloop;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by kevinyin on 2017/7/15.
 */
public class EventLoopScheduledHandler extends SimpleChannelInboundHandler<String>{


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String aVoid) throws Exception {
        Channel channel = channelHandlerContext.channel();

//        channel.eventLoop().schedule(new Runnable() {
//            public void run() {
//                System.out.println("after 60 s run");
//            }
//        },60, TimeUnit.SECONDS);

        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("Aftert 10 s run,while 60s  ");
            }
        },10,60,TimeUnit.SECONDS);
    }
}
