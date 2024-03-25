package net.message;

import constant.MessageConstant;
import net.LocalUser;

/**
 * @author Anselm
 * @date 2024/2/6 10 36
 * description
 */

public class ByeMessage extends Message implements MessageConstant {

    public ByeMessage() {
        super(INFORM, "ByeMessage", NO, LocalUser.getUserID(), SERVER
                , "String", UNKNOWN, "Bye");
    }
}
