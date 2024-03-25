package net.message;

import constant.MessageConstant;
import domain.Chessboard;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/22 10 19
 * description
 */

public class AddPieceMessage extends Message implements MessageConstant {
    public AddPieceMessage(Chessboard.Piece piece, int ID) {
        super(FORWARD, "AddPieceMessage", NO, LocalUser.getUserID(), ID, "Piece", 1, MyGson.toJson(piece));
    }
}
