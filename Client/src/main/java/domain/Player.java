package domain;

import constant.GameConstant;
import entity.User;

public class Player extends User implements GameConstant {
    private int pieceType;

    public Player() {
    }

    public Player(User user, int pieceType) {
        super(user.getId(), user.getName(), user.getSex(), "", user.getWin(), user.getLose(), user.getAvatar());
        this.pieceType = pieceType;
    }

    public int getPieceType() {
        return pieceType;
    }

    public void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }
}
