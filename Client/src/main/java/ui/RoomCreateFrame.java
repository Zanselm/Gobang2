package ui;

import constant.GameConstant;
import entity.Room;
import net.Client;
import net.LocalUser;
import net.message.CreateRoomMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.zui.*;
import utils.FontLoader;
import utils.MyGson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/3/15 15 18
 * description
 */

public class RoomCreateFrame extends JFrame implements GameConstant {
    private static final Font font;
    static HashMap<String, Integer> roomMap;
    private static RoomCreateFrame roomCreateFrame;

    static {
        font = FontLoader.getFont();
        roomMap = new HashMap<>();
        roomMap.put("联网", ONLINE_GAME_TWO_PLAYER);
        roomMap.put("本地AI", CONSOLE_GAME_AI);
        roomMap.put("本地双人", CONSOLE_GAME_TWO_PLAYER);
        roomMap.put("我方", OUR);
        roomMap.put("对方", OTHER_SIDE);
        roomMap.put("随机", RANDOM);
        roomMap.put("可以", CAN);
        roomMap.put("不可以", CANT);

    }

    private final boolean isClientExist = Client.isSocketExist();
    ZTextField nameField;
    ZTextField instructionField;
    ButtonGroup gameType;
    ButtonGroup whoFirst;
    ButtonGroup observable;
    private ExitButton exitButton;
    private ZMainButton createButton;

    private RoomCreateFrame() throws HeadlessException {
        init();

        addExitButton();
        addCreatButton();
        addTitle();
        addText();

        addBackground();
        setVisible(true);
    }

    public static void main(String[] args) {
        RoomCreateFrame.getRoomCreateFrame();
    }

    public static RoomCreateFrame getRoomCreateFrame() {
        if (roomCreateFrame == null) {
            roomCreateFrame = new RoomCreateFrame();
        }
        return roomCreateFrame;
    }

    private void addText() {
        int start_Y = 120;
        int start_X = 200;
        int interval;
        if (isClientExist) {
            interval = 40;
        } else {
            interval = 50;
        }
        int width = 100;
        add(new ZLabel(start_X, start_Y, width, 20, "房间名："));
        add(new ZLabel(start_X, start_Y + interval, width, 20, "简介语："));
        add(new ZLabel(start_X, start_Y + interval * 2, width, 20, "类型："));
        add(new ZLabel(start_X, start_Y + interval * 3, width, 20, "先手："));
        if (isClientExist) {
            add(new ZLabel(start_X, start_Y + interval * 4, width, 20, "观战："));
        }

        nameField = new ZTextField(this, start_X + width / 3 * 2, start_Y, width * 2, 20, "请填写房间名");
        instructionField = new ZTextField(this, start_X + width / 3 * 2, start_Y + interval, width * 2, 20, "请填写简介语");
        add(nameField);
        add(instructionField);

        gameType = new ButtonGroup();
        ZRadioButton online = new ZRadioButton(start_X + 50, start_Y + interval * 2, width, 20, "联网");
        ZRadioButton offlineAI = new ZRadioButton(start_X + 120, start_Y + interval * 2, width, 20, "本地AI");
        ZRadioButton offlineTwoPlayer = new ZRadioButton(start_X + 210, start_Y + interval * 2, width + 20, 20, "本地双人");
        if (isClientExist) {
            gameType.add(online);
        } else {
            offlineAI.setLocation(start_X + 50, start_Y + interval * 2);
            offlineTwoPlayer.setLocation(start_X + 140, start_Y + interval * 2);
        }
        gameType.add(offlineAI);
        gameType.add(offlineTwoPlayer);
        add(gameType);

        whoFirst = new ButtonGroup();
        ZRadioButton our = new ZRadioButton(start_X + 50, start_Y + interval * 3, width, 20, "随机");
        ZRadioButton other = new ZRadioButton(start_X + 120, start_Y + interval * 3, width, 20, "我方");
        ZRadioButton random = new ZRadioButton(start_X + 190, start_Y + interval * 3, width, 20, "对方");
        whoFirst.add(our);
        whoFirst.add(other);
        whoFirst.add(random);
        add(whoFirst);

        observable = new ButtonGroup();
        ZRadioButton yes = new ZRadioButton(start_X + 50, start_Y + interval * 4, width, 20, "可以");
        ZRadioButton no = new ZRadioButton(start_X + 120, start_Y + interval * 4, width, 20, "不可以");
        observable.add(yes);
        observable.add(no);
        if (isClientExist) {
            add(observable);
        }
    }

    private void add(@NotNull ButtonGroup buttonGroup) {
        boolean sign = true;
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            ZRadioButton button = (ZRadioButton) buttons.nextElement();
            add(button);
            button.setSelected(sign);
            sign = false;
        }
    }

    private void addCreatButton() {
        createButton = new ZMainButton(270, 300, 170, 100, "创建");
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                get result = getResult();
                Room room = new Room(0, nameField.getText(), instructionField.getText(), LocalUser.getUserID()
                        , 0, result.gameTypeNum(), result.whoFirstNum(), result.observableBoolean());
                if (isClientExist) {
                    if (room.getGameType() == ONLINE_GAME_TWO_PLAYER) {
                        Client.addMessage(MyGson.toJson(new CreateRoomMessage(MyGson.toJson(room))));
                    } else {
                        new GameFrame(room.getGameType(), room.getWhoFirst());
                        dispose();
                    }
                } else {
                    new GameFrame(room.getGameType(), room.getWhoFirst());
                    System.out.println("本地游戏房间消息：" + MyGson.toJson(room));
                    dispose();
                }
            }

            @NotNull
            private get getResult() {
                ZRadioButton gameTypeButton = getSelectedButton(gameType);
                ZRadioButton whoFirstButton = getSelectedButton(whoFirst);
                ZRadioButton observableButton = getSelectedButton(observable);
                Integer gameTypeNum = null;
                if (gameTypeButton != null) {
                    gameTypeNum = roomMap.get(gameTypeButton.getName());
                }
                Integer whoFirstNum = null;
                if (whoFirstButton != null) {
                    whoFirstNum = roomMap.get(whoFirstButton.getName());
                }
                Integer observableNum = null;
                boolean observableBoolean;
                if (isClientExist) {
                    if (observableButton != null) {
                        observableNum = roomMap.get(observableButton.getName());
                    }
                    observableBoolean = observableNum == CAN;
                } else {
                    observableBoolean = false;
                }
                return new get(gameTypeNum, whoFirstNum, observableBoolean);
            }

            private @Nullable ZRadioButton getSelectedButton(@NotNull ButtonGroup buttonGroup) {
                Enumeration<AbstractButton> buttons = buttonGroup.getElements();
                while (buttons.hasMoreElements()) {
                    ZRadioButton button = (ZRadioButton) buttons.nextElement();
                    if (button.isSelected()) {
                        return button;
                    }
                }
                return null;
            }

            private record get(Integer gameTypeNum, Integer whoFirstNum, boolean observableBoolean) {
            }
        });
        add(createButton);
    }


    private void init() {
        setLayout(null);
        setBounds(0, 0, 706, 410);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setLocationRelativeTo(null);
        PositionDraggingListener.addPositionDraggingListener(this);
    }

    private void addExitButton() {
        exitButton = new ExitButton(580, 40, 40, 40);
        addExitListener();
        add(exitButton);
    }

    private void addExitListener() {
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }

    private void addTitle() {
        JLabel title = new JLabel("房间创建");
        title.setFont(font.deriveFont(40.0F));
        title.setBounds(280, 25, 200, 100);
        add(title);
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/popup_middle.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
    }
}
