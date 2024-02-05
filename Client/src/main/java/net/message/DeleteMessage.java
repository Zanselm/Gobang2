package net.message;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.User;


/**
 * @author Anselm
 * @date 2024/2/5 21 51
 * description
 */

public class DeleteMessage extends Message implements MessageConstant {
    private DeleteMessage() {
    }

    public DeleteMessage(User user) {
        super(INFORM, "DeleteMessage", NO, "User", new Gson().toJson(user));
    }
}
