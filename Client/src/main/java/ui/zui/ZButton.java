package ui.zui;

import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;

/**
 * @author Anselm
 * @date 2024/3/10 20 57
 * description
 */

public class ZButton extends JButton {
    public static final int REGISTER = 0;
    public static final int RETURN = 1;
    public static final int SETTING = 2;
    public static final int LOGIN = 3;
    private static final ImageIcon registerImageIcon;
    private static final ImageIcon returnImageIcon;
    private static final ImageIcon settingImageIcon;
    private static final ImageIcon loginImageIcon;

    static {
        String registerPath = "images/register.png";
        String returnPath = "images/return.png";
        String settingPath = "images/setting.png";
        String loginPath = "images/login.png";
        registerImageIcon = ImageLoader.load(registerPath);
        returnImageIcon = ImageLoader.load(returnPath);
        settingImageIcon = ImageLoader.load(settingPath);
        loginImageIcon = ImageLoader.load(loginPath);
    }

    private ZButton() {
    }

    public ZButton(int x, int y, int width, int type) {
        init(x, y, width, type);
    }

    private void init(int x, int y, int width, int type) {
        setBounds(x, y, width, width);
//        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(null);
        setLayout(null);
        switch (type) {
            case REGISTER -> setIcon(ImageResizer.resize(registerImageIcon, width, ImageResizer.WIDTH));
            case RETURN -> setIcon(ImageResizer.resize(returnImageIcon, width, ImageResizer.WIDTH));
            case SETTING -> setIcon(ImageResizer.resize(settingImageIcon, width, ImageResizer.WIDTH));
            case LOGIN -> setIcon(ImageResizer.resize(loginImageIcon, width, ImageResizer.WIDTH));
        }

    }

}
