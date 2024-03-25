package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;

/**
 * @author Anselm
 * @date 2024/3/21 11 23
 * description
 */

public class EnterGameUserMessage extends Message implements MessageConstant {
    public EnterGameUserMessage(int userId, User user) {
        super(FORWARD, "EnterGameUserMessage", NO, SERVER, userId, "User", 1, new Gson().toJson(user));

    }
}
