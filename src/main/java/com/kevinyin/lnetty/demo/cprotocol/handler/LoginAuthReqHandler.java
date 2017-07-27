package com.kevinyin.lnetty.demo.cprotocol.handler;


import com.kevinyin.lnetty.demo.cprotocol.model.Header;
import com.kevinyin.lnetty.demo.cprotocol.model.NettyMessage;
import com.kevinyin.lnetty.demo.cprotocol.params.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class LoginAuthReqHandler extends SimpleChannelInboundHandler<NettyMessage> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }



    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage message) throws Exception {
        //如果是握手响应消息，判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.Value()){
            byte loginResult = Byte.parseByte(message.getBody().toString());
            if (loginResult != (byte) 0){
                //握手失败
                ctx.close();
            } else {
                System.out.println("Login is ok : " + message);
                ctx.fireChannelRead(message);
            }
        } else {
            ctx.fireChannelRead(message);
        }
    }

    private Object buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.Value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
