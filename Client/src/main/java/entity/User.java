package entity;

/**
 * @author Anselm
 * @date 2024/2/4 12 09
 * description
 */

public class User {
    private int id;
    private String name;
    private String sex;
    private String password;
    private int win;
    private int lose;
    private int avatar;

    public User() {
    }

    public User(int id, String name, String sex, String password, int win, int lose, int avatar) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.password = password;
        this.win = win;
        this.lose = lose;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
