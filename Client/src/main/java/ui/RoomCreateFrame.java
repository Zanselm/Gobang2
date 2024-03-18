package ui;

import net.Client;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.zui.*;
import utils.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/3/15 15 18
 * description
 */

public class RoomCreateFrame extends JFrame {
    private static final Font font;
    private static RoomCreateFrame roomCreateFrame;
    private ExitButton exitButton;
    private ZMainButton createButton;
    private boolean isClientExist = Client.isSocketExist();
    ZTextField nameField;
    ZTextField instructionField;
    ButtonGroup gameType;
    ButtonGroup whoFirst;
    ButtonGroup observable;

    static {
        System.setProperty("sun.java2d.noddraw", "true");
        font = FontLoader.getFont();
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

    private RoomCreateFrame() throws HeadlessException {
        init();

        addExitButton();
        addCreatButton();
        addTitle();
        addText();

        addBackground();
        setVisible(true);
    }

    private void addText() {
        int start_Y = 120;
        int start_X = 200;
        int interval;
        if (isClientExist){
            interval = 40;
        }else {
            interval = 50;
        }
        int width = 100;
        add(new ZLabel(start_X, start_Y, width, 20, "房间名："));
        add(new ZLabel(start_X, start_Y + interval, width, 20, "简介语："));
        add(new ZLabel(start_X, start_Y + interval * 2, width, 20, "类型："));
        add(new ZLabel(start_X, start_Y + interval * 3, width, 20, "先手："));
        if (isClientExist){
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
        if (isClientExist){
            gameType.add(online);
        }else {
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
        if (isClientExist){
            add(observable);
        }
    }

    private void add(@NotNull ButtonGroup buttonGroup) {
        boolean sign = true;
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            ZRadioButton button = (ZRadioButton)buttons.nextElement();
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
                ZRadioButton gameTypeButton = getSelectedButton(gameType);
                ZRadioButton whoFirstButton = getSelectedButton(whoFirst);
                ZRadioButton observableButton = getSelectedButton(observable);
                System.out.println(gameTypeButton.getName());
                System.out.println(whoFirstButton.getName());
                System.out.println(observableButton.getName());
            }

            private @Nullable ZRadioButton getSelectedButton(ButtonGroup buttonGroup) {
                Enumeration<AbstractButton> buttons = buttonGroup.getElements();
                while (buttons.hasMoreElements()) {
                    ZRadioButton button = (ZRadioButton)buttons.nextElement();
                    if (button.isSelected()){
                        return button;
                    }
                }
                return null;
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
