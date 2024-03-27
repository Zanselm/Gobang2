package utils;

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
    private static final HashMap<Integer, String> typeMap = new HashMap<>();
    private static final HashMap<Integer, String> stateMap = new HashMap<>();
    private static final HashMap<Integer, String> senderAndReceiverMap = new HashMap<>();
    private static final HashMap<Integer, String> sizeMap = new HashMap<>();

    static {
        init();
    }

    private MessagePrinter() {
    }

    public static String toString(Message message) {
        return translation(message);
    }

    public static String toString(String messageString) {
        Message message = new Gson().fromJson(messageString, Message.class);
        return translation(message);
    }

    public static void print(String message) {
        System.out.println(toString(message));
    }

    public static void print(Message message) {
        System.out.println(toString(message));
    }

    private static String translation(Message message) {
        String sender;
        String receiver;
        String messageSize;
        if (message.getSender() > 0) {
            sender = String.valueOf(message.getSender());
        }else {
            sender = senderAndReceiverMap.get(message.getSender());
        }
        if (message.getReceiver() > 0) {
            receiver = String.valueOf(message.getReceiver());
        }else {
            receiver = senderAndReceiverMap.get(message.getReceiver());
        }
        if (message.getMessageSize() > 0) {
            messageSize = String.valueOf(message.getMessageSize());
        }else {
            messageSize = sizeMap.get(message.getMessageSize());
        }

        return "Message{" +
                "type=" + typeMap.get(message.getType()) +
                ", messageName='" + message.getMessageName() + '\'' +
                ", state=" + stateMap.get(message.getState()) +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", messageObject='" + message.getMessageObjectType() + '\'' +
                ", messageSize=" + messageSize +
                ", message='" + message.getMessage() + '\'' +
                '}';
    }

    private static void init() {
        typeMap.put(GET, "GET");
        typeMap.put(INFORM, "INFORM");
        typeMap.put(FORWARD, "FORWARD");

        stateMap.put(NO, "NO");
        stateMap.put(OK, "OK");
        stateMap.put(CLIENT_ERROR, "CLIENT_ERROR");
        stateMap.put(SEVER_ERROR, "SEVER_ERROR");

        senderAndReceiverMap.put(NOT_LOGGED_IN_CLIENT,"NOT_LOGGED_IN_CLIENT");
        senderAndReceiverMap.put(SERVER,"SERVER");
        senderAndReceiverMap.put(ALL_USER,"ALL_USER");

        sizeMap.put(UNKNOWN,"UNKNOWN");
    }
}
