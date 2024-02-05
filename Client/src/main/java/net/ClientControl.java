package net;

import com.google.gson.Gson;
import net.message.Message;

/**
 * @author Anselm
 * @date 2024/2/5 11 48
 * description
 */

public class ClientControl {
    public static Client client;

    private ClientControl() {
    }

    public static void init(){
        client = Client.getInstance();
    }

    public static void analyse(String netMessage){
        Message message = new Gson().fromJson(netMessage,Message.class);
        String messageType = message.getMessageName();

    }
}
