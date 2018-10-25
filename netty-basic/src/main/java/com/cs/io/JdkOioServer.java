package com.cs.io;

import com.cs.utils.Constant;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author benjaminChan
 * @date 2018/10/22 0022 下午 3:12
 */
public class JdkOioServer {

    @Test
    public void server() throws IOException {
        final ServerSocket serverSocket = new ServerSocket(Constant.PORT);
        try {
            final Socket socket = serverSocket.accept();
            for (;;) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write("Hi!\r\n".getBytes("UTF-8"));
                            outputStream.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }){}.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
