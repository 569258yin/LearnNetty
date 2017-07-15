package com.kevinyin.lnetty.chat;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by kevinyin on 2017/7/14.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
//            String path = location.toURI() + "index.html";
            HttpRequestHandler.class.getResource("/");
            String path = HttpRequestHandler.class.getResource("/index.html").getPath();
            System.out.println(path);
            INDEX = new File(path);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to locate index.html",e);
        }
    }

    public HttpRequestHandler(String url){
        this.wsUri = url;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        if(wsUri.equalsIgnoreCase(fullHttpRequest.uri())){
            ctx.fireChannelRead(fullHttpRequest.retain());
        }else {
            if(HttpUtil.is100ContinueExpected(fullHttpRequest)){
                send100Continue(ctx);
            }
            RandomAccessFile file = new RandomAccessFile(INDEX,"r");

            HttpResponse response = new DefaultFullHttpResponse(fullHttpRequest.protocolVersion(),HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
            if (keepAlive) { //5
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response); //6
            if (ctx.pipeline().get(SslHandler.class) == null) { //7
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT); if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE); //9
            }
        }
    }
    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
