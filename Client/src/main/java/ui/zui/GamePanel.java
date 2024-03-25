package ui.zui;

import constant.GameConstant;
import domain.AI;
import domain.Chessboard;
import net.Client;
import net.message.AddPieceMessage;
import net.message.VictoryMessage;
import org.jetbrains.annotations.NotNull;
import ui.GameFrame;
import utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Objects;


/**
 * 游戏面板
 */
public class GamePanel extends JPanel implements GameConstant {

    public static final int SPACING = 40;   //间距
    public GameFrame gameFrame;
    Image imageBlack = new ImageIcon(Objects.requireNonNull(GamePanel.class.getResource("/images/blake.png"))).getImage();
    Image imageWhite = new ImageIcon(Objects.requireNonNull(GamePanel.class.getResource("/images/white.png"))).getImage();
    private int abscissa;   //横坐标
    private int ordinate;   //纵坐标
    private int whoSign = BLACK;    //落子颜色
    private boolean isCanDrop = true;   //是否可以落子的标志
    private Chessboard chessboard;  //棋盘
    private int gameType = CONSOLE_GAME_AI;     //游戏类型
    private AI ai;  //AI
    private MusicPlayer musicPlayer;//音乐播放器
//    Image imageWhite = new ImageIcon(this.getClass().getClassLoader().getResource("white.png")).getImage();

    public GamePanel() {
        Init();
        addListener();
    }

    public GamePanel(int gameType, int whoSign, GameFrame gameFrame) {
        this.gameType = gameType;
        this.whoSign = whoSign;
        this.gameFrame = gameFrame;
        Init();
        addListener();
    }

    private void Init() {
        setChessboard(new Chessboard());
//        setBorder(BorderFactory.createRaisedBevelBorder());
        musicPlayer = new MusicPlayer();

//        测试
        musicPlayer.load(GamePanel.class.getResourceAsStream("/audio/drop.wav"));

        setBackground(new Color(0, 0, 0, 0));

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBounds(0, 0, SPACING * 16, SPACING * 16);

        if (gameType == CONSOLE_GAME_AI) {
            if (whoSign == WHITE) {
                ai = new AI(BLACK, chessboard);
                if (ai.getPieceType() == BLACK) {
                    chessboard.addPiece(7, 7, BLACK);
                }
            }
            if (whoSign == BLACK) {
                ai = new AI(WHITE, chessboard);
            }

        }
        if (gameType == CONSOLE_GAME_TWO_PLAYER) {
            if (whoSign == WHITE) {
                whoSign = BLACK;
            }
        }
        if (gameType == ONLINE_GAME_TWO_PLAYER) {
            if (whoSign == WHITE) {
                isCanDrop = false;
            }
        }
    }

