package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.room.Room;

/**
 * @author Anselm
 * @date 2024/3/19 19 27
 * description
 */

public class AlterRoomMessage extends Message implements MessageConstant {
    public AlterRoomMessage(String text){
        super(FORWARD,"AlterRoomMessage",NO, SERVER,ALL_USER,"Room",1,text);
    }
    public AlterRoomMessage(Room room){
        super(FORWARD,"AlterRoomMessage",NO, SERVER,ALL_USER,"Room",1,new Gson().toJson(room));
    }
}
