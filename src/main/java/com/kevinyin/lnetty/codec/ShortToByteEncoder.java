package com.kevinyin.lnetty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short>{

    protected void encode(ChannelHandlerContext channelHandlerContext, Short aShort, ByteBuf byteBuf) throws Exception {
        byteBuf.writeShort(aShort);
    }

//    WebSocket08FrameEncoder
}