    private void addListener() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                abscissa = (e.getX() - GamePanel.SPACING / 2) / GamePanel.SPACING;
                ordinate = (e.getY() - GamePanel.SPACING / 2) / GamePanel.SPACING;
                if (abscissa > 14 || ordinate > 14 || e.getX() < GamePanel.SPACING / 2 || e.getY() < GamePanel.SPACING / 2) {
                    abscissa = -1;
                    ordinate = -1;
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (gameType == CONSOLE_GAME_TWO_PLAYER) {
                    twoPlayerLocal();
                }
                if (gameType == CONSOLE_GAME_AI) {
                    aiLocal();
                }
                if (gameType == ONLINE_GAME_TWO_PLAYER) {
                    twoPlayerOnline();
                }
            }
        });
    }

    private void twoPlayerOnline() {
        if (abscissa >= 0 && ordinate >= 0 && !isHavePiece() && isCanDrop) {
            chessboard.addPiece(abscissa, ordinate, whoSign);
            musicPlayer.play();
            isCanDrop = false;
            Chessboard.Piece piece = new Chessboard.Piece(abscissa, ordinate, whoSign);
            Client.addMessage(new AddPieceMessage(piece, gameFrame.playerR.getId()));
            if (isVictory()) {
                JOptionPane.showConfirmDialog(null, "你胜利了！", "胜利", JOptionPane.DEFAULT_OPTION);
                gameFrame.dispose();
                Client.addMessage(new VictoryMessage(gameFrame.playerR.getId()));
            }
        }
    }

    private void aiLocal() {
        if (abscissa >= 0 && ordinate >= 0 && !isHavePiece()) {
            chessboard.addPiece(abscissa, ordinate, whoSign);

//                musicPlayer.getClip().setFramePosition(0);
//                musicPlayer.getClip().start();
            musicPlayer.play();

            if (isVictory()) {
                JOptionPane.showConfirmDialog(null, "你胜利了！", "胜利", JOptionPane.DEFAULT_OPTION);
                gameFrame.dispose();
            } else {
                ai.scan();
                if (isVictory()) {
                    JOptionPane.showConfirmDialog(null, "AI胜利！", "失败", JOptionPane.DEFAULT_OPTION);
                    gameFrame.dispose();
                }
            }
        }
    }

    private void twoPlayerLocal() {
        if (abscissa >= 0 && ordinate >= 0 && !isHavePiece()) {
            chessboard.addPiece(abscissa, ordinate, whoSign);
            musicPlayer.play();

//          测试
//            musicPlayer.getClip().setFramePosition(0);
//            musicPlayer.getClip().start();

            checkVictory();
            if (whoSign == BLACK) {
                whoSign = WHITE;
            } else {
                whoSign = BLACK;
            }
        }
    }

    private void checkVictory() {
        if (isVictory()) {
            if (whoSign == WHITE) {
                JOptionPane.showConfirmDialog(null, "白方胜利！", "胜利", JOptionPane.DEFAULT_OPTION);
            } else {
                JOptionPane.showConfirmDialog(null, "黑方胜利！", "胜利", JOptionPane.DEFAULT_OPTION);
            }
            //消息对话框
//            gameFrame.dispose();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        填色
//        g.setColor(new Color(244, 178, 102, 173));
        g.setColor(new Color(244, 178, 102));
        g.fillRect(0, 0, 640, 640);

//        画线
        g.setColor(Color.BLACK);
        for (int i = 0; i < 15; i++) {
            g.drawLine(SPACING, SPACING + i * SPACING, SPACING * Chessboard.CHESSBOARD_SIZE, SPACING + i * SPACING);
            g.drawLine(SPACING + i * SPACING, SPACING, SPACING + i * SPACING, SPACING * Chessboard.CHESSBOARD_SIZE);
        }
        int lineLength = SPACING * (Chessboard.CHESSBOARD_SIZE - 1) + 10;
        g.fillRect(SPACING - 5, SPACING - 5, 2, lineLength);
        g.fillRect(SPACING - 5, SPACING - 5, lineLength, 2);
        g.fillRect(SPACING * Chessboard.CHESSBOARD_SIZE + 4, SPACING - 5, 2, lineLength);
        g.fillRect(SPACING - 5, SPACING * Chessboard.CHESSBOARD_SIZE + 4, lineLength, 2);

//        画点点
        final int DIAMETER = SPACING / 5;
        g.fillOval(SPACING * 4 - DIAMETER / 2, SPACING * 4 - DIAMETER / 2, DIAMETER, DIAMETER);
        g.fillOval(SPACING * 8 - DIAMETER / 2, SPACING * 8 - DIAMETER / 2, DIAMETER, DIAMETER);
        g.fillOval(SPACING * 12 - DIAMETER / 2, SPACING * 4 - DIAMETER / 2, DIAMETER, DIAMETER);
        g.fillOval(SPACING * 4 - DIAMETER / 2, SPACING * 12 - DIAMETER / 2, DIAMETER, DIAMETER);
        g.fillOval(SPACING * 12 - DIAMETER / 2, SPACING * 12 - DIAMETER / 2, DIAMETER, DIAMETER);

//        画棋子
//        Image imageBlack = new ImageIcon(Objects.requireNonNull(GamePanel.class.getResource("/frame/resources/images/blake.png"))).getImage();
//        Image imageWhite = new ImageIcon(Objects.requireNonNull(GamePanel.class.getResource("/frame/resources/images/white.png"))).getImage();

        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                if (chessboard.getPieceLocation()[i][j] != NO_CHESS_PIECE) {
                    if (chessboard.getPieceLocation()[i][j] == WHITE) {
                        g.drawImage(imageWhite, j * SPACING + SPACING / 2, i * SPACING + SPACING / 2, SPACING, SPACING, null);
                    } else {
                        g.drawImage(imageBlack, j * SPACING + SPACING / 2, i * SPACING + SPACING / 2, SPACING, SPACING, null);
                    }
                }
            }

        }

//        画指示器
        if (abscissa != -1 || ordinate != -1) {
            new Pointer(abscissa * SPACING + SPACING, ordinate * SPACING + SPACING).paint(g);
        }

        repaint();

