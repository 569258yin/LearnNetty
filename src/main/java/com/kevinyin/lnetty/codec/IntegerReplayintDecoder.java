package com.kevinyin.lnetty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 无需判断字节数，但效率比直接集成ByteTomessage慢
 * Created by kevinyin on 2017/7/13.
 */
public class IntegerReplayintDecoder extends ReplayingDecoder<Void>{

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            list.add(internalBuffer().readInt());
    }
}
