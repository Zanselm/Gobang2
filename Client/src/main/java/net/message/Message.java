package net.message;

import constant.MessageConstant;
import utils.MessagePrinter;

/**
 * @author Anselm
 * @date 2024/2/4 13 06
 * description
 */

public class Message implements MessageConstant {
    private int type;
    private String messageName;
    private int state;
    private int sender;
    private int receiver;
    private String messageObjectType;
    private int messageSize;
    private String message;

    public Message() {
    }

    public Message(int type, String messageName, int state, String messageObjectType, String message) {
        this.type = type;
        this.messageName = messageName;
        this.state = state;
        this.messageObjectType = messageObjectType;
        this.message = message;

        this.messageSize = UNKNOWN;
    }


    public Message(int type, String messageName, int state, int sender, int receiver, String messageObjectType, int messageSize, String message) {
        this.type = type;
        this.messageName = messageName;
        this.state = state;
        this.sender = sender;
        this.receiver = receiver;
        this.messageObjectType = messageObjectType;
        this.messageSize = messageSize;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getMessageObjectType() {
        return messageObjectType;
    }

    public void setMessageObjectType(String messageObjectType) {
        this.messageObjectType = messageObjectType;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return MessagePrinter.toString(this);
    }
}
