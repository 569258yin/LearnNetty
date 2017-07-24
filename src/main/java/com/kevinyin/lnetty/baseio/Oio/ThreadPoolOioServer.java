package com.kevinyin.lnetty.baseio.Oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于线程池实现的伪异步I/O实现
 * Created by kevinyin on 2017/7/24.
 */
public class ThreadPoolOioServer {

    public static void main(String[] args) {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The ThreadPoolOioServer is start in port: " + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new
                    TimeServerHandlerExecutePool(50,10000);
            socket = server.accept();
            singleExecutor.execute(new TimeServerHandler(socket));
        }catch (Exception e){

        }finally {
            if (server != null){
                try {
                    server.close();
                } catch (IOException e) {
                }
                server = null;
            }
        }
    }

}
