package ui;

import entity.Room;
import entity.User;
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
 * @date 2024/3/14 19 01
 * description
 */

public class GameLobbyFrame extends JFrame {
    private static final Font font;
    private static GameLobbyFrame gameLobbyFrame;
    private ExitButton exitButton;
    private ZButton settingButton;
    private RoomListPanel roomListPanel;
    private ZMainButton createRoomButton;
    private ZMainButton enterRoomButton;

    static {
        font = FontLoader.getFont();
    }

    public static void main(String[] args) {
        GameLobbyFrame.getGameLobbyFrame();
    }

    public static GameLobbyFrame getGameLobbyFrame() {
        if (gameLobbyFrame == null) {
            gameLobbyFrame = new GameLobbyFrame();
        }
        return gameLobbyFrame;
    }

    private GameLobbyFrame() throws HeadlessException {
        init();

        addExitButton();
        addSettingButton();
        addTitle();
        addUserInformation();
        addRoomInformation();
        addCreateRoomButton();
        addEnterRoomButton();

        addBackground();
        setVisible(true);
    }

    private void addEnterRoomButton() {
        createRoomButton = new ZMainButton(750,450,300,120,"进入房间");
        add(createRoomButton);
    }

    private void addCreateRoomButton() {
        createRoomButton = new ZMainButton(750,250,300,120,"创建房间");
        add(createRoomButton);
    }

    private void addRoomInformation() {
        roomListPanel = new RoomListPanel(150, 250, 600, 400, 10);
        add(roomListPanel);
    }

    private void addSettingButton() {
        settingButton = new ZButton(920, 60, 40, ZButton.SETTING);
        addSettingListener();
        add(settingButton);
    }

    private void addSettingListener() {
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                roomListPanel.addRoom(new Room());
            }
        });
    }

    private void addTitle() {
        JLabel title = new JLabel("大厅");
        title.setFont(font.deriveFont(70.0F));
        title.setBounds(500, 50, 200, 100);
        add(title);
    }

    private void addUserInformation() {
        add(new UserPanel(new User(), 200, 150, 500, 100));
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/popup_large.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
    }

    public void addRoom(){

    }

    private void addExitButton() {
        exitButton = new ExitButton(1000, 60, 40, 40);
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

    private void init() {
        setLayout(null);
        setBounds(0, 0, 1197, 696);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setLocationRelativeTo(null);
        PositionDraggingListener.addPositionDraggingListener(this);
    }
}
