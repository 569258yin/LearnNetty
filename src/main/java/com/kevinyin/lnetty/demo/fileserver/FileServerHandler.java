package com.kevinyin.lnetty.demo.fileserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String>{

    public static final String CR = System.getProperty("line.separator");

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        File file = new File(s);
        if (file.exists()){
            if (!file.isFile()){
                ctx.writeAndFlush("Not a file: "+ file + CR);
                return;
            }
            ctx.write(file + " " + file.length() + CR);
            RandomAccessFile randomAccessFile = new RandomAccessFile(s,"r");
            FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(),0,
                    randomAccessFile.length());
            ctx.write(region);
            ctx.writeAndFlush(CR);
            randomAccessFile.close();
        }else {
            ctx.writeAndFlush("File not found: " + file + CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
