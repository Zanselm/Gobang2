package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;

/**
 * @author Anselm
 * @date 2024/2/5 20 47
 * description
 */

public class LoginResponse extends Message implements MessageConstant {
    public LoginResponse() {
    }

    public LoginResponse(int state, User user) {
        super(INFORM, "LoginResponse", state, "String", new Gson().toJson(user));
    }
}
