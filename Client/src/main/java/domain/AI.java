package domain;

import constant.GameConstant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 专精于五子棋的AI
 */
public class AI extends Player implements GameConstant {

    private Chessboard chessboard;
    private HashMap<String,Integer> weights;
    private int[][] weightBoardWhite;
    private int[][] weightBoardBlack;

    public AI( int pieceType, Chessboard chessboard) {
        this.setName("AI");
        if (pieceType == WHITE || pieceType == BLACK) {
            this.setPieceType(pieceType);
        } else {
            throw new RuntimeException("错误的棋子类型，" +
                    "应该为 1(BLACK）或 2(WHITE) 见 ChessPieceType");
        }
        this.chessboard = chessboard;
        setPieceType(pieceType);
        init();

    }

    private void init() {
        this.setName("AI");
        weights = new HashMap<>();
        weightBoardWhite = new int[Chessboard.CHESSBOARD_SIZE][Chessboard.CHESSBOARD_SIZE];
        weightBoardBlack = new int[Chessboard.CHESSBOARD_SIZE][Chessboard.CHESSBOARD_SIZE];
//            一
        weights.put("2", 0);
        weights.put("12", 0);    //死一
        weights.put("21", 0);
        weights.put("121", 0);
        weights.put("20", 1);    //眠一
        weights.put("02", 1);
        weights.put("021", 5);
        weights.put("120", 5);
        weights.put("020", 2);  //活一
//            二
        weights.put("22", 5);
        weights.put("122", 5);    //死二
        weights.put("221", 5);
        weights.put("1221", 5);
        weights.put("220", 5);    //眠二
        weights.put("022", 5);
        weights.put("0221", 50);
        weights.put("1220", 50);
        weights.put("0220", 10);  //活二
//            三
        weights.put("222", 5);
        weights.put("1222", 0);    //死三
        weights.put("2221", 0);
        weights.put("12221", 0);
        weights.put("2220", 5);    //眠三
        weights.put("0222", 5);
        weights.put("02221", 5);
        weights.put("12220", 5);
        weights.put("02220", 1500);  //活三
//            四
        weights.put("12222", 0);    //死四
        weights.put("22221", 0);
        weights.put("122221", 0);
        weights.put("22220", 1000);    //眠四
        weights.put("02222", 1000);
        weights.put("022221", 1000);
        weights.put("122220", 1000);
        weights.put("022220", 2000);  //活四
//            五
        weights.put("222222", 10000);
        weights.put("122222", 10000);    //死五
        weights.put("222221", 10000);
        weights.put("1222221", 10000);
        weights.put("222220", 10000);    //眠五
        weights.put("022222", 10000);
        weights.put("0222221", 10000);
        weights.put("1222220", 10000);
        weights.put("0222220", 10000);  //活五

//            一

        weights.put("1", 0);
//            weights.put("12",0);    //死一
//            weights.put("21",0);
        weights.put("212", 0);
        weights.put("10", 5);    //眠一
        weights.put("01", 5);
        weights.put("012", 5);
        weights.put("210", 5);
        weights.put("010", 3);  //活一
//            二
        weights.put("11", 0);
        weights.put("211", 5);    //死二
        weights.put("112", 5);
        weights.put("2112", 5);
        weights.put("110", 5);    //眠二
        weights.put("011", 5);
        weights.put("0112", 5);
        weights.put("2110", 5);
        weights.put("0110", 100);  //活二
//            三
        weights.put("111", 0);
        weights.put("2111", 0);    //死三
        weights.put("1112", 0);
        weights.put("21112", 0);
        weights.put("1110", 5);    //眠三
        weights.put("0111", 5);
        weights.put("01112", 5);
        weights.put("21110", 5);
        weights.put("01110", 1500);  //活三
//            四
        weights.put("21111", 0);    //死四
        weights.put("11112", 0);
        weights.put("211112", 0);
        weights.put("11110", 1500);    //眠四
        weights.put("01111", 1000);
        weights.put("011112", 1000);
        weights.put("211110", 1000);
        weights.put("011110", 5000);  //活四
//            五
        weights.put("11111", 10000);

        weights.put("0111111", 10000);
        weights.put("211111", 10000);    //死五
        weights.put("111112", 10000);
        weights.put("2111112", 10000);
        weights.put("111110", 10000);    //眠五
        weights.put("011111", 10000);
        weights.put("0111112", 10000);
        weights.put("2111110", 10000);
        weights.put("0111110", 10000);  //活五
    }

