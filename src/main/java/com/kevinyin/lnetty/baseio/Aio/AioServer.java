package com.kevinyin.lnetty.baseio.Aio;

/**异步IO
 * Created by kevinyin on 2017/7/24.
 */
public class AioServer {

    public static void main(String[] args) {
        int port = 8080;
        AsyncServerHandler server = new AsyncServerHandler(port);

        new Thread(server,"AIO-AsynServerHandler-001").start();

    }
}
