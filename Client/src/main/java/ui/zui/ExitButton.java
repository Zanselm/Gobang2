package ui.zui;

import utils.ImageResizer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/2/8 12 08
 * description 退出按钮
 */

public class ExitButton extends JButton {
    static ImageIcon blackExit;
    static ImageIcon redExit;

    static {
        URL blackURL = Objects.requireNonNull(ExitButton.class.getClassLoader().getResource("images/exit_black.png"));
        URL redURL = Objects.requireNonNull(ExitButton.class.getClassLoader().getResource("images/exit_red.png"));
        blackExit = new ImageIcon(blackURL);
        redExit = new ImageIcon(redURL);
    }

    ImageIcon redFinalIcon;
    ImageIcon blackFinalIcon;

    public ExitButton(int x, int y, int width, int height) {
        init(x, y, width, height);
        resize();
        setIcon(blackFinalIcon);
        addListener();
    }

    private void init(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(null);
    }

    private void resize() {
//        Image redExitImage = redExit.getImage();
//        redExitImage = redExitImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
//        redFinalIcon = new ImageIcon(redExitImage);
        redFinalIcon = ImageResizer.resize(redExit, getWidth(), getHeight());

//        Image blackExitImage = blackExit.getImage();
//        blackExitImage = blackExitImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
//        blackFinalIcon = new ImageIcon(blackExitImage);
        blackFinalIcon = ImageResizer.resize(blackExit, getWidth(), getHeight());
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setIcon(redFinalIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setIcon(blackFinalIcon);
            }
        });
    }
}
