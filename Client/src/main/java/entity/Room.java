package entity;

/**
 * @author Anselm
 * @date 2024/2/4 12 10
 * description
 */

public class Room {
    private int ID;
    private String name;
    private String introductory;
    private User userL;
    private User userR;
    private int gameType;
    private int whoFirst;

    public Room() {
    }

    public Room(int ID, String name, String introductory, User userL, User userR, int gameType, int whoFirst) {
        this.ID = ID;
        this.name = name;
        this.introductory = introductory;
        this.userL = userL;
        this.userR = userR;
        this.gameType = gameType;
        this.whoFirst = whoFirst;
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

    public User getUserL() {
        return userL;
    }

    public void setUserL(User userL) {
        this.userL = userL;
    }

    public User getUserR() {
        return userR;
    }

    public void setUserR(User userR) {
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

    @Override
    public String toString() {
        return "Room{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", introductory='" + introductory + '\'' +
                ", userL=" + userL +
                ", userR=" + userR +
                ", gameType=" + gameType +
                ", whoFirst=" + whoFirst +
                '}';
    }
}
