package net;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Anselm
 * @date 2024/2/4 16 27
 * description
 */

public class Client {
    private static  Client client = new Client();
    private static Socket socket = null;
    private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    private Client() {
    }
    public static Client getInstance(){
        return client;
    }

    public static void run(){
        try {
//            连接服务器
            try {
                socket = new Socket("127.0.0.1", 8888);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            启动接受和发送线程
            Thread send = new Thread(send());
            Thread accept = new Thread(accept());
            send.start();
            accept.start();
//            初始化控制器
            ClientControl.init();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void addMessage(String message){
        queue.add(message);
    }
    private static Runnable accept() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return () -> {
            // 接受发送的信息
            while (true) {
                try {
                    System.out.println(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
    }


    private static Runnable send() throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        return () -> {
            while (true) {
                // 发送信息
                try {
                    String msg;
                    if (((msg = queue.take()) != null)){
                        pw.println(msg);
                        pw.flush();
                    }

                } catch (Exception e) {
                    break;
                }

            }

        };
    }
}

