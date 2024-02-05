package entity.user;

import java.sql.*;

/**
 * @author Anselm
 * @date 2024/2/4 12 20
 * description
 */

public class UserDAO {
    Connection conn;
    User user;

    public UserDAO(Connection conn,User user) {
        this.conn = conn;
        this.user = user;
    }

    public int insert() throws SQLException {
//        从连接中获取执行对象
        String sql = "insert into user(name, sex,win,lose,password,avatar) value(?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        set(user, pstmt);
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    public int delete() throws SQLException {
//        从连接中获取执行对象
        String sql = "delete from user where id =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        pstmt.setInt(1, user.getId());

//         执行sql
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    public User query() throws SQLException {
//        从连接中获取执行对象
        String sql = "select * from user where  name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数
        pstmt.setString(1, user.getName());

//         执行sql
        ResultSet resultSet = pstmt.executeQuery();

//        获取User
        if (!resultSet.next()){return null;}
        User severUser = new User();
        severUser.setId(resultSet.getInt("id"));
        severUser.setName(resultSet.getNString("name"));
        severUser.setSex(resultSet.getString("sex"));
        severUser.setWin(resultSet.getInt("win"));
        severUser.setLose(resultSet.getInt("lose"));
        severUser.setPassword(resultSet.getString("password"));
        severUser.setAvatar(resultSet.getInt("avatar"));

//        释放
        resultSet.close();
        pstmt.close();

        return severUser;
    }

    public int update() throws SQLException {
//        从连接中获取执行对象
        String sql = "update user set name =?,sex=?,win=?,lose=?,password=?,avatar=? where id =?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        set(user, pstmt);
        pstmt.setInt(7, user.getId());

//         执行sql
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    private static void set(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getSex());
        pstmt.setInt(3, user.getWin());
        pstmt.setInt(4, user.getLose());
        pstmt.setString(5, user.getPassword());
        pstmt.setInt(6, user.getAvatar());
    }

}