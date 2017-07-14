package com.kevinyin.lnetty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class LineDecoder extends LineBasedFrameDecoder{
    public LineDecoder(int maxLength) {
        super(maxLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        return super.decode(ctx, buffer);
    }
}
