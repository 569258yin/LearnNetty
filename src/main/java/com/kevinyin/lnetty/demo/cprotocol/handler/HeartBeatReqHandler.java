package com.kevinyin.lnetty.demo.cprotocol.handler;

import com.kevinyin.lnetty.demo.cprotocol.model.Header;
import com.kevinyin.lnetty.demo.cprotocol.model.NettyMessage;
import com.kevinyin.lnetty.demo.cprotocol.params.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class HeartBeatReqHandler extends SimpleChannelInboundHandler<NettyMessage>{

    private volatile ScheduledFuture<?> heartBeat;

    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage message) throws Exception {
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.Value()){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx)
                ,0,5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.Value()){
            System.out.println("Client receive server heart beat message : --->" + message);
            ctx.fireChannelRead(message);
        }
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        public void run() {
            NettyMessage heatBeat = buildHeatBeat();
            System.out.println("Client send beat message to server : --->" + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private NettyMessage buildHeatBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.Value());
            message.setHeader(header);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
