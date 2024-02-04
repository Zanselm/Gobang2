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

    static Socket socket = null;
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public static void run(){
        try {
            try {
                socket = new Socket("127.0.0.1", 8888);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Runnable r = send(socket);
            Runnable r2 = accept(socket);
            Thread t1 = new Thread(r);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    private static Runnable accept(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return () -> {
            // 接受发送的信息
            while (true) {
                String str;
                try {
                    str = br.readLine();
                    System.out.println("接受者receiver:" + str);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
    }


    private static Runnable send(Socket socket) throws IOException {
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

