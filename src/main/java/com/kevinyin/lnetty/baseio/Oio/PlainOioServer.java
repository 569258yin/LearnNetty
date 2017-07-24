package com.kevinyin.lnetty.baseio.Oio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *  阻塞型 IO java.io Socket
 * Created by kevinyin on 2017/7/9.
 */
public class PlainOioServer {

    public void server(int port) throws IOException{
        final ServerSocket socket = new ServerSocket(port);
        try {
            for(;;){
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connetion from "+clientSocket);

                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out = null;
                        try{
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes("UTF-8"));
                            out.flush();
                            clientSocket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }finally {
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
