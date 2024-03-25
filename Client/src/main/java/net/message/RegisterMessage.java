package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.User;


/**
 * @author Anselm
 * @date 2024/2/5 11 17
 * description
 */

public class RegisterMessage extends Message implements MessageConstant {
    public RegisterMessage() {
    }

    public RegisterMessage(User user) {
        super(GET, "RegisterMessage", NO, "User", new Gson().toJson(user));
    }
}
