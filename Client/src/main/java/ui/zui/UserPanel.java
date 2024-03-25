package ui.zui;

import entity.User;
import utils.FontLoader;
import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/14 19 19
 * description
 */

public class UserPanel extends JPanel {
    static Font font;
    static ImageIcon black;
    static ImageIcon white;

    static {
        font = FontLoader.getFont();
        white = ImageResizer.resize(ImageLoader.load("images/white.png"),40,ImageResizer.WIDTH);
        black = ImageResizer.resize(ImageLoader.load("images/blake.png"),40,ImageResizer.WIDTH);
    }

    User user;
    HeadshotPanel headshotPanel;
    JPanel userInformationPanel;
    JLabel pieceType;
    JLabel prompt;
    boolean isGameFrame;

    public UserPanel(User user, int x, int y, int width, int height) {
        this.user = user;
        isGameFrame = false;
        init(x, y, width, height);
        addUerInformation();
    }

    public UserPanel(User user, int x, int y, int width, int height, boolean isGameFrame) {
        this.user = user;
        this.isGameFrame = isGameFrame;
        init(x, y, width, height);
        addUerInformation();
    }

    private void addUerInformation() {
        if (isGameFrame) {
            if (user.getSex().equals("男")) {
                headshotPanel = new HeadshotPanel(getWidth()/4, 0, getWidth()/2, user.getAvatar(), HeadshotPanel.BOY, HeadshotPanel.NOT);
            } else {
                headshotPanel = new HeadshotPanel(getWidth()/4, 0, getWidth()/2, user.getAvatar(), HeadshotPanel.GIRL, HeadshotPanel.NOT);
            }
        }else {
            if (user.getSex().equals("男")) {
                headshotPanel = new HeadshotPanel(0, 0, getHeight(), user.getAvatar(), HeadshotPanel.BOY, HeadshotPanel.NOT);
            } else {
                headshotPanel = new HeadshotPanel(0, 0, getHeight(), user.getAvatar(), HeadshotPanel.GIRL, HeadshotPanel.NOT);
            }
        }
        add(headshotPanel);


        if (isGameFrame) {
            userInformationPanel = new JPanel(new GridLayout(6, 1));
            userInformationPanel.setBounds(0, getWidth()/2, getWidth(), getHeight() - getWidth() - getHeight() / 4);
            addInfo();
            pieceType = new JLabel("",SwingConstants.CENTER);
            setPieceType("白");
            prompt = new JLabel("提示",SwingConstants.CENTER);
            pieceType.setFont(font);
            prompt.setFont(font);
            userInformationPanel.add(pieceType);
            userInformationPanel.add(prompt);

        } else {
            userInformationPanel = new JPanel(new GridLayout(2, 1));
            userInformationPanel.setBounds(getHeight() + getHeight() / 4, 0, getWidth() - getHeight() - getHeight() / 4, getHeight());
            addInfo();
        }
        add(userInformationPanel);
    }

    private void addInfo() {
        userInformationPanel.setBackground(new Color(0, 0, 0, 0));

        JLabel name = new JLabel(user.getName(),SwingConstants.CENTER);
        JLabel id = new JLabel("ID: " + user.getId(),SwingConstants.CENTER);
        JLabel win = new JLabel("胜场：" + user.getWin(),SwingConstants.CENTER);
        JLabel lose = new JLabel("败场：" + user.getLose(),SwingConstants.CENTER);

        name.setFont(font);
        id.setFont(font);
        win.setFont(font);
        lose.setFont(font);


        userInformationPanel.add(name);
        userInformationPanel.add(id);
        userInformationPanel.add(win);
        userInformationPanel.add(lose);
    }

    public void setPieceType(String s) {
        if (s.equals("白")) {
            pieceType.setText("执白");
            pieceType.setIcon(white);
            flush();
        }
        if (s.equals("黑")) {
            pieceType.setText("执黑");
            pieceType.setIcon(black);
            flush();
        }
        if (s.equals("随机")) {
            pieceType.setText(s);
            flush();
        }
    }

    public void setPrompt(String s) {
        prompt.setText(s);
        flush();
    }

    private void flush() {
        setVisible(false);
        setVisible(true);
    }

    private void init(int x, int y, int width, int height) {
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setBounds(x, y, width, height);
    }
}
