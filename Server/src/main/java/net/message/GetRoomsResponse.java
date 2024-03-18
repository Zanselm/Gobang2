package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/3/19 01 28
 * description
 */

public class GetRoomsResponse extends Message implements MessageConstant {
    public GetRoomsResponse(int length,String text){
        super(INFORM,"GetRoomsResponse",OK, SERVER,0,"Room[]",length,text);
    }
}
