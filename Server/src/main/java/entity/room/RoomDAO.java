package entity.room;

import entity.user.User;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anselm
 * @date 2024/2/6 23 22
 * description
 */

public class RoomDAO {
    Connection conn;
    Room room;

    public RoomDAO(Connection conn,Room room) {
        this.conn = conn;
        this.room = room;
    }

    public int insert() throws SQLException {
//        从连接中获取执行对象
        String sql = "insert into room(name, introductory,user_l,user_r,game_type,who_first,observable) value(?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        set(room, pstmt);
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    public int delete() throws SQLException {
//        从连接中获取执行对象
        String sql = "delete from room where id =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        pstmt.setInt(1, room.getID());

//         执行sql
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    public Room query() throws SQLException {
//        从连接中获取执行对象
        String sql = "select * from room where  id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数
        pstmt.setInt(1,room.getID());

//         执行sql
        ResultSet resultSet = pstmt.executeQuery();

//        获取User
        if (!resultSet.next()){return null;}
        Room serverRoom = new Room();
        serverRoom.setID(resultSet.getInt("id"));
        serverRoom.setName(resultSet.getString("name"));
        serverRoom.setIntroductory(resultSet.getString("introductory"));
        serverRoom.setUserL(resultSet.getInt("user_l"));
        serverRoom.setUserR(resultSet.getInt("user_r"));
        serverRoom.setGameType(resultSet.getInt("game_type"));
        serverRoom.setWhoFirst(resultSet.getInt("who_first"));
        serverRoom.setObservable(resultSet.getBoolean("observable"));

//        释放
        resultSet.close();
        pstmt.close();

        return serverRoom;
    }

    public int update() throws SQLException {
//        从连接中获取执行对象
        String sql = "update room set name = ?, introductory = ?,user_l = ?,user_r = ?" +
                ",game_type = ?,who_first = ?,observable = ? where id =?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);

//        设置参数 依次对应sql里的问号
        set(room, pstmt);
        pstmt.setInt(8, room.getID());

//         执行sql
        int i = pstmt.executeUpdate();

//        释放
        pstmt.close();
        return i;
    }

    private static void set(@NotNull Room room, @NotNull PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, room.getName());
        pstmt.setString(2,room.getIntroductory());
        pstmt.setInt(3, room.getUserL());
        pstmt.setInt(4, room.getUserR());
        pstmt.setInt(5, room.getGameType());
        pstmt.setInt(6,room.getWhoFirst());
        pstmt.setBoolean(7,room.isObservable());
    }
}
