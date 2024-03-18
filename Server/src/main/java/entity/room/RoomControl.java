package entity.room;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;
import exception.MessageTypeException;
import net.ConnectThread;
import net.message.CreateRoomResponse;
import net.message.GetRoomsResponse;
import net.message.Message;
import net.message.RegisterResponse;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * @author Anselm
 * @date 2024/2/6 11 17
 * description
 */

public class RoomControl implements MessageConstant {
    private ConnectThread connectThread;
    private RoomSever roomSever;
    private static final Gson gson = new Gson();
    private static final int CREATE = 0;
    private static final int GET_ROOMS = 1;

    private RoomControl() {
    }

    public RoomControl(ConnectThread connectThread) {
        this.connectThread = connectThread;
        this.roomSever = new RoomSever();
    }

    public void acceptMessage(@NotNull Message message){
        System.out.println(message);
        Room room = gson.fromJson(message.getMessage(),Room.class);
        int type = analyse(message);
        switch (type){
            case CREATE -> create(room);
            case GET_ROOMS -> getRooms();
            case -1 -> throw new MessageTypeException();
        }
    }

    private int analyse(@NotNull Message message) {
        String messageName = message.getMessageName();
        if("CreateRoomMessage".equals(messageName)){return CREATE;}
        if("GetRoomsMessage".equals(messageName)){return GET_ROOMS;}
        return -1;
    }
    private void getRooms() {
        try {
            Room[] allRoom = roomSever.getAllRoom();
            connectThread.addMessage(gson.toJson(new GetRoomsResponse(allRoom.length,gson.toJson(allRoom))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Room room){
        try {
            boolean sign = roomSever.addRoom(room);
            if (sign) {
                Room severRoom = roomSever.getRoom(room);
                connectThread.addMessage(gson.toJson(new CreateRoomResponse(OK,gson.toJson(severRoom))));
            }else {
                connectThread.addMessage(gson.toJson(new CreateRoomResponse(CLIENT_ERROR, "房间名重复")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
