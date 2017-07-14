package com.kevinyin.lnetty.demo.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel>{

    private final SSLContext context;
    private final boolean startTls;

    public SslChannelInitializer(SSLContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    protected void initChannel(Channel channel) throws Exception {
        SSLEngine engine = context.createSSLEngine();
//        engine.setUseClientMode();
//        channel.pipeline().addFirst("SSL",new SSLContext(engine,startTls));
    }
}