    public void scan(){
//        清理权值版
        clearWightBoard(weightBoardBlack);
        clearWightBoard(weightBoardWhite);
//        遍历每一个落点
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
//                判断落点是否有子，有子的话跳过。
                if (chessboard.isHavePiece(j,i))continue;
                scanPiece(i,j, BLACK);
                scanPiece(i,j, WHITE);

            }

        }
        dropPiece();
//        printWightBoard(weightBoardBlack);
//        printWightBoard(weightBoardWhite);
//        System.out.println(getMaxPiece(weightBoardBlack));
//        System.out.println();
//        printWightBoard(weightBoardBlack);
//        printWightBoard(weightBoardWhite);
//        System.out.println(getSumWights(weightBoardBlack));
    }

    private void scanPiece(int i, int j, int pieceType) {
        StringBuilder count;
        if (pieceType == WHITE){
            count = new StringBuilder("2");
        } else{
            count = new StringBuilder("1");
        }
//                左右
        for (int k = 1; k < 5; k++) {
            if (j != 0){
                if (chessboard.getPieceLocation()[i][j -k]==WHITE){
                    if (pieceType == WHITE) {
                        count.insert(0, "2");
                        if (j-k-1<=0) break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"2");
                        break;
                    }
                }else if (chessboard.getPieceLocation()[i][j -k]==BLACK){
                    if (pieceType == WHITE) {
                        count.insert(0,"1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0, "1");
                        if (j-k-1<=0) break;
                    }

                }else if (chessboard.getPieceLocation()[i][j -k]==NO_CHESS_PIECE){
                    count.insert(0,"0");
                    break;
                }
            }

        }
        for (int k = 1; k < 5; k++) {
            if (j != 14){
                if (chessboard.getPieceLocation()[i][j +k]==WHITE){
                    if (pieceType == WHITE) {
                        count.append("2");
                        if (j+k+1>=14) break;
                    }
                    if (pieceType == BLACK){
                        count.append("2");
                        break;
                    }
                }else if (chessboard.getPieceLocation()[i][j +k]==BLACK){
                    if (pieceType == WHITE) {
                        count.append("1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.append("1");
                        if (j+k+1>=14) break;
                    }
                }else {
                    count.append("0");
                    break;
                }
            }
        }
//        System.out.println(count.toString());
        assignValue(i, j, count, pieceType);

//                上下
        for (int k = 1; k < 5; k++) {
            if (i!=0){
                if (chessboard.getPieceLocation()[i-k][j]==WHITE){
                    if (pieceType == WHITE) {
                        count.insert(0,"2");
                        if (i-k-1<=0) break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j]==BLACK){
                    if (pieceType == WHITE) {
                        count.insert(0,"1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"1");
                        if (i-k-1<=0) break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j]==NO_CHESS_PIECE){
                    count.insert(0,"0");
                    break;
                }
            }
        }
        for (int k = 1; k < 5; k++) {
            if (i!=14){
                if (chessboard.getPieceLocation()[i+k][j]==WHITE){
                    if (pieceType == WHITE) {
                        count.append("2");
                        if (i+k+1>=14) break;
                    }
                    if (pieceType == BLACK){
                        count.append("2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j]==BLACK){
                    if (pieceType == WHITE) {
                        count.append("1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.append("1");
                        if (i+k+1>=14) break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j]==NO_CHESS_PIECE){
                    count.append("0");
                    break;
                }
            }
        }
        assignValue(i, j, count, pieceType);

//                左上右下
        for (int k = 1; k < 5; k++) {
            if (i!=0&&j!=0){
                if (chessboard.getPieceLocation()[i-k][j-k]==WHITE){
                    if (pieceType == WHITE) {
                        count.insert(0,"2");
                        if (i-k-1<=0||j-k-1<=0) break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j-k]==BLACK){
                    if (pieceType == WHITE) {
                        count.insert(0,"1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"1");
                        if (i-k-1<=0||j-k-1<=0) break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j-k]==NO_CHESS_PIECE){
                    count.insert(0,"0");
                    break;
                }
            }
        }
        for (int k = 1; k < 5; k++) {
            if (i!=14&&j!=14){
                if (chessboard.getPieceLocation()[i+k][j+k]==WHITE){
                    if (pieceType == WHITE) {
                        count.append("2");
                        if (i+k+1>=14||j+k+1>=14) break;
                    }
                    if (pieceType == BLACK){
                        count.append("2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j+k]==BLACK){
                    if (pieceType == WHITE) {
                        count.append("1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.append("1");
                        if (i+k+1>=14||j+k+1>=14) break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j+k]==NO_CHESS_PIECE){
                    count.append("0");
                    break;
                }
            }
        }
        assignValue(i, j, count, pieceType);

//                左下右上
        for (int k = 1; k < 5; k++) {
            if (i!=0&&j!=14){
                if (chessboard.getPieceLocation()[i-k][j+k]==WHITE){
                    if (pieceType == WHITE) {
                        count.insert(0,"2");
                        if (i-k-1<=0||j+k+1>=14) break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j+k]==BLACK){
                    if (pieceType == WHITE) {
                        count.insert(0,"1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.insert(0,"1");
                        if (i-k-1<=0||j+k+1>=14) break;
                    }
                }
                if (chessboard.getPieceLocation()[i-k][j+k]==NO_CHESS_PIECE){
                    count.insert(0,"0");
                    break;
                }
            }
        }
        for (int k = 1; k < 5; k++) {
            if (i!=14&&j!=0){
                if (chessboard.getPieceLocation()[i+k][j-k]==WHITE){
                    if (pieceType == WHITE) {
                        count.append("2");
                        if (i+k+1>=14||j-k-1<=0) break;
                    }
                    if (pieceType == BLACK){
                        count.append("2");
                        break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j-k]==BLACK){
                    if (pieceType == WHITE) {
                        count.append("1");
                        break;
                    }
                    if (pieceType == BLACK){
                        count.append("1");
                        if (i+k+1>=14||j-k-1<=0) break;
                    }
                }
                if (chessboard.getPieceLocation()[i+k][j-k]==NO_CHESS_PIECE){
                    count.append("0");
                    break;
                }
            }
        }
        assignValue(i, j, count, pieceType);
    }

    private void assignValue(int i, int j, @NotNull StringBuilder count, int pieceType) {
        //        查表赋值，如果大于7就不查表直接赋值
            if (count.length()>=7){
                if (pieceType == WHITE){
                    weightBoardWhite[i][j] = 10000;
                }
                if (pieceType == BLACK){
                    weightBoardBlack[i][j] = 10000;
                }
            }else {
                try {
                    if (pieceType == WHITE){
                        weightBoardWhite[i][j] += weights.get(count.toString());
                    }
                    if (pieceType == BLACK){
                        weightBoardBlack[i][j] += weights.get(count.toString());
                    }
                } catch (Exception e) {
                    System.out.println("exceptionPiece:("+i+","+j+":"+ count +","+pieceType+")");
                }
            }
//        清空全部，初始化
        if (pieceType == WHITE){
            count.setLength(0);
            count.append("2");
        }
        if (pieceType == BLACK){
            count.setLength(0);
            count.append("1");
        }

    }

    private void clearWightBoard(int[][] weightBoard){
        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                weightBoard[i][j] = 0;
            }
        }
    }

    private int getSumWights(int[][] weightBoard){
        int sum = 0;
        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                sum += weightBoard[i][j];
            }
        }
        return sum;
    }

    private Piece getMaxPiece(int[][] weightBoard){
        int max = 0;
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                if (weightBoard[i][j]>max)max = weightBoard[i][j];
            }
        }
        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                if (weightBoard[i][j]==max){
                    pieces.add(new Piece(j,i));
                }
            }
        }
        return pieces.get(new Random().nextInt(pieces.size()));
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void printWightBoard(int[][] weightBoard){
        for (int i = 0; i < Chessboard.CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < Chessboard.CHESSBOARD_SIZE; j++) {
                System.out.print(weightBoard[i][j]+"  ");
            }
            System.out.println();
        }
        System.out.println("score:"+getSumWights(weightBoard));
        System.out.println("-------------------------------");

    }

    public void randomDropPiece(){
        Random random = new Random();
        while (true) {
            int i = random.nextInt(Chessboard.CHESSBOARD_SIZE);
            int j = random.nextInt(Chessboard.CHESSBOARD_SIZE);
            if (chessboard.addPiece(j,i,getPieceType())) break;
        }
    }

    public void dropPiece(){
        try {
            if (getSumWights(weightBoardBlack)>getSumWights(weightBoardWhite)){
                Piece piece = getMaxPiece(weightBoardBlack);
                chessboard.addPiece(piece.getX(),piece.getY(),getPieceType());
            } else {
                Piece piece = getMaxPiece(weightBoardWhite);
                chessboard.addPiece(piece.getX(),piece.getY(),getPieceType());
            }

//            printWightBoard(weightBoardBlack);
//            System.out.println(piece);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private class Piece {
        private int x;
        private int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+") ";
    }
}
}
