package ui;

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
 * @date 2024/2/4 12 23
 * description
 */

public class RegisterFrame extends JFrame {
    public static void main(String[] args) {
        new RegisterFrame();
    }
    Font font;
    ZButton registerButton;
    ExitButton exitButton;
    ZPasswordField nameField;
    ZPasswordField passwordField;
    ZPasswordField repeatField;
    ZRadioButton man;
    ZRadioButton woman;
    public RegisterFrame() {
        init();

        add(new HeadshotPanel(100,100,100,5,HeadshotPanel.GIRL));

        addText();
        addSexGroup();
        addLoginButton();
        addExitButton();

        addBackground();
        setVisible(true);
    }

    private void addSexGroup() {
        ButtonGroup sexGroup = new ButtonGroup();
        man = new ZRadioButton(260,210,80,35,"男");
        woman = new ZRadioButton(350,210,80,35,"女");
        sexGroup.add(man);
        sexGroup.add(woman);
        add(man);
        add(woman);
    }


    private void addText() {

        JLabel title = new JLabel("注册界面");
        JLabel name = new JLabel("姓名");
        JLabel sex = new JLabel("性别");
        JLabel password = new JLabel("密码");
        JLabel repeat = new JLabel("重复密码");
        JLabel headshot = new JLabel("头像");

        title.setBounds(getWidth() / 2-100, 60, 300, 70);
        name.setBounds(200, 150, 300, 50);
        sex.setBounds(200, 200, 300, 50);
        password.setBounds(200, 250, 300, 50);
        repeat.setBounds(200, 300, 300, 50);
        headshot.setBounds(600, 150, 300, 50);

        nameField = new ZPasswordField("",260, 150, 200, 50);
        passwordField = new ZPasswordField("",260, 250, 200, 50);
        repeatField = new ZPasswordField("",320, 300, 200, 50);

        title.setFont(font.deriveFont(60f));
        name.setFont(font);
        sex.setFont(font);
        password.setFont(font);
        repeat.setFont(font);
        headshot.setFont(font);
        nameField.setFont(font);
        passwordField.setFont(font);
        repeatField.setFont(font);
        nameField.showPassword();

        add(title);
        add(name);
        add(sex);
        add(password);
        add(repeat);
        add(headshot);
        add(nameField);
        add(passwordField);
        add(repeatField);
    }

    private void init() {
        font = FontLoader.getFont();
        setLayout(null);
        setBounds(500,500,995,522);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setLocationRelativeTo(null);
        PositionDraggingListener.addPositionDraggingListener(this);
    }
    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/registerBackground.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0,0,getWidth(),getHeight());
        add(label);
    }
    private void addLoginButton() {
        registerButton = new ZButton(getWidth() / 2 - 60, 330, 200, 144, "注册");
        addRegisterButtonListener();
        add(registerButton);
    }

    private void addRegisterButtonListener() {

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
}
