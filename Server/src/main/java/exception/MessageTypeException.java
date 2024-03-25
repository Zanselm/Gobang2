package exception;

/**
 * @author Anselm
 * @date 2024/2/5 21 43
 * description
 */

public class MessageTypeException extends RuntimeException {
    public MessageTypeException() {
    }

    public MessageTypeException(String message) {
        super(message);
    }
}
