package domain.frame;

import constant.GameConstant;
import domain.Player;
import entity.Room;
import entity.User;
import net.Client;
import net.LocalUser;
import net.message.CompareNumMessage;
import net.message.Message;
import ui.LoginFrame;
import ui.zui.PositionDraggingListener;
import utils.FontLoader;
import utils.MusicPlayer;
import utils.MyGson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class GameFrame extends JFrame implements GameConstant {
    static Font font = FontLoader.getFont();
    static GameFrame gameFrame;
    int gameType;
    int whoFirst;
    Player playerLLocal;
    Player playerR;
    JLabel waiting;
    int number;

    public static void main(String[] args) {
//        new GameFrame(CONSOLE_GAME_AI,BLACK);
        new GameFrame(CONSOLE_GAME_TWO_PLAYER,WHITE);
    }

    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;
    public static GameFrame getGameFrame(){
        return gameFrame;
    }

    public GameFrame() {
        initFrame();
//        addPlayer();
        addMusic();
        addGamePanel();
        addListener();
        this.setVisible(true);
    }

    private void addPlayer() {
        if (LocalUser.getLocalUser() != null){
            playerLLocal = new Player(LocalUser.getLocalUser(),GameConstant.WHITE);
        }
    }

    public GameFrame(int gameType,int whoFirst) {
        gameFrame = this;
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
        if (gameType == ONLINE_GAME_TWO_PLAYER){
            addGamePanel();
            gamePanel.setVisible(false);
            addWaiting();
        }else {
            addGamePanel();
        }
        addListener();
        addBackground();
        this.setVisible(true);
        this.setAlwaysOnTop(false);
    }
    public GameFrame(Room room){
        gameFrame = this;
        this.gameType = room.getGameType();
        int whoFirst = room.getWhoFirst();
//        if (whoFirst == OUR){
//            this.whoFirst = BLACK;
//        }
//        if (whoFirst == OTHER_SIDE){
//            this.whoFirst = WHITE;
//        }
//        if (whoFirst == RANDOM){
//            this.whoFirst = new Random().nextInt(1,3);
//        }、
        if (room.getUserL() == LocalUser.getUserID()){
            if (whoFirst == OUR){
                playerLLocal = new Player(LocalUser.getLocalUser(),BLACK);
            }else if (whoFirst == OTHER_SIDE){
                playerLLocal = new Player(LocalUser.getLocalUser(),WHITE);
            }else {
                playerLLocal = new Player(LocalUser.getLocalUser(),RANDOM);
            }
        }else {
            if (whoFirst == OUR){
                playerLLocal = new Player(LocalUser.getLocalUser(),WHITE);
            }else if (whoFirst == OTHER_SIDE){
                playerLLocal = new Player(LocalUser.getLocalUser(),BLACK);
            }else {
                playerLLocal = new Player(LocalUser.getLocalUser(),RANDOM);
            }
        }
        number = new Random().nextInt();
        System.out.println(MyGson.toJson(playerLLocal));
        initFrame();
        addMusic();
        if (gameType == ONLINE_GAME_TWO_PLAYER){
            addGamePanel();
            gamePanel.setVisible(false);
            addWaiting();
        }else {
            addGamePanel();
        }
        addListener();
        addBackground();
        this.setVisible(true);
        this.setAlwaysOnTop(false);
    }

    private void addWaiting() {
        waiting = new JLabel("请等待玩家进入...",JLabel.CENTER);
        waiting.setFont(font.deriveFont(50f));
        waiting.setBounds(450,500,680,60);
        add(waiting);
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
        this.setAlwaysOnTop(true);
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
    public void playerEnterRoom(Message message){
        User user = MyGson.fromJson(message.getMessage(), User.class);
        int pieceType;
        if (playerLLocal.getPieceType() == WHITE){
            pieceType = BLACK;
        }else if (playerLLocal.getPieceType() == BLACK){
            pieceType = BLACK;
        }else {
            pieceType = RANDOM;
        }
        playerR = new Player(user,pieceType);
        System.out.println(MyGson.toJson(playerR));
        gamePanel.setVisible(true);
        waiting.setVisible(false);
        if (pieceType == RANDOM){
            Client.addMessage(new CompareNumMessage(number,playerR.getId()));
        }
    }
    public void compareNum(Message message){
        int otherSideNum = Integer.parseInt(message.getMessage());
        if (otherSideNum<number){
            playerLLocal.setPieceType(GameConstant.BLACK);
        }
        if (otherSideNum>number){
            playerLLocal.setPieceType(GameConstant.WHITE);
        }
        if (otherSideNum==number){
            number = new Random().nextInt();
            Client.addMessage(new CompareNumMessage(number,playerR.getId()));
        }
    }
}
