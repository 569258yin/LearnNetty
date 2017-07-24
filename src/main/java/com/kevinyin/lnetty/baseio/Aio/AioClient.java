package com.kevinyin.lnetty.baseio.Aio;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class AioClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;

        AsyncClientHandler clientHandler = new AsyncClientHandler(host,port);

        new Thread(clientHandler,"AIO-Client 0001").start();
    }
}
