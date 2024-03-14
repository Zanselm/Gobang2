package entity.room;

/**
 * @author Anselm
 * @date 2024/2/4 12 10
 * description
 */

public class Room {
    private int ID;
    private String name;
    private String introductory;
    private int userL;
    private int userR;
    private int gameType;
    private int whoFirst;
    private boolean observable;

    public Room() {
    }

    public Room(int ID, String name, String introductory, int userL, int userR, int gameType, int whoFirst, boolean observable) {
        this.ID = ID;
        this.name = name;
        this.introductory = introductory;
        this.userL = userL;
        this.userR = userR;
        this.gameType = gameType;
        this.whoFirst = whoFirst;
        this.observable = observable;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroductory() {
        return introductory;
    }

    public void setIntroductory(String introductory) {
        this.introductory = introductory;
    }

    public int getUserL() {
        return userL;
    }

    public void setUserL(int userL) {
        this.userL = userL;
    }

    public int getUserR() {
        return userR;
    }

    public void setUserR(int userR) {
        this.userR = userR;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public int getWhoFirst() {
        return whoFirst;
    }

    public void setWhoFirst(int whoFirst) {
        this.whoFirst = whoFirst;
    }

    public boolean isObservable() {
        return observable;
    }

    public void setObservable(boolean observable) {
        this.observable = observable;
    }
}
