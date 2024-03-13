import com.google.gson.Gson;
import entity.User;
import net.Client;
import net.message.*;
import ui.LoginFrame;
import ui.RegisterFrame;

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
        Client.run();
//        RegisterFrame.getRegisterFrame();

        LoginFrame.getLoginFrame();

    }
}
