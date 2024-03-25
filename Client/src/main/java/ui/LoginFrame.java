package ui;

import com.google.gson.Gson;
import entity.User;
import net.Client;
import net.LocalUser;
import net.message.ByeMessage;
import net.message.LoginMessage;
import net.message.Message;
import org.jetbrains.annotations.NotNull;
import ui.zui.*;
import utils.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/2/4 12 22
 * description
 */

public class LoginFrame extends JFrame {
    private static LoginFrame loginFrame;
    private ZPasswordField accountField;
    private ZPasswordField passwordField;
    private ZMainButton loginButton;
    private ExitButton exitButton;
    private CheckLabel accountCheck;

    private LoginFrame() throws HeadlessException {
        init();
        addTextField();
        addLoginButton();
        addExitButton();
        addRegister();


//        ZButton button = new ZButton(100, 100, 50, ZButton.LOGIN);
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                Client.addMessage(new ForwardMessage(accountField.getContent()));
//            }
//        });
//        add(button);

        addBackground();
        setVisible(true);
    }

    public static void main(String[] args) {
        LoginFrame.getLoginFrame();
    }

    public static LoginFrame getLoginFrame() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame();
        }
        return loginFrame;
    }

    public static void successRegister() {
        RegisterFrame registerFrame = RegisterFrame.getRegisterFrame();
        registerFrame.setVisible(false);
        loginFrame.setLocation(registerFrame.getLocation());
        loginFrame.setVisible(true);
        loginFrame.accountField.setText(registerFrame.getNameField().getContent());
        loginFrame.passwordField.setText(registerFrame.getPasswordField().getContent());
    }

    private void addRegister() {
        ZButton register = new ZButton(200, 100, 30, ZButton.REGISTER);
        add(register);
        JLabel jLabel = new JLabel();
        jLabel.setBounds(200, 130, 40, 20);
        add(jLabel);
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                toRegisterFrame();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setText("注册");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setText("");
            }
        });
    }

    private void toRegisterFrame() {
        RegisterFrame.getRegisterFrame().setBounds(getBounds());
        RegisterFrame.getRegisterFrame().setVisible(true);
        setVisible(false);
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/loginBackground.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
    }


    private void addLoginButton() {
        loginButton = new ZMainButton(getWidth() / 2 - 60, 360, 170, 100, "登录");
        addLoginListener();
        add(loginButton);
    }

    private void addLoginListener() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!examine()) {
                    accountCheck.setText("请检查您的输入");
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    return;
                }
                if (Client.isSocketExist()) {
                    accountCheck.clean();
                    User loginUser = new User(0, new String(accountField.getPassword()), null, new String(passwordField.getPassword()), 0, 0, 0);
                    Client.addMessage(new LoginMessage(loginUser));
                } else {
                    accountCheck.clean();
                    System.out.println("未连接服务器");
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

            private boolean examine() {
                boolean sign = switch (accountField.getContent()) {
                    case "", "请输入您的账号" -> false;
                    default -> true;
                };
                if (accountField.getContent().isEmpty()) {
                    sign = false;
                }
                return sign;
            }
        });
    }

    public void LoginFailed(String text) {
        accountCheck.setText(text);
    }

    public void LoginSuccessful(@NotNull Message message) {
        Gson gson = new Gson();
        LocalUser.online(gson.fromJson(message.getMessage(), User.class));
        GameLobbyFrame.getGameLobbyFrame().setVisible(true);
        setVisible(false);
        RegisterFrame.getRegisterFrame().dispose();
        loginFrame = null;
        dispose();
    }

    private void addExitButton() {
        exitButton = new ExitButton(830, 60, 50, 50);
        addExitListener();
        add(exitButton);
    }

    private void addExitListener() {
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Client.isSocketExist()) {
                    Client.addMessage(new ByeMessage());
                }
                System.exit(0);
            }
        });
    }

    private void addTextField() {
        accountField = new ZPasswordField("请输入您的账号", 350, 200, 300, 50);
        accountField.showPassword();
        passwordField = new ZPasswordField("", 350, 290, 300, 50);
        accountCheck = new CheckLabel(650, 220, 200, 20);
        add(accountField);
        add(passwordField);
        add(accountCheck);

        EyeButton eyeButton = new EyeButton(passwordField, 670, 310, 40);
        add(eyeButton);
    }

    private void init() {
        loadFont();
        setLayout(null);
        setBounds(500, 500, 995, 522);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setLocationRelativeTo(null);
        PositionDraggingListener.addPositionDraggingListener(this);
    }

    private void loadFont() {
        Font font = FontLoader.getFont();
        this.setFont(font);
    }
}
