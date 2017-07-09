package com.kevinyin.lnetty.discard.handler;

import com.kevinyin.lnetty.discard.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class TimeServerHandle extends ChannelInboundHandlerAdapter{

//    private ByteBuf buf;
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//            buf = ctx.alloc().buffer(4);
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        buf.release();
//        buf = null;
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.write(new UnixTime());
        ctx.close();

//        ByteBuf m = (ByteBuf) msg;
//        buf.writeBytes(m);
//        m.release();
//        if(buf.readableBytes() >= 4){
//            byte[] buffer = new byte[buf.readableBytes()];
//            buf.readBytes(buffer);
//            String time = new String(buffer);
//            long currentTimeMillis = (Long.parseLong(new String(buffer)) - 220898800L) * 1000L;
//            System.out.println(new Date(currentTimeMillis));
//            ctx.close();
//        }
//        try {
//            long currentTimeMillis = (m.readUnsignedInt() - 220898800L) * 1000L;
//            System.out.println(currentTimeMillis);
//            ctx.close();
//        }finally {
//            m.release();
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
        ctx.close();
    }
}
