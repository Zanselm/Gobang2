package ui.zui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Anselm
 * @date 2024/2/9 22 22
 * description
 */

public class PositionDraggingListener {
    public static void addPositionDraggingListener(@NotNull JFrame frame){
        Point origin = new Point();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
    }
}
