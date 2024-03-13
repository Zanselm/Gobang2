package ui.zui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/11 18 40
 * description
 */

public class CheckLabel extends JLabel {

    public static final int PASS = 1;
    public static final int FAILED = 2;
    private static final int NULL = 0;
    private int sign;

    public CheckLabel() {
        super();
        sign = FAILED;
    }

    public CheckLabel(int x, int y, int width, int length) {
        super();
        setBounds(x, y, width, length);
        sign = FAILED;
    }

    public void setFontSize(int fontSize) {
        setFont(new Font(getFont().getFontName(), getFont().getStyle(), fontSize));
    }

    public void clean() {
        sign = NULL;
        setText("");
        sign = FAILED;
    }

    @Override
    public void setText(@NotNull String text) {
        if (sign == NULL) {
            super.setText("");
        }
        if (sign == PASS) {
            setForeground(Color.GREEN);
            super.setText("√ " + text);
        }
        if (sign == FAILED) {
            setForeground(Color.red);
            super.setText("X " + text);
        }
    }

    public void pass() {
        sign = PASS;
        setText("");
        sign = FAILED;
    }

    public void pass(String text) {
        sign = PASS;
        setText(text);
        sign = FAILED;
    }
}
