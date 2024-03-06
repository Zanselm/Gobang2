package net;

import com.google.gson.Gson;
import net.message.Message;
import utils.MessagePrinter;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Anselm
 * @date 2024/2/4 16 27
 * description
 */

public class Client {
    private static Socket socket = null;
    private static boolean sign = true;
    private static BufferedReader br;
    private static PrintWriter pw;
    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
    private static final Gson gson = new Gson();

    private Client() {
    }

    public static void run() {
        try {
//            连接服务器
            try {
                socket = new Socket("127.0.0.1", 8888);
            } catch (IOException e) {
                System.out.println("连接失败，服务器可能未启动");
                return;
            }
//            启动接受和发送线程
            Thread send = new Thread(send());
            Thread accept = new Thread(accept());
            send.start();
            accept.start();

        } catch (IOException e) {
            shutdown();
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        if (socket != null) {
            try {
                if (br != null) {
                    br.close();
                }
                if (pw != null) {
                    pw.close();
                }
                socket.close();
                sign = false;
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addMessage(String message) {
        queue.add(message);
    }
    public static void addMessage(Message message) {
        String m = gson.toJson(message);
        queue.add(m);
    }

    private static Runnable accept() throws IOException {
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return () -> {
            // 接受发送的信息
            while (sign) {
                try {
                    String msg = br.readLine();
                    if (msg == null) {
                        shutdown();
                        return;
                    }
//                    应放入Mapper
                    MessagePrinter.print(msg);
                } catch (IOException e) {
                    shutdown();
                    e.printStackTrace();
                }
            }
        };
    }

    private static Runnable send() throws IOException {
        pw = new PrintWriter(socket.getOutputStream());
        return () -> {
            while (sign) {
                // 发送信息
                try {
                    String msg;
                    if (((msg = queue.take()) != null)) {
                        pw.println(msg);
                        pw.flush();
                    } else {
                        shutdown();
                        return;
                    }
                } catch (Exception e) {
                    shutdown();
                    e.printStackTrace();
                }
            }
        };
    }
    public static boolean isSocketExist(){
        return socket != null;
    }

}

