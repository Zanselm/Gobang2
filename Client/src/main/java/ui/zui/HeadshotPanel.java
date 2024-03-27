package ui.zui;

import org.jetbrains.annotations.NotNull;
import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/2/20 17 33
 * description 头像面板
 */

public class HeadshotPanel extends JPanel {
    public static final boolean BOY = true;
    public static final boolean GIRL = false;
    public static final int BUTTON = 0;
    public static final int SIDE = 1;
    public static final int NOT = 2;
    static ImageIcon[] headshotsBoy;
    static ImageIcon[] headshotsGirl;

    static {
        headshotsBoy = new ImageIcon[10];
        headshotsGirl = new ImageIcon[10];
    }

    private final int width;
    private final int arrowLocation;
    private int imageNumber;
    private boolean sex;
    private JLabel headshot;
    private ArrowButton leftButton;
    private ArrowButton rightButton;

    /**
     * @param x             横坐标
     * @param y             纵坐标
     * @param width         头像框宽
     * @param arrowLocation 箭头按钮位置
     *                      SIDE 两边；
     *                      BUTTON 底部；
     *                      NOT 无箭头按钮；；
     */
    public HeadshotPanel(int x, int y, int width, int arrowLocation) {
        this.width = width;
        sex = BOY;
        imageNumber = 0;
        this.arrowLocation = arrowLocation;
        init(x, y);
    }


    public HeadshotPanel(int x, int y, int width, int imageNumber, boolean sex, int arrowLocation) {
        this.width = width;
        this.sex = sex;
        this.imageNumber = imageNumber;
        this.arrowLocation = arrowLocation;
        init(x, y);
    }

    private void init(int x, int y) {
        setBounds(x - (int) (width * 0.4), y, (int) (width + width * 0.8), (int) (width + width * 0.4));
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        addArrowButton();
        headshot = new JLabel();
        headshot.setBounds((int) (width * 0.4), 0, width, width);
        add(new LineBorderPanel(headshot.getX(),headshot.getY(),width,width));
        add(headshot);
        setImage();
        setVisible(true);
    }

    private void addArrowButton() {
        if (arrowLocation == NOT) {
            return;
        }
        if (arrowLocation == BUTTON) {
            leftButton = ArrowButton.getLeftButton((int) (width * 0.4), getHeight() - (int) (width * 0.3)
                    , (int) (width * 0.3), (int) (width * 0.3), this);
            rightButton = ArrowButton.getRightButton((int) (width * 0.66) + (int) (width * 0.4)
                    , getHeight() - (int) (width * 0.3), (int) (width * 0.3), (int) (width * 0.3), this);
        }
        if (arrowLocation == SIDE) {
            leftButton = ArrowButton.getLeftButton(0, (int) (width * 0.3), (int) (width * 0.3), (int) (width * 0.3)
                    , this);
            rightButton = ArrowButton.getRightButton(width + (int) (width * 0.5), (int) (width * 0.3), (int) (width * 0.3)
                    , (int) (width * 0.3), this);
        }
        add(leftButton);
        add(rightButton);
    }

    private void setImage() {
        if (sex == BOY) {
            if (headshotsBoy[imageNumber] == null) {
                headshotsBoy[imageNumber] = ImageLoader.load("images/headshot/boy_" + imageNumber + ".png");
            }
            headshot.setIcon(ImageResizer.resize(headshotsBoy[imageNumber], getWidth(), ImageResizer.WIDTH));
        } else if (sex == GIRL){
            if (headshotsGirl[imageNumber] == null) {
                headshotsGirl[imageNumber] = ImageLoader.load("images/headshot/girl_" + imageNumber + ".png");
            }
            headshot.setIcon(ImageResizer.resize(headshotsGirl[imageNumber], getWidth(), ImageResizer.WIDTH));
        }
    }

    public void setImage(int imageNumber, boolean sex) {
        if (imageNumber < 0 || imageNumber > 9) imageNumber = 0;
        this.imageNumber = imageNumber;
        this.sex = sex;
        setImage();
    }

    private void leftImage() {
        imageNumber--;
        if (imageNumber < 0) imageNumber = 9;
        setImage();
    }

    private void rightImage() {
        imageNumber++;
        if (imageNumber > 9) imageNumber = 0;
        setImage();
    }

    public void changeSex(boolean sex) {
        this.sex = sex;
        imageNumber = 0;
        setImage();
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public String getSex() {
        if (sex == BOY) {
            return "男";
        } else {
            return "女";
        }
    }

    public static class ArrowButton extends JButton {
        static final boolean LEFT = true;
        static final boolean RIGHT = false;
        static ImageIcon left;
        static ImageIcon right;

        static {
            left = ImageLoader.load("images/left.png");
            right = ImageLoader.load("images/right.png");
        }

        boolean direction;
        HeadshotPanel headshotPanel;

        private ArrowButton() {
        }

        private ArrowButton(int x, int y, int width, int high, boolean direction, HeadshotPanel headshotPanel) {
            init();
            this.direction = direction;
            this.headshotPanel = headshotPanel;
            setBounds(x, y, width, high);
            if (direction == LEFT) {
                addActionListener(e -> headshotPanel.leftImage());
            } else {
                addActionListener(e -> headshotPanel.rightImage());
            }

        }

        public static @NotNull HeadshotPanel.ArrowButton getLeftButton(int x, int y, int width, int high, HeadshotPanel headshotPanel) {
            ArrowButton arrowButton = new ArrowButton(x, y, width, high, LEFT, headshotPanel);
            arrowButton.setIcon(ImageResizer.resize(left, high, ImageResizer.HIGH));
            return arrowButton;
        }

        public static @NotNull HeadshotPanel.ArrowButton getRightButton(int x, int y, int width, int high, HeadshotPanel headshotPanel) {
            ArrowButton arrowButton = new ArrowButton(x, y, width, high, RIGHT, headshotPanel);
            arrowButton.setIcon(ImageResizer.resize(right, high, ImageResizer.HIGH));
            return arrowButton;
        }

        private void init() {
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(null);
            setLayout(null);
        }

    }

}
