package entity.room;

import com.google.gson.Gson;
import constant.MessageConstant;
import entity.user.User;
import exception.MessageTypeException;
import net.ConnectThread;
import net.Transmitter;
import net.message.*;
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
    private static final int ENTER_ROOM = 2;

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
            case ENTER_ROOM -> enterRoom(room);
            case -1 -> throw new MessageTypeException();
        }
    }



    private int analyse(@NotNull Message message) {
        String messageName = message.getMessageName();
        if("CreateRoomMessage".equals(messageName)){return CREATE;}
        if("GetRoomsMessage".equals(messageName)){return GET_ROOMS;}
        if("EnterRoomMessage".equals(messageName)){return ENTER_ROOM;}
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
                Transmitter.forward(new AlterRoomMessage(severRoom));
            }else {
                connectThread.addMessage(gson.toJson(new CreateRoomResponse(CLIENT_ERROR, "房间名重复")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void enterRoom(Room room) {
        try {
//            更新房间信息并通知全部客户端
            roomSever.update(room);
            Transmitter.forward(new AlterRoomMessage(room));
//            返回创建房间者的用户信息
            User userL = connectThread.getNetMapper().userControl.getUser(room.getUserL());
            connectThread.addMessage(new EnterRoomResponse(userL));
//            告诉创建房间者进入房间者
            User userR = connectThread.getNetMapper().userControl.getUser(room.getUserR());
            Transmitter.forward(new EnterGameUserMessage(room.getUserL(),userR));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
