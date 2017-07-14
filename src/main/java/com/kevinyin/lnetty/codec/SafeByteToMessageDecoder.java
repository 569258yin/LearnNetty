package com.kevinyin.lnetty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder{
    public static final int MAX_FRAME_SIZE = 1024;

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readble = byteBuf.readableBytes();
        if(readble > MAX_FRAME_SIZE){
            byteBuf.skipBytes(readble);
            throw new TooLongFrameException("Frame too big");
        }
        //doSomeThing

    }
}
