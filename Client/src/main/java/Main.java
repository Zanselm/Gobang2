import net.GameClient;
import net.message.Message;

import java.io.IOException;

/**
 * @author Anselm
 * @date 2024/2/4 13 03
 * description
 */

public class Main {
    public static void main(String[] args) throws IOException {
        GameClient.getGameclient().sendMessage(new Message());
    }
}
