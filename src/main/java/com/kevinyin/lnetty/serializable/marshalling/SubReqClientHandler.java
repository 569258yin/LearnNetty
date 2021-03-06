package com.kevinyin.lnetty.serializable.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private Object subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setSubReqID(i);
        req.setUserName("kevin");
        req.setProductName("Netty book For Marshalling");
        req.setAddress("上海市嘉定区");
        req.setPhoneNumber("183****");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server reponse : [" +msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
