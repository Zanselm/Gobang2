package ui.zui;

import utils.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Anselm
 * @date 2024/2/7 22 10
 * description
 */

public class ZPasswordField extends JPasswordField {
    static Font font;
    String text;
    int width = 0;
    int height = 0;
    int x = 0;
    int y = 0;

    static {
        font = FontLoader.getFont();
    }

    public ZPasswordField() {
        this.text = "";
        init();
    }

    public ZPasswordField(String text, int width, int height) {
        super(text);
        this.text = text;
        this.width = width;
        this.height = height;
        init();
    }

    public ZPasswordField(String text, int x, int y, int width, int height) {
        super(text);
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        setBounds(x, y, width, height);
        setVisible(true);
        setOpaque(false);
        setBorder(null);
        font = font.deriveFont((float) height / 3 * 2);
        setFont(font);
        setEchoChar('*');
        addListener();
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (text.equals(String.valueOf(getPassword()))) {
                    setText("");
                }
            }
        });
    }

    public void hidePassword() {
        setEchoChar('*');
    }

    public void hidePassword(char c) {
        setEchoChar(c);
    }

    public void showPassword() {
        setEchoChar((char) 0);
    }
    public String getContent(){
        return new String(getPassword());
    }

}
