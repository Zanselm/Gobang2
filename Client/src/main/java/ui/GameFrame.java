package ui;

import constant.GameConstant;
import domain.Chessboard;
import domain.Player;
import entity.Room;
import entity.User;
import net.Client;
import net.LocalUser;
import net.message.CompareNumMessage;
import net.message.GiveUpMessage;
import net.message.Message;
import ui.zui.ExitButton;
import ui.zui.GamePanel;
import ui.zui.PositionDraggingListener;
import utils.FontLoader;
import utils.MusicPlayer;
import utils.MyGson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    public Player playerLLocal;
    public Player playerR;
    JLabel waiting;
    private ExitButton exitButton;
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
        addExitButton();
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
        addExitButton();
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
        addExitButton();
        addBackground();
        this.setVisible(true);
        this.setAlwaysOnTop(false);
    }

    private void addExitButton() {
        exitButton = new ExitButton(1300,200,80,80);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Client.isSocketExist()){
                    gamePanel.lose();
                    Client.addMessage(new GiveUpMessage(playerR.getId()));
                }
            }
        });
        add(exitButton);
    }

    private void addWaiting() {
        waiting = new JLabel("请等待玩家进入...",JLabel.CENTER);
        waiting.setFont(font.deriveFont(60f));
        waiting.setBounds(450,500,680,75);
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
            pieceType = WHITE;
        }else {
            pieceType = RANDOM;
        }
        playerR = new Player(user,pieceType);
        System.out.println(MyGson.toJson(playerR));
        gamePanel.setVisible(true);
        waiting.setVisible(false);
        if (pieceType == RANDOM){
            System.out.println("发送数字"+number);
            Client.addMessage(new CompareNumMessage(number,playerR.getId()));
        }
    }
    public void compareNum(Message message){
        int otherSideNum = Integer.parseInt(message.getMessage());
        System.out.println("收到数字"+otherSideNum+"本地数字"+number);
        if (otherSideNum<number){
            playerLLocal.setPieceType(GameConstant.BLACK);
            System.out.println("黑");
            gamePanel.setWhoSign(GameConstant.BLACK);
        }
        if (otherSideNum>number){
            playerLLocal.setPieceType(GameConstant.WHITE);
            System.out.println("白");
            gamePanel.setWhoSign(GameConstant.WHITE);
        }
        if (otherSideNum==number){
            number = new Random().nextInt();
            Client.addMessage(new CompareNumMessage(number,playerR.getId()));
        }
    }
    public void addPiece(Message message){
        Chessboard.Piece piece = MyGson.fromJson(message.getMessage(), Chessboard.Piece.class);
        gamePanel.addPiece(piece);
    }
    public void lose(){
       gamePanel.lose();
    }

    public void victory() {
        gamePanel.victory();
    }
}
