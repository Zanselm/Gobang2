package net.message;

import constant.MessageConstant;

/**
 * @author Anselm
 * @date 2024/3/12 01 08
 * description
 */

public class ForwardMessage extends Message implements MessageConstant {
    public ForwardMessage(String text){
        super(FORWARD,"ForwardMessage",OK,"String",text);
    }
}
