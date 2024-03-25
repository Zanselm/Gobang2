package ui;

import entity.Room;
import entity.User;
import net.Client;
import net.LocalUser;
import net.message.ByeMessage;
import net.message.EnterRoomMessage;
import net.message.GetRoomsMessage;
import net.message.Message;
import ui.zui.*;
import utils.FontLoader;
import utils.MyGson;

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
    private ZTextField textField;


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

        textField = new ZTextField(this,100,100,100,20,"1");
        add(textField);

        addExitButton();
        addSettingButton();
        addTitle();
        addUserInformation();
        addRoomInformation();
        addCreateRoomButton();
        addEnterRoomButton();

        addBackground();
        setVisible(true);

        Client.addMessage(new GetRoomsMessage());
    }

    private void addEnterRoomButton() {
        enterRoomButton = new ZMainButton(750,450,300,120,"进入房间");
        enterRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RoomListPanel.RoomInformationPanel selectedRIP = roomListPanel.getSelectedRIP();
                if (selectedRIP == null){
                    System.out.println("Room = NULL");
                }else {
                    Room room = selectedRIP.getRoom();
                    room.setUserR(LocalUser.getUserID());
                    System.out.println(MyGson.toJson(room));
                    Client.addMessage(new EnterRoomMessage(room));
                    new GameFrame(room);
                }
            }
        });
        add(enterRoomButton);
    }

    private void addCreateRoomButton() {
        createRoomButton = new ZMainButton(750,250,300,120,"创建房间");
        createRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RoomCreateFrame.getRoomCreateFrame().setVisible(true);
            }
        });
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
//                roomListPanel.addRoom(new Room(Integer.parseInt(textField.getText()),textField.getText(),"",0,0,0,0,true));
                roomListPanel.removeRoom(new Room(Integer.parseInt(textField.getText()),textField.getText(),"",0,0,0,0,true));
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
        if (LocalUser.localUser == null){
            add(new UserPanel(new User(), 200, 150, 700, 100));
        }else {
            add(new UserPanel(LocalUser.getLocalUser(), 200, 150, 700, 100));
        }
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/popup_large.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
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
                Client.addMessage(new ByeMessage());
                System.exit(0);
            }
        });
    }
    public void addRoom(Message message){
        roomListPanel.addRoom(MyGson.fromJson(message.getMessage(), Room.class));
    }
    public void addRooms(Message message){
        Room[] rooms = MyGson.fromJson(message.getMessage(), Room[].class);
        roomListPanel.addRoomList(rooms);
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
