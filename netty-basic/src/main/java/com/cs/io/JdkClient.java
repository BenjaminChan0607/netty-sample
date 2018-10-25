package com.cs.io;

import com.cs.utils.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author benjaminChan
 * @date 2018/10/25 0025 上午 10:53
 */
public class JdkClient {

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(Constant.HOST, Constant.PORT);
            //Client和Server的in/out获取方式都相同
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
//            out.println("Query time order");
//            System.out.println("Send order to server succeed.");
            String resp = in.readLine();//receive msg from server
            System.out.println("Now is :" + resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
