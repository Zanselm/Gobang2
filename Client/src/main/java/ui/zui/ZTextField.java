package ui.zui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.FontLoader;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

public class ZTextField extends JTextField {
    private Font font = FontLoader.getFont();
    private JFrame frame;
    private String initText;

    private ZTextField() {
        init();
    }

    public ZTextField(JFrame frame) {
        init();
        setFont(font);
        this.frame = frame;
    }

    public ZTextField(JFrame frame, int x, int y, int width, int height) {
        this.initText = "";
        setBounds(x, y, width, height);
        init();
        setFont(font.deriveFont((float) (height * 0.9)));
        this.frame = frame;
    }

    public ZTextField(JFrame frame, int x, int y, int width, int height, String text) {
        this.initText = text;
        setBounds(x, y, width, height);
        init();
        setFont(font.deriveFont((float) (height * 0.9)));
        setText(text);
        this.frame = frame;
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                frame.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                frame.repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                frame.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (getText().equals(initText)){
                    setText("");
                }
                frame.repaint();
            }
        });

        int delay=500;    //时间间隔
        ActionListener taskPerformer= e -> {
//                getCaret().setDot(getCaret().getDot());
            if (isFocusOwner()){
                getCaret().setVisible(!getCaret().isVisible());
            }else {
                getCaret().setVisible(false);
            }

            frame.repaint();
        };
        new Timer(delay,taskPerformer).start();

//        闪烁问题未完全解决
//        new Thread(cursorFlush()).start();
    }


//    @Contract(pure = true)
//    private @NotNull Runnable cursorFlush() {
//        return () -> {
//            while (frame != null) {
//                if (hasFocus()) {
//                    setCaret(new DefaultCaret() {
//                        public boolean isVisible() {
//                            return true;
//                        }
//                    });
//                    getCaret().setDot(getText().length());
//                    frame.repaint();
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    setCaret(new DefaultCaret() {
//                        public boolean isVisible() {
//                            return false;
//                        }
//                    });
//                    getCaret().setDot(getText().length());
//                    frame.repaint();
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//            if (frame == null) {
//                Thread.currentThread().interrupt();
//            }
//        };
//    }


}
