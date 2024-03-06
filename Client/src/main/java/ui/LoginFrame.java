package ui;

import entity.User;
import net.Client;
import net.message.LoginMessage;
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
    private static final LoginFrame loginFrame =  new LoginFrame();
    private ZPasswordField accountField;
    private ZPasswordField passwordField;
    private ZButton loginButton;
    private ExitButton exitButton;
    public static void main(String[] args) {
        LoginFrame.showLoginFrame();
    }
    public static void showLoginFrame(){
    }
    public static JFrame getLoginFrame(){
        return loginFrame;
    }
    private LoginFrame() throws HeadlessException {
        init();
        addTextField();
        addLoginButton();
        addExitButton();
        addBackground();
        setVisible(true);
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/loginBackground.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0,0,getWidth(),getHeight());
        add(label);
    }


    private void addLoginButton() {
        loginButton = new ZButton(getWidth() / 2 - 60, 330, 200, 144, "登录");
        addLoginListener();
        add(loginButton);
    }

    private void addLoginListener() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!examine()){
                    System.out.println("请检查您的输入");
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    return;
                }
                if (Client.isSocketExist()){
                    User loginUser = new User(0, new String(accountField.getPassword()),null,new String(passwordField.getPassword()),0,0,0);
                    Client.addMessage(new LoginMessage(loginUser));
                }else {
                    System.out.println("未连接服务器");
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

            private boolean examine() {
                boolean sign = switch (new String(accountField.getPassword())) {
                    case "", "请输入您的账号" -> false;
                    default -> true;
                };
                if (new String(passwordField.getPassword()).isEmpty()){
                    sign = false;
                }
                return sign;
            }
        });
    }

    private void addExitButton() {
        exitButton = new ExitButton(830,60,50,50);
        addExitListener();
        add(exitButton);
    }

    private void addExitListener() {
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    private void addTextField() {
        accountField = new ZPasswordField("请输入您的账号", 350, 200, 300, 50);
        accountField.showPassword();
        passwordField = new ZPasswordField("", 350, 290, 300, 50);
        add(accountField);
        add(passwordField);

        EyeButton eyeButton = new EyeButton(passwordField,670,310,40);
        add(eyeButton);
    }

    private void init() {
        loadFont();
        setLayout(null);
        setBounds(500,500,995,522);
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
