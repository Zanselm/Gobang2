package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/3/19 00 11
 * description
 */

public class CreateRoomResponse extends Message implements MessageConstant {
    public CreateRoomResponse(int state,String text){
        super(INFORM,"CreateRoomResponse",state, SERVER,0,"String",UNKNOWN,text);
    }
}
