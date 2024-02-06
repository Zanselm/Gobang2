package net.message;

import constant.MessageConstant;
import net.message.Message;

/**
 * @author Anselm
 * @date 2024/2/6 10 39
 * description
 */

public class HelloResponse extends Message implements MessageConstant {
    public HelloResponse() {
        super(INFORM, "HelloResponse", OK, "String", "Hello");
    }
}
