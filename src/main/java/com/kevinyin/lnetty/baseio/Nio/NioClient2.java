package com.kevinyin.lnetty.baseio.Nio;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class NioClient2 {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        for (int i = 0; i < 1000; i++) {
            new Thread(new NioClientHandler(host,port)).start();
        }
    }
}
