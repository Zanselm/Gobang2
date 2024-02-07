package ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

/**
 * @author Anselm
 * @date 2024/2/4 12 22
 * description
 */

public class LoginFrame extends JFrame {
    Font font;
    JTextField accountField;
    JPasswordField passwordField;
    public static void main(String[] args) {
        new LoginFrame();
    }
    public LoginFrame() throws HeadlessException {
        init();

        addTextField();
        addLoginButton();
        addExitButton();
        addBackground();
        addMouseListener();

        setVisible(true);
    }

    private void addMouseListener() {
        Point origin = new Point();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point p = getLocation();
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
    }

    private void addBackground() {
        int width = getWidth();
        int height = getHeight();
        ImageIcon icon1 = new ImageIcon("D:\\source\\idea\\Gobang2\\Client\\src\\main\\resources\\images\\登录2.png");

        Image image = icon1.getImage();
        image = image.getScaledInstance(width,height,Image.SCALE_FAST);
        icon1= new ImageIcon(image);
        JLabel label = new JLabel(icon1);
        label.setBounds(0,0,width,height);
        add(label);

    }


    private void addLoginButton() {
        JButton loginButton = new JButton("");
        loginButton.setFont(font);
//        loginButton.setForeground(Color.white);

        loginButton.setBounds(getWidth()/2-60,330,200,144);
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(null);

        ImageIcon icon1 = new ImageIcon("D:\\source\\idea\\Gobang2\\Client\\src\\main\\resources\\images\\button.png");

        Image image = icon1.getImage();
        image = image.getScaledInstance(loginButton.getWidth(),loginButton.getHeight(),Image.SCALE_FAST);
        icon1= new ImageIcon(image);
        loginButton.setIcon(icon1);
        loginButton.setLayout(null);

        JLabel label = new JLabel("登录");
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setBounds(loginButton.getWidth()/6,0,loginButton.getWidth(),loginButton.getHeight());
        loginButton.add(label);
        add(loginButton);

        ImageIcon finalIcon = icon1;
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setForeground(Color.black);
                loginButton.setIcon(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setForeground(Color.white);
                loginButton.setIcon(finalIcon);
            }
        });
    }

    private void addExitButton() {
        JButton exitButton = new JButton();
        exitButton.setFont(font);
        exitButton.setBounds(820,70,50,50);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(null);

        ImageIcon icon2 = new ImageIcon("D:\\source\\idea\\Gobang2\\Client\\src\\main\\resources\\images\\退出2.png");
        ImageIcon icon1 = new ImageIcon("D:\\source\\idea\\Gobang2\\Client\\src\\main\\resources\\images\\退出1.png");

        Image image = icon1.getImage();
        image = image.getScaledInstance(exitButton.getWidth(),exitButton.getHeight(),Image.SCALE_FAST);
        icon1= new ImageIcon(image);

        Image image2 = icon2.getImage();
        image2 = image2.getScaledInstance(exitButton.getWidth(),exitButton.getHeight(),Image.SCALE_FAST);
        icon2= new ImageIcon(image2);

        exitButton.setIcon(icon2);
        add(exitButton);
        ImageIcon finalIcon1 = icon1;
        ImageIcon finalIcon2 = icon2;
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exitButton.setIcon(finalIcon1);
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                exitButton.setIcon(finalIcon2);
            }
        });
    }

    private void addTextField() {
        accountField = new JTextField("账号");
        passwordField = new JPasswordField();
        int width = 100;
        int height = 50;
        accountField.setBounds(350,200,width,height);
        passwordField.setBounds(350,300,width,height);
        accountField.setVisible(true);
        passwordField.setVisible(true);
        accountField.setOpaque(false);
        passwordField.setOpaque(false);
        MatteBorder border = new MatteBorder(0, 0, 0, 0,new Color(255, 255, 255));
        accountField.setBorder(border);
        passwordField.setBorder(border);
        accountField.setFont(font);
        passwordField.setFont(font);
        add(accountField);
        add(passwordField);
    }

    private void init() {
        loadFont();
        setTitle("Login");
        setLayout(null);
        setBounds(500,500,995,522);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void loadFont() {
        try {
//            fonts = Font.createFonts(Objects.requireNonNull(LoginFrame.class.getClassLoader().getResourceAsStream("font/XuandongKaishu.ttf")));
            Font f = Font.createFont(Font.TRUETYPE_FONT,new File("D:\\source\\idea\\Gobang2\\Client\\src\\main\\resources\\font\\字小魂飞墨手书.ttf"));
            font = f.deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        this.setFont(font);
    }
}
