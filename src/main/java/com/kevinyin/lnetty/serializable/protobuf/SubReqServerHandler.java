package com.kevinyin.lnetty.serializable.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req  = (SubscribeReqProto.SubscribeReq) msg;
        if ("kevin".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscibe req : ["
                + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp
                .newBuilder();
        builder.setSubReqID(1);
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book ordeed success");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
