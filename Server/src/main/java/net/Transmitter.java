package net;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;
import net.message.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Anselm
 * @date 2024/3/11 20 35
 * description
 */

public class Transmitter implements MessageConstant {
    private static final HashMap<Integer, ConnectThread> loggedAccountMap = new HashMap<>();
    private static final HashMap<Integer, ArrayList<Integer>> roomMap = new HashMap<>();

    public static boolean online(@NotNull User user, ConnectThread connectThread) {
        Set<Integer> IDs = loggedAccountMap.keySet();
        if (IDs.contains(user.getId())) {
            return false;
        }
        loggedAccountMap.put(user.getId(), connectThread);
        return true;
    }

    public static boolean online(@NotNull ConnectThread connectThread) {
        int id = connectThread.getUser().getId();
        Set<Integer> IDs = loggedAccountMap.keySet();
        if (IDs.contains(id)) {
            return false;
        }
        loggedAccountMap.put(id, connectThread);
        return true;
    }

    public static void offline(@NotNull User user) {
        loggedAccountMap.remove(user.getId());
    }

    public static void offline(@NotNull ConnectThread connectThread) {
        if (connectThread.getUser() != null) {
            loggedAccountMap.remove(connectThread.getUser().getId());
        }
    }

    public static void forward(@NotNull Message message) {
        if (message.getReceiver() == ALL_USER) {
            loggedAccountMap.forEach((k, v) -> v.addMessage(new Gson().toJson(message)));
        } else {
            loggedAccountMap.get(message.getReceiver()).addMessage(message);
        }
    }

}
