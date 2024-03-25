package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;

/**
 * @author Anselm
 * @date 2024/3/21 19 47
 * description
 */

public class EnterRoomResponse extends Message implements MessageConstant {
    public EnterRoomResponse(User otherSideUser) {
        super(INFORM, "EnterRoomResponse", NO, SERVER, CLIENT, "User", 1, new Gson().toJson(otherSideUser));
    }
}
