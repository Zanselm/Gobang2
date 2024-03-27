package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.room.Room;

/**
 * @author Anselm
 * @date 2024/3/26 19 48
 * description
 */

public class DeleteLobbyRoomMessage extends Message implements MessageConstant {
    public DeleteLobbyRoomMessage(Room room) {
        super(FORWARD, "DeleteLobbyRoomMessage", NO, SERVER, ALL_USER, "Room", 1, new Gson().toJson(room));
    }
}
