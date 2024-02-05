package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;

/**
 * @author Anselm
 * @date 2024/2/5 20 44
 * description
 */

public class LoginMessage extends Message implements MessageConstant {
    public LoginMessage() {
    }

    public LoginMessage(User user) {
        super(GET, "LoginMessage", NO, "User", new Gson().toJson(user));
    }
}
