import com.google.gson.Gson;
import entity.User;
import net.Client;
import net.message.*;
import ui.LoginFrame;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Anselm
 * @date 2024/2/4 13 03
 * description
 */

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
//        System.setProperty("sun.java2d.noddraw", "true");
//        System.setProperty("java.awt.im.style","on-the-spot");
        Client.run();
        LoginFrame.showLoginFrame();
//        Gson gson = new Gson();
//        User user2 = new User(15,"xyf","","12344564",2,4,7);
//        Client.addMessage(gson.toJson(new RegisterMessage(user2)));
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
//        Client.addMessage(gson.toJson(new ByeMessage()));
    }
}
