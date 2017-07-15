package com.kevinyin.lnetty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * Created by kevinyin on 2017/7/14.
 */
public class LogEventBroadcaster {
    private final Bootstrap bootstrap;
    private final File file;
    private final EventLoopGroup group;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LogEventEncoder(address));

        this.file = file;
    }

    public void run() throws IOException{
        Channel ch = bootstrap.bind(0).syncUninterruptibly().channel();
        System.out.println("LogEventBoradcaster running");
        long pointer = 0;
        for (;;){
            long len = file.length();
            if(len < pointer){
                pointer = len;
            }else if (len > pointer){
                RandomAccessFile raf = new RandomAccessFile(file,"r");
                raf.seek(pointer);
                String line;
                if((line = raf.readLine()) != null){
                    ch.writeAndFlush(new LogEvent(null,file.getAbsolutePath(),line,-1));
                }
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws IOException {
        String fileName = "./logFile";
        if (!new File(fileName).exists()){
            System.out.println("File Not Found");
            System.exit(0);
        }
        int port = 8080;
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress("127.0.0.1",port),
            new File(fileName));
        try {
            broadcaster.run();
        }finally {
            broadcaster.stop();
        }

    }


}
