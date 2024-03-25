package ui;

import entity.User;
import net.Client;
import net.message.ByeMessage;
import net.message.RegisterMessage;
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
 * @date 2024/2/4 12 23
 * description
 */

public class RegisterFrame extends JFrame {
    private static RegisterFrame registerFrame;
    HeadshotPanel headshotPanel;
    private Font font;
    private ZMainButton registerButton;
    private ExitButton exitButton;
    private ZPasswordField nameField;
    private ZPasswordField passwordField;
    private ZPasswordField repeatField;
    private ZRadioButton man;
    private ZRadioButton woman;
    private CheckLabel nameCheck;
    private CheckLabel sexCheck;
    private CheckLabel passwordCheck;
    private CheckLabel repeatCheck;
    private RegisterFrame() {
        init();

        addHeadshotPanel();
        addText();
        addSexGroup();
        addRegisterButton();
        addExitButton();
        addLogin();

        addBackground();
        setVisible(true);
    }

    public static void main(String[] args) {
        getRegisterFrame();
    }

    public static RegisterFrame getRegisterFrame() {
        if (registerFrame == null) {
            registerFrame = new RegisterFrame();
            registerFrame.setVisible(false);
        }
        return registerFrame;
    }

    private void addLogin() {
        ZButton register = new ZButton(200, 100, 30, ZButton.LOGIN);
        add(register);
        JLabel jLabel = new JLabel();
        jLabel.setBounds(200, 130, 40, 20);
        add(jLabel);
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                toLoginFrame();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setText("登录");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setText("");
            }
        });
    }

    private void toLoginFrame() {
        LoginFrame.getLoginFrame().setBounds(getBounds());
        LoginFrame.getLoginFrame().setVisible(true);
        setVisible(false);


    }

    private void addHeadshotPanel() {
        headshotPanel = new HeadshotPanel(600, 200, 150, HeadshotPanel.SIDE);
        add(headshotPanel);
    }

    private void addSexGroup() {
        ButtonGroup sexGroup = new ButtonGroup();
        man = new ZRadioButton(270, 210, 80, 35, "男");
        woman = new ZRadioButton(360, 210, 80, 35, "女");
        sexGroup.add(man);
        sexGroup.add(woman);
        add(man);
        add(woman);
        man.addActionListener(e -> headshotPanel.changeSex(HeadshotPanel.BOY));
        woman.addActionListener(e -> headshotPanel.changeSex(HeadshotPanel.GIRL));
    }


    private void addText() {

        JLabel title = new JLabel("注册界面");
        JLabel name = new JLabel("姓名");
        JLabel sex = new JLabel("性别");
        JLabel password = new JLabel("密码");
        JLabel repeat = new JLabel("重复密码");
        JLabel headshot = new JLabel("头像");

        title.setBounds(getWidth() / 2 - 100, 60, 300, 70);
        name.setBounds(200, 150, 300, 50);
        sex.setBounds(200, 200, 300, 50);
        password.setBounds(200, 250, 300, 50);
        repeat.setBounds(200, 300, 300, 50);
        headshot.setBounds(650, 150, 300, 50);

        nameField = new ZPasswordField("", 270, 150, 180, 50);
        passwordField = new ZPasswordField("", 270, 250, 180, 50);
        repeatField = new ZPasswordField("", 330, 300, 120, 50);

        nameCheck = new CheckLabel(450, 150, 200, 50);
        sexCheck = new CheckLabel(450, 200, 200, 50);
        passwordCheck = new CheckLabel(450, 250, 200, 50);
        repeatCheck = new CheckLabel(450, 300, 200, 50);

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
        add(nameCheck);
        add(sexCheck);
        add(passwordCheck);
        add(repeatCheck);
    }

    private void init() {
        font = FontLoader.getFont();
        setLayout(null);
        setBounds(500, 500, 995, 522);
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
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
    }

    private void addRegisterButton() {
        registerButton = new ZMainButton(getWidth() / 2 - 60, 350, 150, 100, "注册");
        addRegisterButtonListener();
        add(registerButton);
    }

    private boolean check() {
        return nameCheck() & sexCheck() & passwordCheck() & repeatCheck();
    }

    private boolean repeatCheck() {
        String password = new String(passwordField.getPassword());
        String repeat = new String(repeatField.getPassword());
        if (repeat.equals(password)) {
            repeatCheck.clean();
            return true;
        }
        repeatCheck.setText("两次输入不一致");
        return false;
    }

    private boolean passwordCheck() {
        String password = new String(passwordField.getPassword());
        if (password.length() < 6) {
            passwordCheck.setText("密码长度大于6");
            return false;
        }
        passwordCheck.clean();
        return true;
    }

    private boolean sexCheck() {
        if (man.isSelected() || woman.isSelected()) {
            sexCheck.clean();
            return true;
        }
        sexCheck.setText("请选择性别");
        return false;
    }

    private boolean nameCheck() {
        String name = new String(nameField.getPassword());
        if (name.length() < 3 || name.length() > 10) {
            nameCheck.setText("姓名长度需在3-10之间");
            return false;
        }
        if (name.charAt(0) == '_') {
            nameCheck.setText("请勿以下划线开头");
            return false;
        }
        nameCheck.clean();
        return true;
    }

    private void addRegisterButtonListener() {
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (check()) {
                    if (Client.isSocketExist()) {
                        Client.addMessage(new RegisterMessage(getUser()));
                    } else {
                        System.out.println("服务器不存在，请重启客户端或联系管理员");
                    }
                } else {
                    System.out.println("请完善信息");
                }
            }

            @NotNull
            private User getUser() {
                String name = new String(nameField.getPassword());
                String sex;
                String password = new String(passwordField.getPassword());
                if (man.isSelected()) {
                    sex = "男";
                } else {
                    sex = "女";
                }
                return new User(0, name, sex, password, 0, 0, headshotPanel.getImageNumber());
            }
        });
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

    public void duplicateUsername() {
        nameCheck.setText("用户名重复");
    }

    public void success() {
        nameCheck.clean();
        LoginFrame.successRegister();
    }

    public ZPasswordField getNameField() {
        return nameField;
    }

    public ZPasswordField getPasswordField() {
        return passwordField;
    }


}
