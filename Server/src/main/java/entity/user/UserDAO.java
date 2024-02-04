package entity.user;

import java.sql.*;

/**
 * @author Anselm
 * @date 2024/2/4 12 20
 * description
 */

public class UserDAO {

    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new UserDAO();
        userDAO.insertPlayer(new User(0,"乔改平","女","1234566",0,0,0));
    }
    private final String url = "jdbc:mysql://150.158.45.98:3306/userdb";
    private final String user = "userdb";
    private final String password = "root";

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

    public void insertPlayer(User user) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, this.user, password);

//        从连接中获取执行对象
        String sql = "insert into user(name, sex,win,lose,password,avatar) value(?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getSex());
        pstmt.setInt(3, user.getWin());   //Mysql里char对应的Java类型为String
        pstmt.setInt(4, user.getLose());
        pstmt.setString(5, user.getPassword());
        pstmt.setInt(6, user.getAvatar());

//         执行sql
        ResultSet resultSet;
        try {
//            开始事务
            conn.setAutoCommit(false);
            pstmt.executeUpdate();
//            通过数据库自增来获取用户ID
            sql = "select last_insert_id()";
            resultSet = pstmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
//            结束事务
            conn.commit();
        } catch (SQLException e) {
//            如果报错 回滚
            conn.rollback();
            throw new RuntimeException(e);
        }

//        释放
        resultSet.close();
        pstmt.close();
        conn.close();

    }
}