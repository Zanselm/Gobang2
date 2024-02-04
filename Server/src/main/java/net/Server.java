package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Anselm
 * @date 2024/2/4 15 24
 * description
 */

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(8888);
            Socket socket = server.accept();
            System.out.println("----程序已经连接++++");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(os);
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        // 接受信息
                        String str;
                        try {
                            str = br.readLine();
                            System.out.println("sender的信息" + str);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            };
            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    // 返回信息
                    while (true) {
                        Scanner scan = new Scanner(System.in);
                        String msg = scan.nextLine();
                        pw.println(msg);
                        pw.flush();
                    }
                }
            };

            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

