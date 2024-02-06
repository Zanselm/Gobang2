package net.message;

/**
 * @author Anselm
 * @date 2024/2/4 13 06
 * description
 */

public class Message{
    private int type;
    private String messageName;
    private int state;
    private String messageObject;
    private String message;

    public Message() {
    }

    public Message(int type,String messageName, int state, String messageObject, String message) {
        this.type = type;
        this.messageName = messageName;
        this.state = state;
        this.messageObject = messageObject;
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

    public String getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(String messageObject) {
        this.messageObject = messageObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", messageName='" + messageName + '\'' +
                ", state=" + state +
                ", messageObject='" + messageObject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
