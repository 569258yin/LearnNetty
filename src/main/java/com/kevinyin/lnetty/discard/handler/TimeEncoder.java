package com.kevinyin.lnetty.discard.handler;

import com.kevinyin.lnetty.discard.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter{



    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int) m.getValue());
        ctx.write(encoded,promise);
    }
}
