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
        ServerSocket serverSocket = new ServerSocket(5500);
        Socket socket = serverSocket.accept();
        ObjectInputStream ojs  = new ObjectInputStream(socket.getInputStream());
        Message message = (Message)ojs.readObject();
        System.out.println(message.message);
        ojs.close();

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(new Message());


        socket.close();
    }


}
