package com.kevinyin.lnetty.TestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 处理一定长度的消息
 * Created by kevinyin on 2017/7/13.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder{
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0){
            throw new IllegalArgumentException("frame must be a positive integer:" + frameLength);
        }
        this.frameLength = frameLength;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            if (byteBuf.readableBytes() >= frameLength){
                ByteBuf buf = byteBuf.readBytes(frameLength);
                list.add(buf);
            }
    }
}
