package ui.zui;

import org.jetbrains.annotations.NotNull;
import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/2/20 17 33
 * description
 */

public class HeadshotPanel extends JPanel {
    static ImageIcon[] headshotsBoy;
    static ImageIcon[] headshotsGirl;
    public static final boolean BOY = true;
    public static final boolean GIRL = false;
    int imageNumber;
    boolean sex;
    JLabel headshot;
    ArrowButton leftButton;
    ArrowButton rightButton;

    static {
        headshotsBoy = new ImageIcon[10];
        headshotsGirl = new ImageIcon[10];
    }

    public HeadshotPanel(int x, int y, int width) {
        init(x, y, width);
        sex = BOY;
        imageNumber = 0;
        setImage();
        setVisible(true);
    }


    public HeadshotPanel(int x, int y, int width, int imageNumber, boolean sex) {
        init(x, y, width);
        this.sex = sex;
        this.imageNumber = imageNumber;
        setImage();
        setVisible(true);
    }

    private void init(int x, int y, int width) {
        setBounds(x, y, width, (int) (width + width * 0.4));
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        addArrowButton();
        headshot = new JLabel();
        headshot.setBounds(0, 0, width, width);
        add(headshot);
    }

    private void addArrowButton() {
        leftButton = ArrowButton.getLeftButton(0, getHeight() - (int) (getWidth() * 0.3), (int) (getWidth() * 0.3), (int) (getWidth() * 0.3), this);
        rightButton = ArrowButton.getRightButton((int) (getWidth() * 0.66), getHeight() - (int) (getWidth() * 0.3), (int) (getWidth() * 0.3), (int) (getWidth() * 0.3), this);
        add(leftButton);
        add(rightButton);
    }

    private void setImage() {
        if (sex == BOY) {
            if (headshotsBoy[imageNumber] == null) {
                headshotsBoy[imageNumber] = ImageLoader.load("images/headshot/boy_" + imageNumber + ".png");
            }
            headshot.setIcon(ImageResizer.resize(headshotsBoy[imageNumber], getWidth()));
        } else {
            if (headshotsGirl[imageNumber] == null) {
                headshotsGirl[imageNumber] = ImageLoader.load("images/headshot/girl_" + imageNumber + ".png");
            }
            headshot.setIcon(ImageResizer.resize(headshotsGirl[imageNumber], getWidth()));
        }
    }

    public void setImage(int imageNumber, boolean sex) {
        if (imageNumber < 0 || imageNumber > 9) imageNumber = 0;
        setImage();
    }

    private void leftImage() {
        imageNumber++;
        if (imageNumber > 9) imageNumber = 0;
        setImage();
    }

    private void rightImage() {
        imageNumber--;
        if (imageNumber < 0) imageNumber = 9;
        setImage();
    }

    private void changeSex(boolean sex) {
        this.sex = sex;
        imageNumber = 0;
        setImage();
    }

    public static class ArrowButton extends JButton {
        static ImageIcon left;
        static ImageIcon right;
        static final boolean LEFT = true;
        static final boolean RIGHT = false;
        boolean direction;
        HeadshotPanel headshotPanel;

        static {
            left = ImageLoader.load("images/left.png");
            right = ImageLoader.load("images/right.png");
        }

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
            arrowButton.setIcon(ImageResizer.resize(left, high));
            return arrowButton;
        }

        public static @NotNull HeadshotPanel.ArrowButton getRightButton(int x, int y, int width, int high, HeadshotPanel headshotPanel) {
            ArrowButton arrowButton = new ArrowButton(x, y, width, high, RIGHT, headshotPanel);
            arrowButton.setIcon(ImageResizer.resize(right, high));
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
