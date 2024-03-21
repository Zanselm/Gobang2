package entity.user;

import org.junit.Test;

import java.sql.*;

/**
 * @author Anselm
 * @date 2024/2/4 12 21
 * description
 */

public class UserServer {
    private static final String url = "jdbc:mysql://150.158.45.98:3306/userdb";
    private static final String user = "userdb";
    private static final String password = "root";


    private Connection getConnection() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    @Test
    public void test() throws Exception {
        System.out.println(register(new User(0, "wb", "男", "32165644", 0, 0, 0)));
    }
    public boolean register(User user) throws SQLException {
        Connection conn = getConnection();

        try (conn) {
            UserDAO userDAO = new UserDAO(conn, user);
            if (userDAO.query() == null) {
                userDAO.insert();
                return true;
            } else {
                return false;
            }
        }
    }

    @Test
    public void test2() throws Exception {
        System.out.println(login(new User(0, "wb", "男", "32165644", 0, 0, 0)).getName());
        System.out.println(login(new User(0, "wkx", "男", "32165644", 0, 0, 0)).getName());
    }
    public User login(User user) throws SQLException {
        Connection conn = getConnection();

        try (conn) {
            UserDAO userDAO = new UserDAO(conn, user);
            User serverUser;
            if ((serverUser = userDAO.query()) == null) {return null;}
            if (!user.getPassword().equals(serverUser.getPassword())) {return null;}
            return serverUser;
        }
    }
    @Test
    public void test5() throws Exception {
        System.out.println(getUser(new User(30, "", "", "", 0, 0, 0)).getName());
    }
    public User getUser (User user) throws SQLException{
        Connection conn = getConnection();

        try (conn) {
            UserDAO userDAO = new UserDAO(conn, user);
            User serverUser;
            System.out.println(1);
            if ((serverUser = userDAO.query()) == null) {return null;}
            serverUser.setPassword("");
            return serverUser;
        }
    }
    @Test
    public void test3() throws Exception {
        System.out.println(update(new User(14, "wb", "男", "00000", 0, 0, 0)));
    }
    public boolean update(User user) throws SQLException {
        Connection conn = getConnection();

        try (conn) {
            UserDAO userDAO = new UserDAO(conn, user);
            userDAO.update();
            return true;
        }
    }

    @Test
    public void test4() throws Exception {
        System.out.println(delete(new User(14, "wb", "男", "00000", 0, 0, 0)));
    }
    public boolean delete(User user) throws SQLException {
        Connection conn = getConnection();

        try (conn) {
            UserDAO userDAO = new UserDAO(conn, user);
            userDAO.delete();
            return true;
        }
    }
}
