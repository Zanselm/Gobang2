package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/18 23 53
 * description
 */

public class CreateRoomMessage extends Message implements MessageConstant {
    public CreateRoomMessage(String text){
        super(GET,"CreateRoomMessage",NO, LocalUser.getUserID(),SERVER,"Room",UNKNOWN, text);
    }
}
