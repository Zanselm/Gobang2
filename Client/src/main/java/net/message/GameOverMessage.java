package net.message;

import constant.MessageConstant;
import domain.Chessboard;
import entity.Room;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/26 15 18
 * description
 */

public class GameOverMessage extends Message implements MessageConstant {
    public GameOverMessage(Room room) {
        super(FORWARD, "GameOverMessage", NO, LocalUser.getUserID(), SERVER, "Room", 1, MyGson.toJson(room));
    }
}
