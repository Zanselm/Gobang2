package net;

import com.google.gson.Gson;
import net.message.Message;

/**
 * @author Anselm
 * @date 2024/2/5 11 48
 * description
 */

public class ClientMapper {

    private static int LoginControl;


    private ClientMapper() {
    }


    public static void acceptMessage(String netMessage){
        Message message = new Gson().fromJson(netMessage,Message.class);
        int type = analyse(message);
        String messageType = message.getMessageName();
    }

    private static int analyse(Message message) {
        return 0;
    }
}
