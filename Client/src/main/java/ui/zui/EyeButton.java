package ui.zui;

import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/2/10 21 06
 * description
 */

public class EyeButton extends JButton {
    static boolean VISIBLE = false;
    static ImageIcon visible;
    static ImageIcon invisible;

    static {
        URL visibleURL = Objects.requireNonNull(ZMainButton.class.getClassLoader().getResource("images/password_visible.png"));
        URL inVisibleURL = Objects.requireNonNull(ZMainButton.class.getClassLoader().getResource("images/password_invisible.png"));
        visible = new ImageIcon(visibleURL);
        invisible = new ImageIcon(inVisibleURL);
    }

    ZPasswordField passwordField;
    ImageIcon finalVisible;
    ImageIcon finalInvisible;

    public EyeButton(ZPasswordField passwordField, int x, int y, int width) {
        this.passwordField = passwordField;
        init(x, y, width);
        resizeIcon();
        setIcon(finalInvisible);
        addListener();
    }

    private void init(int x, int y, int width) {
        setBounds(x, y, width, (int) (width * (375.0 / 655.0)));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(null);
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (VISIBLE) {
                    setIcon(finalInvisible);
                    passwordField.hidePassword();
                    VISIBLE = false;
                } else {
                    setIcon(finalVisible);
                    passwordField.showPassword();
                    VISIBLE = true;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setOpaque(false);
            }
        });
    }

    private void resizeIcon() {
        Image visibleImage = visible.getImage();
        visibleImage = visibleImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
        finalVisible = new ImageIcon(visibleImage);
        finalVisible = ImageResizer.resize(visible, getWidth(), getHeight());

        Image invisibleImage = invisible.getImage();
        invisibleImage = invisibleImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
        finalInvisible = new ImageIcon(invisibleImage);
        finalInvisible = ImageResizer.resize(invisible, getWidth(), getHeight());
    }
}
