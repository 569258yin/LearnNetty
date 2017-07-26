package com.kevinyin.lnetty.demo.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;


/**
 * Created by kevinyin on 2017/7/26.
 */
public class ChineseProverServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {"只是功夫深，铁棒磨成针","洛阳亲友如想问，一片冰心在玉壶",
            "举头望明月，低头思故乡","野火烧不尽，春风吹又生"};
    private String nextQuote(){
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if ("谚语字典查询？".equals(req)){
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                "谚语字典结果： "+ nextQuote(),CharsetUtil.UTF_8
            ),packet.sender()));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
