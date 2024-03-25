package net.message;

import constant.MessageConstant;
import domain.Chessboard;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/22 10 50
 * description
 */

public class VictoryMessage extends Message implements MessageConstant {
    public VictoryMessage(int ID){
        super(FORWARD,"VictoryMessage",NO, LocalUser.getUserID(),ID,"null",UNKNOWN, "null");
    }
}
