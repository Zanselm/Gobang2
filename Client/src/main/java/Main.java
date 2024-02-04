import net.Client;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Anselm
 * @date 2024/2/4 13 03
 * description
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Client.run();
        Client.queue.add("ss");
        while (true){
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            Client.queue.add(s);
        }
    }
}
