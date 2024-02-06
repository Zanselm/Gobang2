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
    ConnectThread connectThread;
    UserControl userControl;
    RoomControl roomControl;
    NetControl netControl;
    static HashMap<String,Integer> controlMap = new HashMap<>();
    static final int ERROR = -1;
    static final int USER_CONTROL = 1;
    static final int ROOM_CONTROL = 2;
    static final int SERVER_CONTROL = 3;

    static {
        initMap();
    }

    private NetMapper(){}

    public NetMapper(ConnectThread connectThread) {
        this.connectThread = connectThread;
        this.userControl = new UserControl(connectThread);
        this.roomControl = new RoomControl();
        this.netControl = new NetControl(connectThread);
    }

    public void acceptMessage(String netMessage){
        Gson gson = new Gson();
        Message message = gson.fromJson(netMessage, Message.class);
        int type =analyse(message);
        switch (type){
            case USER_CONTROL -> userControl.acceptMessage(message);
            case SERVER_CONTROL -> netControl.acceptMessage(message);
            case ERROR -> throw new MessageTypeException();
        }
    }
    private int analyse(Message message){
        try {
            return controlMap.get(message.getMessageName());
        } catch (Exception e) {
            return ERROR;
        }
    }



    private static void initMap() {
        controlMap.put("RegisterMessage",USER_CONTROL);
        controlMap.put("DeleteMessage",USER_CONTROL);
        controlMap.put("LoginMessage",USER_CONTROL);
        controlMap.put("UpdateMessage",USER_CONTROL);
        controlMap.put("HelloMessage",SERVER_CONTROL);
        controlMap.put("ByeMessage",SERVER_CONTROL);
    }
}
