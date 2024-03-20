package domain;

import constant.GameConstant;
import entity.User;

public class Player extends User implements GameConstant {
    private int pieceType;

    public Player() {
    }

    public Player(User user,int pieceType) {
        super(user.getId(),user.getName(),user.getSex(),"",user.getWin(), user.getLose(), user.getAvatar());
        if (pieceType == WHITE || pieceType == BLACK) {
            this.pieceType = pieceType;
        } else {
            throw new RuntimeException("错误的棋子类型，" +
                    "应该为 1(BLACK）或 2(WHITE) 见 ChessPieceType");
        }
    }
    public int getPieceType() {
        return pieceType;
    }

    public void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }
}
