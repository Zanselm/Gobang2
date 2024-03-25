package net.message;

import constant.MessageConstant;
import entity.Room;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/20 16 38
 * description
 */

public class EnterRoomMessage extends Message implements MessageConstant {
    public EnterRoomMessage(Room room) {
        super(GET, "EnterRoomMessage", NO, LocalUser.getUserID(), SERVER, "Room", 1, MyGson.toJson(room));
    }

    public EnterRoomMessage(String room) {
        super(GET, "EnterRoomMessage", NO, LocalUser.getUserID(), SERVER, "Room", 1, room);
    }
}
