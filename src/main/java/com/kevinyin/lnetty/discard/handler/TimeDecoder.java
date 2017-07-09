package com.kevinyin.lnetty.discard.handler;

import com.kevinyin.lnetty.discard.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class TimeDecoder extends ByteToMessageDecoder{

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 4){
            return;
        }
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
    }
}
