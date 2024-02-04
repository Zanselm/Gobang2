package net;

import net.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Anselm
 * @date 2024/2/4 12 16
 * description
 */

public class GameClient {
    static final GameClient gameclient = new GameClient();
    String IP = "127.0.0.1";
    int port = 5500;
    Socket socket;

    private GameClient()  {
        try {
            socket = new Socket(IP,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public GameClient getGameclient(){
        return gameclient;
    }

    public void sendMessage(Message message) throws IOException {
        ObjectOutputStream oos;
        oos =new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        oos.close();
    }

}
