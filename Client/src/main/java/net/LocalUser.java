package net;

import entity.User;

/**
 * @author Anselm
 * @date 2024/3/13 17 19
 * description
 */

public class LocalUser extends User {
    public static User localUser;

    private LocalUser() {
    }

    private LocalUser(int id, String name, String sex, String password, int win, int lose, int avatar) {
        super(id, name, sex, password, win, lose, avatar);
    }

    public static void online(User user) {
        if (localUser == null) {
            localUser = user;
        } else {
            System.out.println("重复登录");
        }
    }

    public static void offline() {
        localUser = null;
    }

    public static User getLocalUser() {
        return localUser;
    }

    public static int getUserID() {
        if (localUser == null) {
            return -1;
        }
        return localUser.getId();
    }
    public static void win(){
        localUser.setWin(localUser.getWin()+1);
    }
    public static void lose(){
        localUser.setLose(localUser.getLose()+1);
    }
}
