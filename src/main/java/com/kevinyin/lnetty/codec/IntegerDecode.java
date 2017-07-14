package com.kevinyin.lnetty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 *
 * Created by kevinyin on 2017/7/13.
 */
public class IntegerDecode extends ByteToMessageDecoder{

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(internalBuffer().readableBytes() > 4){
            list.add(internalBuffer().readInt());
        }
    }
}
