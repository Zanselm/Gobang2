package net;

import net.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Anselm
 * @date 2024/2/4 12 19
 * description
 */

public class GameSever {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        Thread thread = new Thread(new ConnectThread(socket));
        thread.start();


    }


}
