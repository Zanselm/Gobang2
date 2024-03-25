package entity.user;

import com.google.gson.Gson;
import constant.MessageConstant;
import exception.MessageTypeException;
import net.ConnectThread;
import net.Transmitter;
import net.message.LoginResponse;
import net.message.Message;
import net.message.RegisterResponse;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * @author Anselm
 * @date 2024/2/5 19 49
 * description
 */

public class UserControl implements MessageConstant {
    private static final Gson gson = new Gson();
    private static final int REGISTER = 0;
    private static final int DELETE = 1;
    private static final int LOGIN = 2;
    private static final int UPDATE = 3;
    private ConnectThread connectThread;
    private UserServer userServer;

    private UserControl() {
        this.userServer = new UserServer();
    }

    public UserControl(ConnectThread connectThread) {
        this.connectThread = connectThread;
        this.userServer = new UserServer();
    }

    public static void main(String[] args) {
        User user = new UserControl().getUser(30);
        System.out.println(user.getName());
    }

    public void acceptMessage(@NotNull Message message) {
        User user = gson.fromJson(message.getMessage(), User.class);
        int type = analyse(message);
        switch (type) {
            case REGISTER -> register(user);
            case DELETE -> delete(user);
            case LOGIN -> login(user);
            case UPDATE -> update(user);
            case -1 -> throw new MessageTypeException();
        }
    }

    private int analyse(@NotNull Message message) {
        String messageName = message.getMessageName();
        if ("RegisterMessage".equals(messageName)) {
            return REGISTER;
        }
        if ("DeleteMessage".equals(messageName)) {
            return DELETE;
        }
        if ("LoginMessage".equals(messageName)) {
            return LOGIN;
        }
        if ("UpdateMessage".equals(messageName)) {
            return UPDATE;
        }
        return -1;
    }

    private void register(User user) {
        try {
            boolean sign = userServer.register(user);
            if (sign) {
                connectThread.addMessage(gson.toJson(new RegisterResponse(OK, "成功")));
            } else {
                connectThread.addMessage(gson.toJson(new RegisterResponse(CLIENT_ERROR, "用户名重复")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(User user) {
        try {
            userServer.delete(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(User user) {
        try {
            User severUser = userServer.login(user);
            if (severUser != null) {
                if (Transmitter.online(severUser, connectThread)) {
                    connectThread.addMessage(gson.toJson(new LoginResponse(OK, severUser)));
                    connectThread.setUser(severUser);
                } else {
                    connectThread.addMessage(gson.toJson(new LoginResponse(CLIENT_ERROR, "重复登录")));
                }
            } else {
                connectThread.addMessage(gson.toJson(new LoginResponse(CLIENT_ERROR, "密码或用户名错误")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(User user) {
        try {
            userServer.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(int userID) {
        try {
            return userServer.getUser(new User(userID, "", "", "", 0, 0, 0));
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
}
