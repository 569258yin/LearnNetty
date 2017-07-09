package com.kevinyin.lnetty.discard.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 处理服务器通道
 * Created by kevinyin on 2017/7/3.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        //以静默的方式丢弃
//        ((ByteBuf) msg) .release();
//一般做法
        ByteBuf in = (ByteBuf) msg;
        try {
            //接受处理消息
            while (in.isReadable()){
                System.out.println((char)in.readByte());
                System.out.flush();
            }
            //回写响应数据
            ctx.write("Response By Server");
            ctx.flush();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        //当异常时关闭
        ctx.close();
    }
}
