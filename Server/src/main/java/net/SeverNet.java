package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Anselm
 * @date 2024/2/4 12 19
 * description
 */

public class SeverNet {
    private static boolean sign = true;
    private static ExecutorService pool;
    public static void run(){
        pool = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (sign){
                Socket socket = serverSocket.accept();
                pool.submit(new Thread(new ConnectThread(socket)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }

    private static void shutdown(){
        sign = false;
        pool.shutdown();
    }
}
