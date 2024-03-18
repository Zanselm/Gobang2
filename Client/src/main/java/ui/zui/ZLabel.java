package ui.zui;

import utils.FontLoader;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/18 20 09
 * description
 */

public class ZLabel extends JLabel {
    private Font font = FontLoader.getFont();
    public ZLabel(){
        init();
    }
    public ZLabel(String text){
        init();
        setText(text);
    }
    public ZLabel(int x,int y,int width,int height,String text){
        init();
        setBounds(x,y,width,height);
        setText(text);
    }
    private void init(){
        setBounds(100,100,100,30);
        setFont(font.deriveFont((float) (getHeight()*0.9)));
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        setFont(font.deriveFont((float) (getHeight()*0.9)));
    }
}
