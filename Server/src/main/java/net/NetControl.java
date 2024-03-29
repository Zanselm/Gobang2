package net;

import com.google.gson.Gson;
import exception.MessageTypeException;
import net.message.HelloResponse;
import net.message.Message;

/**
 * @author Anselm
 * @date 2024/2/6 11 17
 * description
 */

public class NetControl {
    private static final Gson gson = new Gson();
    private static final int ERROR = -1;
    private static final int HELLO = 0;
    private static final int BYE = 1;
    private static final int FORWARD = 2;
    private ConnectThread connectThread;

    private NetControl() {
    }

    public NetControl(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    public void acceptMessage(Message message) {
        int type = analyse(message);
        switch (type) {
            case HELLO -> hello(message);
            case BYE -> shutdown();
            case FORWARD -> forward(message);
            case ERROR -> throw new MessageTypeException();
        }
    }

    private void hello(Message message) {
        connectThread.addMessage(gson.toJson(new HelloResponse()));
    }

    private void shutdown() {
        connectThread.shutdown();
        Transmitter.offline(connectThread);
    }

    private void forward(Message message) {
        Transmitter.forward(message);
    }

    private int analyse(Message message) {
        String messageName = message.getMessageName();
        if ("HelloMessage".equals(messageName)) {
            return HELLO;
        }
        if ("ByeMessage".equals(messageName)) {
            return BYE;
        }
        if ("ForwardMessage".equals(messageName)) {
            return FORWARD;
        }
        return ERROR;
    }
}
