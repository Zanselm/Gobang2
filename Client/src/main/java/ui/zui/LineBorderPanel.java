package ui.zui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/27 09 17
 * description
 */

public class LineBorderPanel extends JPanel {
    public LineBorderPanel(int x,int y,int width,int height){
        setBackground(new Color(0,0,0,0));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setBounds(x,y,width,height);
    }
}
