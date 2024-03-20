package domain.frame;

import constant.GameConstant;
import ui.LoginFrame;
import ui.zui.PositionDraggingListener;
import utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class GameFrame extends JFrame implements GameConstant {
    int gameType;
    int whoFirst;
    public static void main(String[] args) {
//        new GameFrame(CONSOLE_GAME_AI,BLACK);
        new GameFrame(CONSOLE_GAME_TWO_PLAYER,WHITE);
    }

    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;

    public GameFrame() {
        initFrame();
        addMusic();
        addGamePanel();
        addListener();
        this.setVisible(true);
    }
    public GameFrame(int gameType,int whoFirst) {
        this.gameType = gameType;
        if (whoFirst == OUR){
            this.whoFirst = BLACK;
        }
        if (whoFirst == OTHER_SIDE){
            this.whoFirst = WHITE;
        }
        if (whoFirst == RANDOM){
            this.whoFirst = new Random().nextInt(1,3);
        }
        initFrame();
        addMusic();
        addGamePanel();
        addListener();
        addBackground();
        this.setVisible(true);
    }

    private void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    musicPlayer.close();
                    gamePanel.getMusicPlayer().close();
                } catch (Exception ex) {
                    dispose();
                }
            }
        });
    }

    private void addMusic() {
        musicPlayer = new MusicPlayer();
        musicPlayer.load(GameFrame.class.getResourceAsStream("/audio/background.wav"));
        musicPlayer.setLoudness(-15f);
        musicPlayer.playLoop();
    }
    private void addGamePanel() {
        gamePanel = new GamePanel(gameType,whoFirst,this);
        gamePanel.setLocation(450,180);
        this.add(gamePanel);
    }

    private void initFrame(){
        this.setTitle("五子棋");
        this.setSize(1564,1000);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);
        this.setResizable(false);
        this.setLocation(getX(),getY()-50);
//        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        PositionDraggingListener.addPositionDraggingListener(this);
    }
    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/game_background.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0,0,getWidth(),getHeight());
        add(label);
    }
}
