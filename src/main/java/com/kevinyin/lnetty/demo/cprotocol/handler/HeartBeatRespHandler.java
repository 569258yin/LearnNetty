package com.kevinyin.lnetty.demo.cprotocol.handler;

import com.kevinyin.lnetty.demo.cprotocol.model.Header;
import com.kevinyin.lnetty.demo.cprotocol.model.NettyMessage;
import com.kevinyin.lnetty.demo.cprotocol.params.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class HeartBeatRespHandler extends SimpleChannelInboundHandler<NettyMessage>{



    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage message) throws Exception {
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.Value()){
            System.out.println("Receive client heart beat message : --->" + message);
            NettyMessage heartBeat = buildHeatBeat();
            System.out.println("Send heart beat response message to client : ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(message);
        }
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.Value());
        message.setHeader(header);
        return message;
    }
}
