
import entity.Room;
import entity.User;
import net.Client;
import net.message.*;
import ui.GameLobbyFrame;
import ui.LoginFrame;
import ui.RegisterFrame;
import utils.MyGson;

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
        System.setProperty("sun.java2d.noddraw", "true");
        Client.run();
        LoginFrame.getLoginFrame();
//        System.out.println(MyGson.toJson(new Room(0, "132", "13", 0, 0, 0, 0, true)));
//        Client.addMessage(new CreateRoomMessage(MyGson.toJson(new Room(0,"121","13",0,0,0,0,true))));


    }
}
