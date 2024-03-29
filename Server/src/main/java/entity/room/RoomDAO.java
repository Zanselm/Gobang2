package entity.room;

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

    public RoomDAO(Connection conn, Room room) {
        this.conn = conn;
        this.room = room;
    }

    private static @NotNull Room get(@NotNull ResultSet resultSet) throws SQLException {
        Room serverRoom = new Room();
        serverRoom.setID(resultSet.getInt("id"));
        serverRoom.setName(resultSet.getString("name"));
        serverRoom.setIntroductory(resultSet.getString("introductory"));
        serverRoom.setUserL(resultSet.getInt("user_l"));
        serverRoom.setUserR(resultSet.getInt("user_r"));
        serverRoom.setGameType(resultSet.getInt("game_type"));
        serverRoom.setWhoFirst(resultSet.getInt("who_first"));
        serverRoom.setObservable(resultSet.getBoolean("observable"));
        return serverRoom;
    }

    private static void set(@NotNull Room room, @NotNull PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, room.getName());
        pstmt.setString(2, room.getIntroductory());
        pstmt.setInt(3, room.getUserL());
        pstmt.setInt(4, room.getUserR());
        pstmt.setInt(5, room.getGameType());
        pstmt.setInt(6, room.getWhoFirst());
        pstmt.setBoolean(7, room.isObservable());
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
        int id = room.getID();
        PreparedStatement pstmt;
        if (id == 0) {
            String sql = "select * from room where  name = ?";
            pstmt = conn.prepareStatement(sql);

        } else {
            String sql = " select * from room where  id = ?";
            pstmt = conn.prepareStatement(sql);
        }

//        设置参数
        if (id == 0) {
            pstmt.setString(1, room.getName());
        } else {
            pstmt.setInt(1, id);
        }

//         执行sql
        ResultSet resultSet = pstmt.executeQuery();

//        获取User
        if (!resultSet.next()) {
            return null;
        }
        Room serverRoom = get(resultSet);

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

    private int queryRoomNumber() throws SQLException {
        String sql = "select count(*) from room";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public Room[] getAll() throws SQLException {
        Room[] rooms = new Room[queryRoomNumber()];
        String sql = "select * from room";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet resultSet = pstmt.executeQuery();

        int i = 0;
        while (resultSet.next()) {
            rooms[i] = get(resultSet);
            i++;
        }
        return rooms;
    }
}
