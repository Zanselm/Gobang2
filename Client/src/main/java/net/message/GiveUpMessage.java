package net.message;

import constant.MessageConstant;
import domain.Chessboard;
import net.LocalUser;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/3/22 11 17
 * description
 */

public class GiveUpMessage extends Message implements MessageConstant {
    public GiveUpMessage(int ID){
        super(FORWARD,"GiveUpMessage",NO, LocalUser.getUserID(),ID,"null",UNKNOWN,"null" );
    }
}
