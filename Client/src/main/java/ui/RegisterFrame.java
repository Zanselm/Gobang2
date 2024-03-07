package ui;

import net.Client;
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
    CheckLabel nameCheck;
    CheckLabel sexCheck;
    CheckLabel passwordCheck;
    CheckLabel repeatCheck;
    HeadshotPanel headshotPanel;
    public RegisterFrame() {
        init();

        addHeadshotPanel();
        addText();
        addSexGroup();
        addLoginButton();
        addExitButton();

        addBackground();
        setVisible(true);
    }

    private void addHeadshotPanel() {
        headshotPanel = new HeadshotPanel(600,200,150,HeadshotPanel.SIDE);
        add(headshotPanel);
    }

    private void addSexGroup() {
        ButtonGroup sexGroup = new ButtonGroup();
        man = new ZRadioButton(270,210,80,35,"男");
        woman = new ZRadioButton(360,210,80,35,"女");
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

        title.setBounds(getWidth() / 2-100, 60, 300, 70);
        name.setBounds(200, 150, 300, 50);
        sex.setBounds(200, 200, 300, 50);
        password.setBounds(200, 250, 300, 50);
        repeat.setBounds(200, 300, 300, 50);
        headshot.setBounds(650, 150, 300, 50);

        nameField = new ZPasswordField("",270, 150, 180, 50);
        passwordField = new ZPasswordField("",270, 250, 180, 50);
        repeatField = new ZPasswordField("",330, 300, 120, 50);

        nameCheck = new CheckLabel(450, 150, 200, 50,"");
        sexCheck = new CheckLabel(450, 200, 200, 50,"");
        passwordCheck = new CheckLabel(450, 250, 200, 50,"");
        repeatCheck = new CheckLabel(450, 300, 200, 50,"");

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
    private boolean check(){
        return nameCheck() & sexCheck() & passwordCheck() & repeatCheck();
    }

    private boolean repeatCheck() {
        String password = new String(passwordField.getPassword());
        String repeat = new String(repeatField.getPassword());
        if (repeat.equals(password)){
            repeatCheck.clean();
            return true;
        }
        repeatCheck.setText("两次输入不一致");
        return false;
    }

    private boolean passwordCheck() {
        String password = new String(passwordField.getPassword());
        if (password.length()<6){
            passwordCheck.setText("密码长度大于6");
            return false;
        }
        passwordCheck.clean();
        return true;
    }

    private boolean sexCheck() {
        if (man.isSelected() || woman.isSelected()){
            sexCheck.clean();
            return true;
        }
        sexCheck.setText("请选择性别");
        return false;
    }

    private boolean nameCheck() {
        String name = new String(nameField.getPassword());
        if (name.length()<4||name.length()>10){
            nameCheck.setText("姓名长度需在4-10之间");
            return false;
        }
        if (name.charAt(0) == '_'){
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
                if (check()){
                    if (Client.isSocketExist()){
                        Client.addMessage(new Message());
                    }else {
                        System.out.println("服务器不存在，请重启客户端或联系管理员");
                    }
                }else{
                    System.out.println("请完善信息");
                }
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
    private static class CheckLabel extends JLabel{
        public CheckLabel(){
            super();
            setForeground(Color.red);
        }
        public CheckLabel(int x,int y,int width,int length,String text){
            super();
            setText(text);
            setBounds(x,y,width,length);
            setForeground(Color.red);
        }
        public void setFontSize(int fontSize){
            setFont(new Font(getFont().getFontName(),getFont().getStyle(),fontSize));
        }
        public void clean(){
            setText("");
        }

        @Override
        public void setText(@NotNull String text) {
            if (text.isEmpty()){
                super.setText(text);
            }else {
                super.setText("X "+text);
            }
        }
    }
}
