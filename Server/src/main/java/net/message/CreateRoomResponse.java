package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/3/19 00 16
 * description
 */

public class CreateRoomResponse extends Message implements MessageConstant {
    public CreateRoomResponse(int state, String text) {
        super(INFORM, "CreateRoomResponse", state, SERVER, 0, "Room", UNKNOWN, text);
    }
}
