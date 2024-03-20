package domain.frame;

import constant.GameConstant;
import utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class GameFrame extends JFrame implements GameConstant {
    int gameType;
    public static void main(String[] args) {
        new GameFrame(CONSOLE_GAME_AI);
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
    public GameFrame(int gameType) {
        this.gameType = gameType;
        initFrame();
        addMusic();
        addGamePanel();
        addListener();
        this.setVisible(true);
    }

    private void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
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
        gamePanel = new GamePanel(gameType);
        gamePanel.setLocation(0,0);
        this.add(gamePanel);
    }

    private void initFrame(){
        this.setTitle("五子棋");
        this.setSize(955,680);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    private static class InfoPanel extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Image gobangimage = new ImageIcon(Objects.requireNonNull(GameFrame.class.getResource("/frame/resources/images/gobang.png"))).getImage();
            g.drawImage(gobangimage,0,0,100,37,null);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
