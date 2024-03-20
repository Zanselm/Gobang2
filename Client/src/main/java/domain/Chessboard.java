package domain;

import constant.GameConstant;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 五子棋棋盘模板
 */
public class Chessboard implements GameConstant {
    public static final int CHESSBOARD_SIZE = 15;
    private int pieceNumber;
    private final int[][] pieceLocation; //一个15*15二维数组充当棋盘
    private final ArrayList<Piece> pieceArrayList;

    public static class Piece {
        public int x;
        public int y;
        public int chessPieceType;

        public Piece() {

        }

        private Piece(int x, int y, int chessPieceType) {
            this.x = x;
            this.y = y;
            this.chessPieceType = chessPieceType;
        }

        @Override
        public String toString() {
            return "Piece{" +
                    "x=" + x +
                    ", y=" + y +
                    ", chessPieceType=" + chessPieceType +
                    '}';
        }
    }

    public Chessboard() {
        pieceNumber = 0;
        this.pieceLocation = new int[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
        pieceArrayList = new ArrayList<>();
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public int[][] getPieceLocation() {
        return pieceLocation;
    }

    public boolean addPiece(int j, int i, int chessPieceType) {
        if (this.pieceLocation[i][j] == NO_CHESS_PIECE) {
            this.pieceLocation[i][j] = chessPieceType;
            this.pieceArrayList.add(new Piece(j, i, chessPieceType));
            this.pieceNumber++;
            return true;
        } else {
            return false;
        }
    }

    public boolean removePiece(int x, int y) {
        if (isHavePiece(x, y)) {
            pieceLocation[y][x] = NO_CHESS_PIECE;
            Iterator<Piece> iterator = pieceArrayList.iterator();
            Piece piece;
            while (iterator.hasNext()){
                piece = iterator.next();
                if (piece.x==x && piece.y == y){
                    pieceArrayList.remove(piece);
                }
            }
            pieceNumber--;
            return true;
        }
        return false;
    }

    public boolean removeLastPiece(){
        if (!pieceArrayList.isEmpty()) {
            Piece piece = pieceArrayList.remove(pieceNumber - 1);
            int x = piece.x;
            int y = piece.y;
            pieceLocation[y][x] = NO_CHESS_PIECE;
            pieceNumber--;
            return true;
        }
        return false;
    }

    public ArrayList<Piece> getPieceArrayList() {
        return pieceArrayList;
    }

    public boolean isHavePiece(int x, int y) {
        return !(pieceLocation[y][x] == NO_CHESS_PIECE);
    }
    public void removeAll() {
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                this.pieceLocation[i][j] = NO_CHESS_PIECE;
            }
        }
        pieceArrayList.clear();
        pieceNumber = 0;
    }

    public void printChessBoard() {
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                System.out.print(this.pieceLocation[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }

    public void printPieceArrayList() {
        for (Piece piece : pieceArrayList) {
            if (piece.chessPieceType == WHITE) {
                System.out.print("(" + piece.x + "," + piece.y + "):" + "白" + " ");
            }
            if(piece.chessPieceType == BLACK) {
                System.out.print("(" + piece.x + "," + piece.y + "):" + "黑" + " ");
            }
        }
        System.out.println();
    }

}
