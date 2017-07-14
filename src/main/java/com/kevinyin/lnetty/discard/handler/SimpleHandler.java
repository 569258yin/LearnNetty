package com.kevinyin.lnetty.discard.handler;

import com.kevinyin.lnetty.discard.bean.GetDataMessage;
import com.kevinyin.lnetty.discard.bean.LoginMessage;
import com.kevinyin.lnetty.discard.bean.Message;
import com.kevinyin.lnetty.discard.bean.UnixTime;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class SimpleHandler extends SimpleChannelInboundHandler<Message>{

    private boolean loggedIn;

    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
//        Channel ch = ctx.channel();
//        if (message instanceof LoginMessage) {
//            authenticate((LoginMessage) message);
//            loggedIn = true;
//        } else if(message instanceof GetDataMessage) {
//            if (loggedIn) {
//                ch.write(fetchSecret((GetDataMessage) message));
//            } else {
//                fail();
//            }
//        }

    }
}
