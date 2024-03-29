package net;

import com.google.gson.Gson;
import entity.user.User;
import net.message.Message;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Anselm
 * @date 2024/2/4 15 24
 * description
 */

public class ConnectThread implements Runnable {
    private final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
    private Socket socket;
    private NetMapper netMapper;
    private boolean sign = true;
    private BufferedReader br;
    private PrintWriter pw;
    private User user;

    private ConnectThread() {
    }

    public ConnectThread(Socket socket) {
        this.socket = socket;
        netMapper = new NetMapper(this);
    }

    @Override
    public void run() {
        try {
            Thread accept = new Thread(accept());
            Thread send = new Thread(send());
            accept.start();
            send.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            Thread.currentThread().interrupt();
            br.close();
            pw.close();
            if (socket != null) {
                socket.close();
            }
            queue.add("exit");
            sign = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(String message) {
        queue.add(message);
    }

    public void addMessage(Message message) {
        queue.add(new Gson().toJson(message));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private @NotNull Runnable send() throws IOException {
        pw = new PrintWriter(socket.getOutputStream());
        return () -> {
            // 返回信息
            while (sign) {
                try {
                    String msg = queue.take();
                    if (msg.equals("exit")) {
                        return;
                    }
                    pw.println(msg);
                    pw.flush();
                } catch (InterruptedException e) {
                    shutdown();
                    e.printStackTrace();
                }

            }
        };
    }

    private @NotNull Runnable accept() throws IOException {
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return () -> {
            while (sign) {
                // 接受信息
                String str;
                try {
                    str = br.readLine();
                    netMapper.acceptMessage(str);
                } catch (Exception e) {
                    if (user != null) {
                        Transmitter.offline(user);
                    }
                    shutdown();
//                    e.printStackTrace();
                }
            }
        };
    }

    public NetMapper getNetMapper() {
        return netMapper;
    }
}
