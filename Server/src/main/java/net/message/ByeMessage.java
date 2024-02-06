package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/2/6 10 36
 * description
 */

public class ByeMessage extends Message implements MessageConstant {

    public ByeMessage() {
        super(INFORM, "ByeMessage", NO, "String", "Bye");
    }
}
