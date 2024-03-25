package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/2/6 10 38
 * description
 */

public class HelloMessage extends Message implements MessageConstant {
    public HelloMessage() {
        super(GET, "HelloMessage", NO, "String", "Hello");
    }
}
