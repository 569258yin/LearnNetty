package com.kevinyin.lnetty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Created by kevinyin on 2017/7/14.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket>{
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf data = datagramPacket.content();
        int i = data.indexOf(0,data.readableBytes(),LogEvent.SEPARATOR);
        String filename = data.slice(0,i).toString(CharsetUtil.UTF_8);
        String logMsg = data.slice(i+1,data.readableBytes()).toString(CharsetUtil.UTF_8);

        LogEvent event = new LogEvent(datagramPacket.recipient(),filename,logMsg,System.currentTimeMillis());
        list.add(event);
    }
}
