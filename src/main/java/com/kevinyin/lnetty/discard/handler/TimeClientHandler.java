package com.kevinyin.lnetty.discard.handler;

import com.kevinyin.lnetty.discard.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//        final ByteBuf time = ctx.alloc().buffer(4);
//        long currentTime = (System.currentTimeMillis() / 1000L + 2208988800L);
//        System.out.println(new Date(currentTime));
//        time.writeInt((int) currentTime);
//
//        final ChannelFuture f = ctx.writeAndFlush(time);
//        f.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                assert  f == channelFuture;
//                ctx.close();
//            }
//        });
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();

//        ByteBuf m = (ByteBuf) msg;
//        buf.writeBytes(m);
//        m.release();
//        if(buf.readableBytes() >= 4){
//            long currentTimeMillis = (m.readUnsignedInt() - 220898800L) * 1000L;
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
