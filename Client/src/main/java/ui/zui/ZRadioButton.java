package ui.zui;

import utils.FontLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/3/5 23 21
 * description
 */

public class ZRadioButton extends JRadioButton{
    static Font font;
    static ImageIcon hook;
    static ImageIcon blankness;
    ImageIcon finalHook;
    ImageIcon finalBlankness;
    static {
        font = FontLoader.getFont();
        URL hookURL = Objects.requireNonNull(ZMainButton.class.getClassLoader().getResource("images/hook.png"));
        hook = new ImageIcon(hookURL);
        URL blanknessURL = Objects.requireNonNull(ZMainButton.class.getClassLoader().getResource("images/blankness.png"));
        blankness = new ImageIcon(blanknessURL);
    }
    private ZRadioButton(){}
    public ZRadioButton(int x, int y, int width, int height, String text){
        setFont(font.deriveFont(30f));
        setText(text);
        setOpaque(true);
        setBackground(new Color(0, 0, 0, 0));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBounds(x,y,width,height);
        resize();
        setIcon(finalBlankness);
        setSelectedIcon(finalHook);
    }
    private void resize(){
        int width = getHeight();
        finalHook = ImageResizer.resize(hook,width,ImageResizer.WIDTH);
        finalBlankness = ImageResizer.resize(blankness,width,ImageResizer.WIDTH);
    }
}
