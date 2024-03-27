package ui;

import constant.GameConstant;
import domain.Chessboard;
import domain.Player;
import entity.Room;
import entity.User;
import net.Client;
import net.LocalUser;
import net.message.CompareNumMessage;
import net.message.GameOverMessage;
import net.message.GiveUpMessage;
import net.message.Message;
import ui.zui.*;
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
    public Room room;
    public Player playerR;
    public Player playerL;
    int gameType;
    int whoFirst;
    JLabel waiting;
    int number;
    private ExitButton exitButton;
    private UserPanel userPanelL;
    private UserPanel userPanelR;
    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;

    public GameFrame() {
        initFrame();
//        addPlayer();
        addMusic();
        addGamePanel();
        addListener();
        addExitButton();
        this.setVisible(true);
    }

    public GameFrame(int gameType, int whoFirst) {
        gameFrame = this;
        this.gameType = gameType;
        if (whoFirst == OUR) {
            this.whoFirst = BLACK;
        }
        if (whoFirst == OTHER_SIDE) {
            this.whoFirst = WHITE;
        }
        if (whoFirst == RANDOM) {
            this.whoFirst = new Random().nextInt(1, 3);
        }
        initFrame();
        addMusic();
        if (gameType == ONLINE_GAME_TWO_PLAYER) {
            addGamePanel();
            gamePanel.setVisible(false);
            addWaiting();
        } else {
            addGamePanel();
        }
        addExitButton();
        addListener();
        addUserPanel();
        addBackground();
        this.setVisible(true);
        this.setAlwaysOnTop(false);
    }

    public GameFrame(Room room) {
        gameFrame = this;
        this.room = room;
        this.gameType = room.getGameType();
        int whoFirst = room.getWhoFirst();

        if (room.getUserL() == LocalUser.getUserID()) {
            if (whoFirst == OUR) {
                playerR = new Player(LocalUser.getLocalUser(), BLACK);
            } else if (whoFirst == OTHER_SIDE) {
                playerR = new Player(LocalUser.getLocalUser(), WHITE);
            } else {
                playerR = new Player(LocalUser.getLocalUser(), RANDOM);
            }
        } else {
            if (whoFirst == OUR) {
                playerR = new Player(LocalUser.getLocalUser(), WHITE);
            } else if (whoFirst == OTHER_SIDE) {
                playerR = new Player(LocalUser.getLocalUser(), BLACK);
            } else {
                playerR = new Player(LocalUser.getLocalUser(), RANDOM);
            }
        }
        number = new Random().nextInt();
        System.out.println(MyGson.toJson(playerR));
        initFrame();
        addMusic();
        if (gameType == ONLINE_GAME_TWO_PLAYER) {
            addGamePanel();
            gamePanel.setVisible(false);
            addWaiting();
        } else {
            addGamePanel();
        }
        addListener();
        addExitButton();
        addUserPanel();
        addBackground();
        this.setVisible(true);
        this.setAlwaysOnTop(false);
    }

    public static void main(String[] args) {
//        new GameFrame(CONSOLE_GAME_AI,BLACK);
        new GameFrame(CONSOLE_GAME_TWO_PLAYER, WHITE);
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }

    private void addUserPanel() {
        if (!Client.isSocketExist()) {
            LocalUser.online(new User(1, "本地玩家", "男", "", 0, 0, 0));
        }
        User user = null;
        if (gameType == ONLINE_GAME_TWO_PLAYER) {
            user = new User(0, "网络玩家", "女", "0", 0, 0, 0);
        } else if (gameType == CONSOLE_GAME_AI) {
            user = new User(-1, "AI", "AI", "AI", -1, -1, 0);
        } else if (gameType == CONSOLE_GAME_TWO_PLAYER) {
            user = new User(0, "本地玩家2", "女", "0", 0, 0, 0);
        }
        userPanelL = new UserPanel(user, 150, 300, 250, 850, true);
        userPanelR = new UserPanel(LocalUser.getLocalUser(), 1150, 300, 250, 850, true);
        add(new LineBorderPanel(userPanelL.getX()-5,userPanelL.getY()-10,260,540));
        add(new LineBorderPanel(userPanelR.getX()-5,userPanelR.getY()-10,260,540));
        if (whoFirst == WHITE){
            userPanelR.setPieceType("白");
            userPanelL.setPieceType("黑");
        }else if(whoFirst == BLACK){
            userPanelR.setPieceType("黑");
            userPanelL.setPieceType("白");
        }else {
            userPanelR.setPieceType("随机");
            userPanelL.setPieceType("随机");
        }

        if (gameType != ONLINE_GAME_TWO_PLAYER){
            userPanelR.setPrompt("本地游戏");
            userPanelL.setPrompt("本地游戏");
        }else {
            userPanelL.setPrompt("等待比较");
            userPanelR.setPrompt("等待比较");
        }
        add(userPanelL);
        add(userPanelR);
    }
    public void haveAdd(){
        userPanelR.setPrompt("请等待");
        userPanelL.setPrompt("在思考");
    }

    private void addPlayer() {
        if (LocalUser.getLocalUser() != null) {
            playerR = new Player(LocalUser.getLocalUser(), GameConstant.WHITE);
        }
    }


    private void addExitButton() {
        exitButton = new ExitButton(1300, 200, 80, 80);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Client.isSocketExist()) {
                    gamePanel.lose();
                    if (playerL == null){
                        Client.addMessage(new GameOverMessage(room));
                    }else {
                        Client.addMessage(new GiveUpMessage(playerL.getId()));
                    }
                } else {
                    dispose();
                }
            }
        });
        add(exitButton);
    }

    private void addWaiting() {
        waiting = new JLabel("请等待玩家进入...", JLabel.CENTER);
        waiting.setFont(font.deriveFont(60f));
        waiting.setBounds(450, 500, 680, 75);
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
                    if (GameLobbyFrame.getGameLobbyFrame() != null){
                        GameLobbyFrame.getGameLobbyFrame().UpdateUserPanel();
                        GameLobbyFrame.getGameLobbyFrame().setVisible(true);
                    }
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
        gamePanel = new GamePanel(gameType, whoFirst, this);
        gamePanel.setLocation(450, 180);
        this.add(gamePanel);
    }

    private void initFrame() {
        this.setTitle("五子棋");
        this.setSize(1564, 1000);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);
        this.setResizable(false);
        this.setLocation(getX(), getY() - 50);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        PositionDraggingListener.addPositionDraggingListener(this);
    }

    private void addBackground() {
        URL loginBackground = Objects.requireNonNull(
                LoginFrame.class.getClassLoader().getResource("images/game_background.png"));
        ImageIcon loginBackgroundIcon = new ImageIcon(loginBackground);
        JLabel label = new JLabel(loginBackgroundIcon);
        label.setBounds(0, 0, getWidth(), getHeight());
        add(label);
    }

    public void playerEnterRoom(Message message) {
        User user = MyGson.fromJson(message.getMessage(), User.class);
        room.setUserR(user.getId());
        if (room.getUserL() == room.getUserR()){
            room.setUserR(LocalUser.getUserID());
        }
        System.out.println(MyGson.toJson(room));
        int pieceType;
        if (playerR.getPieceType() == WHITE) {
            pieceType = BLACK;
        } else if (playerR.getPieceType() == BLACK) {
            pieceType = WHITE;
        } else {
            pieceType = RANDOM;
        }
        playerL = new Player(user, pieceType);
        System.out.println(MyGson.toJson(playerL));
        gamePanel.setVisible(true);
        waiting.setVisible(false);
        userPanelL.userEnter(user);
        if (pieceType == RANDOM) {
            System.out.println("发送数字" + number);
            Client.addMessage(new CompareNumMessage(number, playerL.getId()));
        }
    }

    public void compareNum(Message message) {
        int otherSideNum = Integer.parseInt(message.getMessage());
        System.out.println("收到数字" + otherSideNum + "本地数字" + number);
        if (otherSideNum < number) {
            playerR.setPieceType(GameConstant.BLACK);
            System.out.println("黑");
            gamePanel.setWhoSign(GameConstant.BLACK);
            userPanelR.setPieceType("黑");
            userPanelL.setPieceType("白");
            userPanelR.setPrompt("请下棋");
            userPanelL.setPrompt("等待中");
        }
        if (otherSideNum > number) {
            playerR.setPieceType(GameConstant.WHITE);
            System.out.println("白");
            gamePanel.setWhoSign(GameConstant.WHITE);
            userPanelL.setPieceType("黑");
            userPanelR.setPieceType("白");
            userPanelR.setPrompt("请等待");
            userPanelL.setPrompt("思考中");
        }
        if (otherSideNum == number) {
            number = new Random().nextInt();
            Client.addMessage(new CompareNumMessage(number, playerL.getId()));
        }
    }

    public void addUserLPiece(Message message) {
        Chessboard.Piece piece = MyGson.fromJson(message.getMessage(), Chessboard.Piece.class);
        gamePanel.addPiece(piece);
        userPanelR.setPrompt("请下棋");
        userPanelL.setPrompt("等待中");
    }

    public void lose() {
        gamePanel.lose();
        LocalUser.lose();
        GameLobbyFrame.getGameLobbyFrame().setVisible(true);
    }

    public void victory() {
        gamePanel.victory();
        LocalUser.win();
        GameLobbyFrame.getGameLobbyFrame().setVisible(true);
    }

}
