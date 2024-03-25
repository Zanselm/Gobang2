package net.message;

import constant.MessageConstant;
import net.LocalUser;

/**
 * @author Anselm
 * @date 2024/3/21 20 29
 * description
 */

public class CompareNumMessage extends Message implements MessageConstant {
    public CompareNumMessage(int number, int ID) {
        super(FORWARD, "CompareNumMessage", NO, LocalUser.getUserID(), ID, "int", 1, String.valueOf(number));
    }
}
