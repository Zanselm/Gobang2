package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/2/5 11 30
 * description
 */

public class RegisterResponse extends Message implements MessageConstant {
    public RegisterResponse() {
    }
    public RegisterResponse(int state, String message) {
        super(INFORM,"RegisterResponse", state, "String", message);
    }
    public RegisterResponse(int state) {
        super(INFORM,"RegisterResponse", state, "String", "NULL");
    }
}
