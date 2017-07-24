package com.kevinyin.lnetty.baseio.Oio;

import java.net.Socket;

/**
 * Created by kevinyin on 2017/7/24.
 */
public class TimeServerHandler implements Runnable{
    private final Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        //处理socket
    }
}
