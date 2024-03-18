package ui.zui;

import utils.FontLoader;
import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Anselm
 * @date 2024/2/8 12 30
 * description
 */

public class ZMainButton extends JButton {
    static Font font;
    static ImageIcon buttonBlack;
    static ImageIcon buttonWhite;
    JLabel textLabel;
    ImageIcon blackFinalIcon;
    ImageIcon whiteFinalIcon;

    static {
        font = FontLoader.getFont();
        buttonBlack = ImageLoader.load("images/button_black.png");
        buttonWhite = ImageLoader.load("images/blankness.png");
    }

    public ZMainButton(int x, int y, int width, int height, String text) {
        init(x, y, width, height);
        resize();
        setIcon(whiteFinalIcon);
        addText(text);
        addListener();
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                textLabel.setForeground(Color.white);
                setIcon(blackFinalIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                textLabel.setForeground(Color.black);
                setIcon(whiteFinalIcon);
            }
        });
    }

    private void addText(String text) {
        textLabel = new JLabel(text);
        textLabel.setForeground(Color.black);
        Font font1 = font.deriveFont((float) (getHeight()/2*0.9));
//        Font font1 = font.deriveFont(50f);
        textLabel.setFont(font1);
        textLabel.setBounds(getWidth() / 6, 0, getWidth(), getHeight());
        add(textLabel);
    }

    private void resize() {
        blackFinalIcon = ImageResizer.resize(buttonBlack,getWidth(),getHeight());
        whiteFinalIcon = ImageResizer.resize(buttonWhite,getWidth(),getHeight());
    }

    private void init(int x, int y, int width, int height) {
        setFont(font);
        setBounds(x, y, width, height);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(null);
        setLayout(null);
    }
}
