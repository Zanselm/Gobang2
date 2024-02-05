package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.User;

/**
 * @author Anselm
 * @date 2024/2/5 21 59
 * description
 */

public class UpdateMessage extends Message implements MessageConstant {
    private UpdateMessage() {
    }

    public UpdateMessage(User user) {
        super(INFORM, "UpdateMessage", NO, "User", new Gson().toJson(user));
    }
}
