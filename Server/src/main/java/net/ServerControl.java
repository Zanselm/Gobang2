package net;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;
import entity.user.UserControl;
import entity.user.UserServer;
import net.message.Message;
import net.message.RegisterResponse;

import java.util.HashMap;

/**
 * @author Anselm
 * @date 2024/2/5 11 58
 * description
 */

public class ServerControl implements MessageConstant {
    ConnectThread connectThread;
    static HashMap<String,String> controlMap = new HashMap<>();

    static {
        controlMap.put("RegisterMessage","UserControl");
        controlMap.put("DeleteMessage","UserControl");
        controlMap.put("LoginMessage","UserControl");
        controlMap.put("UpdateMessage","UserControl");
    }
    private ServerControl(){}

    public ServerControl(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    public void analyse(String netMessage){
        Gson gson = new Gson();
        Message message = gson.fromJson(netMessage, Message.class);
        UserControl userControl = new UserControl(connectThread);
        userControl.acceptMessage(message);
    }
}
