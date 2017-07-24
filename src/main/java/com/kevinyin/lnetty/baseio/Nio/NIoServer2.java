package com.kevinyin.lnetty.baseio.Nio;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class NIoServer2 {
    public static void main(String[] args) {
        int port = 8080;

        MultiplexerNioServer server = new MultiplexerNioServer(port);

        new Thread(server,"NIO-MultiplexerTimeServer-001").start();
    }

}
