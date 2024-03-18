package net.message;

import constant.MessageConstant;
import net.LocalUser;

/**
 * @author Anselm
 * @date 2024/3/19 01 22
 * description
 */

public class GetRoomsMessage extends Message implements MessageConstant {
    public GetRoomsMessage(){
        super(GET,"GetRoomsMessage",NO, LocalUser.getUserID(),SERVER,null,UNKNOWN, null);
    }
}
