package net;

import com.google.gson.Gson;
import entity.user.User;
import entity.user.UserDAO;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Anselm
 * @date 2024/2/4 15 24
 * description
 */

public class ConnectThread implements Runnable{
    Socket socket;
    ServerControl serverControl;
    public ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    private ConnectThread() {
    }

    public ConnectThread(Socket socket) {
        this.socket = socket;
        serverControl = new ServerControl(this);
    }

    @Override
    public void run(){
        try {
            Thread accept = new Thread(accept());
            Thread send = new Thread(send());
            accept.start();
            send.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addMessage(String message){
        queue.add(message);
    }

    private Runnable send() throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        return () -> {
            // 返回信息
            while (true) {
                try {
                    String msg;
                    if ((msg = queue.take()) != null) {
                        pw.println(msg);
                        pw.flush();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

    private Runnable accept() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return () -> {
            while (true) {
                // 接受信息
                String str;
                try {
                    str = br.readLine();
                    serverControl.analyse(str);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}

