package util;

import com.google.gson.Gson;
import constant.MessageConstant;
import net.message.Message;

import java.util.HashMap;


/**
 * @author Anselm
 * @date 2024/2/6 22 22
 * description
 */

public class MessagePrinter implements MessageConstant {
    private static final HashMap<Integer,String> typeMap = new HashMap<>();
    private static final HashMap<Integer,String> stateMap = new HashMap<>();
    static {init();}

    private MessagePrinter() {
    }

    public static String toString(Message message) {
        return translation(message);
    }
    public static String toString(String messageString){
        Message message = new Gson().fromJson(messageString, Message.class);
        return  translation(message);
    }
    public static void print(String message){
        System.out.println(toString(message));
    }
    public static void print(Message message){
        System.out.println(toString(message));
    }
    private static String  translation(Message message){
        return "Message{" +
                "type=" + typeMap.get(message.getType()) +
                ", messageName='" + message.getMessageName() + '\'' +
                ", state=" + stateMap.get(message.getState()) +
                ", messageObject='" + message.getMessageObjectType() + '\'' +
                ", message='" + message.getMessage() + '\'' +
                '}';

    }

    private static void init() {
        typeMap.put(GET,"GET");
        typeMap.put(INFORM,"INFORM");
        typeMap.put(FORWARD,"FORWARD");
        stateMap.put(NO,"NO");
        stateMap.put(OK,"OK");
        stateMap.put(CLIENT_ERROR,"CLIENT_ERROR");
        stateMap.put(SEVER_ERROR,"SEVER_ERROR");
    }
}
