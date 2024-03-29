package net;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.room.RoomControl;
import entity.user.UserControl;
import exception.MessageTypeException;
import net.message.Message;

import java.util.HashMap;

/**
 * @author Anselm
 * @date 2024/2/5 11 58
 * description
 */

public class NetMapper implements MessageConstant {
    static final int UN_KNOW = -1;
    static final int USER_CONTROL = 1;
    static final int ROOM_CONTROL = 2;
    static final int SERVER_CONTROL = 3;
    static HashMap<String, Integer> controlMap = new HashMap<>();

    static {
        initMap();
    }

    public UserControl userControl;
    public RoomControl roomControl;
    public NetControl netControl;
    ConnectThread connectThread;

    private NetMapper() {
    }

    public NetMapper(ConnectThread connectThread) {
        this.connectThread = connectThread;
        this.userControl = new UserControl(connectThread);
        this.roomControl = new RoomControl(connectThread);
        this.netControl = new NetControl(connectThread);
    }

    private static void initMap() {
        controlMap.put("RegisterMessage", USER_CONTROL);
        controlMap.put("DeleteMessage", USER_CONTROL);
        controlMap.put("LoginMessage", USER_CONTROL);
        controlMap.put("UpdateMessage", USER_CONTROL);

        controlMap.put("CreateRoomMessage", ROOM_CONTROL);
        controlMap.put("GetRoomsMessage", ROOM_CONTROL);
        controlMap.put("EnterRoomMessage", ROOM_CONTROL);
        controlMap.put("GameOverMessage", ROOM_CONTROL);

        controlMap.put("HelloMessage", SERVER_CONTROL);
        controlMap.put("ByeMessage", SERVER_CONTROL);
        controlMap.put("ForwardMessage", SERVER_CONTROL);
    }

    public void acceptMessage(String netMessage) {
        Gson gson = new Gson();
        Message message = gson.fromJson(netMessage, Message.class);
        int type = analyse(message);
        try {
            switch (type) {
                case USER_CONTROL -> userControl.acceptMessage(message);
                case SERVER_CONTROL -> netControl.acceptMessage(message);
                case ROOM_CONTROL -> roomControl.acceptMessage(message);
                case UN_KNOW -> forward(message);
            }
        } catch (MessageTypeException e) {
            throw new RuntimeException(e);
        }

    }

    private void forward(Message message) {
        if (message.getType() == MessageConstant.FORWARD) {
            Transmitter.forward(message);
        } else {
            throw new MessageTypeException();
        }
    }

    private int analyse(Message message) {
        try {
            System.out.println(message.getMessageName());
            return controlMap.get(message.getMessageName());
        } catch (Exception e) {
            return UN_KNOW;
        }
    }
}