//        强制暂停线程来节省资源
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPiece(Chessboard.Piece piece) {
        chessboard.addPiece(piece.x, piece.y, piece.chessPieceType);
        musicPlayer.play();
        isCanDrop = true;
    }

    public void lose() {
        JOptionPane.showConfirmDialog(null, "你输了！", "失败", JOptionPane.DEFAULT_OPTION);
        gameFrame.dispose();
    }

    public void victory() {
        JOptionPane.showConfirmDialog(null, "对方认输", "胜利", JOptionPane.DEFAULT_OPTION);
        gameFrame.dispose();
    }


    public int getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(int abscissa) {
        this.abscissa = abscissa;
    }

    public int getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(int ordinate) {
        this.ordinate = ordinate;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    private boolean isHavePiece() {
        return chessboard.isHavePiece(abscissa, ordinate);
    }

    public int getWhoSign() {
        return whoSign;
    }

    public void setWhoSign(int whoSign) {
        this.whoSign = whoSign;
        if (whoSign == WHITE) {
            isCanDrop = false;
        }
    }

    public boolean isCanDrop() {
        return isCanDrop;
    }

    public void setCanDrop(boolean canDrop) {
        isCanDrop = canDrop;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }


    private boolean isVictory() {
        ArrayList<Chessboard.Piece> pieceArrayList = chessboard.getPieceArrayList();
        Chessboard.Piece piece;
        piece = pieceArrayList.get(pieceArrayList.size() - 1);

        int i = piece.y;
        int j = piece.x;
        int count;
//        上下
        count = 0;
        for (int k = 0; k < 5; k++) {
            if (getChessboard().getPieceLocation()[i - k][j] == piece.chessPieceType) {
                count++;
                if (i - k == 0) break;
            } else {
                break;
            }
        }
        for (int k = 0; k < 5; k++) {
            if (getChessboard().getPieceLocation()[i + k][j] == piece.chessPieceType) {
                count++;
                if (i + k == 14) break;
            } else {
                break;
            }
        }
        count -= 1;
//        左右
        if (count < 5) {
            count = 0;
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i][j - k] == piece.chessPieceType) {
                    count++;
                    if (j - k == 0) break;
                } else {
                    break;
                }
            }
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i][j + k] == piece.chessPieceType) {
                    count++;
                    if (j + k == 14) break;
                } else {
                    break;
                }
            }
            count -= 1;
        }
//        斜 左上到右下
        if (count < 5) {
            count = 0;
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i - k][j - k] == piece.chessPieceType) {
                    count++;
                    if (j - k == 0 || i - k == 0) break;
                } else {
                    break;
                }
            }
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i + k][j + k] == piece.chessPieceType) {
                    count++;
                    if (j + k == 14 || i + k == 14) break;
                } else {
                    break;
                }
            }
            count -= 1;
        }
//        斜 右上到左下
        if (count < 5) {
            count = 0;
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i - k][j + k] == piece.chessPieceType) {
                    count++;
                    if (j + k == 14 || i - k == 0) break;
                } else {
                    break;
                }
            }
            for (int k = 0; k < 5; k++) {
                if (getChessboard().getPieceLocation()[i + k][j - k] == piece.chessPieceType) {
                    count++;
                    if (j - k == 0 || i + k == 14) break;
                } else {
                    break;
                }
            }
            count -= 1;
        }
        return count >= 5;
    }

    private static class Pointer {
        final int SPACING = GamePanel.SPACING;
        int x;
        int y;

        public Pointer(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void paint(@NotNull Graphics g) {
            g.setColor(Color.red);
            g.drawLine(x - SPACING / 2, y - SPACING / 2, x - SPACING / 4, y - SPACING / 2);
            g.drawLine(x - SPACING / 2, y - SPACING / 2, x - SPACING / 2, y - SPACING / 4);

            g.drawLine(x + SPACING / 2, y - SPACING / 2, x + SPACING / 4, y - SPACING / 2);
            g.drawLine(x + SPACING / 2, y - SPACING / 2, x + SPACING / 2, y - SPACING / 4);

            g.drawLine(x - SPACING / 2, y + SPACING / 2, x - SPACING / 4, y + SPACING / 2);
            g.drawLine(x - SPACING / 2, y + SPACING / 2, x - SPACING / 2, y + SPACING / 4);

            g.drawLine(x + SPACING / 2, y + SPACING / 2, x + SPACING / 4, y + SPACING / 2);
            g.drawLine(x + SPACING / 2, y + SPACING / 2, x + SPACING / 2, y + SPACING / 4);
        }
    }


}
