package com.kevinyin.lnetty.demo.cprotocol.handler;

import com.kevinyin.lnetty.demo.cprotocol.model.Header;
import com.kevinyin.lnetty.demo.cprotocol.model.NettyMessage;
import com.kevinyin.lnetty.demo.cprotocol.params.MessageType;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class LoginAuthRespHandler extends SimpleChannelInboundHandler<NettyMessage> {

    private Map<String, Boolean> nodeChcek = new ConcurrentHashMap<String, Boolean>();
    private String[] whitekList = {"127.0.0.1", "192.168.1.104"};

    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage message) throws Exception {
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.Value()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if (nodeChcek.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for (String WIP :
                        whitekList) {
                    if (WIP.equals(ip)) {
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk ? buildResponse((byte) 0) : buildResponse((byte) -1);
                if (isOk) {
                    nodeChcek.put(nodeIndex, true);
                }
            }
            System.out.println("The login response is : " + loginResp + " body [" + loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(message);

        }
    }

    private NettyMessage buildResponse(byte b) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.Value());
        message.setHeader(header);
        message.setBody(b);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeChcek.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
