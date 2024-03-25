package entity.room;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Anselm
 * @date 2024/2/6 23 22
 * description
 */

public class RoomSever {
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
        addRoom(new Room(0,"房间名","简介",6,11,1,2,true));
    }
    public boolean addRoom(Room room) throws SQLException {
        Connection conn = getConnection();
        try (conn) {
            RoomDAO roomDAO = new RoomDAO(conn,room);
            if (roomDAO.query() == null){
                roomDAO.insert();
                return true;
            } else {
                return false;
            }
        }
    }
    @Test
    public void test2() throws Exception {
        deleteRoom(new Room(4,"1","2",6,11,1,2,true));
    }
    public void deleteRoom(Room room) throws SQLException {
        Connection conn = getConnection();
        try (conn) {
            RoomDAO roomDAO = new RoomDAO(conn,room);
            roomDAO.delete();
        }
    }

    @Test
    public void test3() throws Exception {
        Room room = new Room(5, "1", "2", 6, 11, 1, 2, true);
        Room room1 = getRoom(room);
        System.out.println(room1.getID()+room1.getName()+room1.getGameType()+room1.getIntroductory());
    }
    public Room getRoom(Room room) throws SQLException {
        Connection conn = getConnection();
        try (conn) {
            RoomDAO roomDAO = new RoomDAO(conn,room);
            return roomDAO.query();
        }
    }
    @Test
    public void test4() throws Exception {
        Room room= new Room(5, "5", "5", 29, 28, 5, 5, false);
        update(room);
        Room room1 = getRoom(room);
        System.out.println(room1.getID()+room1.getName()+room1.getGameType()+room1.getIntroductory());
    }
    public void update(Room room) throws SQLException {
        Connection conn = getConnection();
        try (conn) {
            RoomDAO roomDAO = new RoomDAO(conn,room);
            roomDAO.update();
        }
    }
    @Test
    public void test5() throws Exception {
        Room[] allRoom = getAllRoom();
        System.out.println(allRoom.length);
        for (Room room:allRoom){
            System.out.println("‘’‘");
            System.out.println(room.getName());
        }
    }
//    public int getRoomNumber() throws SQLException {
//        Connection conn = getConnection();
//        try (conn) {
//            RoomDAO roomDAO = new RoomDAO(conn,null);
//            return roomDAO.queryRoomNumber();
//        }
//    }
    public Room[] getAllRoom() throws SQLException{
        Connection conn = getConnection();
        try (conn) {
            RoomDAO roomDAO = new RoomDAO(conn,null);
            return roomDAO.getAll();
        }
    }
}
