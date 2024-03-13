package net;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.room.Room;
import entity.user.User;
import net.message.LoginResponse;
import net.message.Message;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author Anselm
 * @date 2024/3/11 20 35
 * description
 */

public class Transmitter implements MessageConstant {
    private static final HashMap<Integer,ConnectThread> loggedAccountMap = new HashMap<>();
    private static final HashMap<Integer,ConnectThread[]>  roomMap =  new HashMap<>();
    public static boolean online(@NotNull User user, ConnectThread connectThread){
        Set<Integer> IDs = loggedAccountMap.keySet();
        if (IDs.contains(user.getId())){return false;}
        loggedAccountMap.put(user.getId(),connectThread);
        return true;
    }
    public static boolean online(@NotNull ConnectThread connectThread){
        int id = connectThread.getUser().getId();
        Set<Integer> IDs = loggedAccountMap.keySet();
        if (IDs.contains(id)){return false;}
        loggedAccountMap.put(id,connectThread);
        return true;
    }
    public static void offline(@NotNull User user){
        loggedAccountMap.remove(user.getId());
    }
    public static void offline(@NotNull ConnectThread connectThread){
        if (connectThread.getUser()!=null){
            loggedAccountMap.remove(connectThread.getUser().getId());
        }
    }
    public static boolean enterRoom(@NotNull Room room, ConnectThread  connectThread){
        Set<Integer> roomIDs = roomMap.keySet();
        int ID = room.getID();
        if (roomIDs.contains(ID)) {
            ConnectThread[] connectThreads = roomMap.get(ID);
            if (connectThreads[0] == null){
                connectThreads[0] = connectThread;
                return true;
            }
            if (connectThreads[1] == null){
                connectThreads[1] = connectThread;
                return true;
            }
            return false;
        }else {
            ConnectThread[] connectThreads = new ConnectThread[2];
            connectThreads[0] = connectThread;
            roomMap.put(ID,connectThreads);
            return true;
        }
    }
    public static void leaveRoom(){
    }
    public static void forward(Message message){
        loggedAccountMap.forEach((k,v)-> v.addMessage(new Gson().toJson(new LoginResponse(CLIENT_ERROR,message.getMessage()))));
    }

}
