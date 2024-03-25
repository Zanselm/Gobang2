package net;

import com.google.gson.Gson;
import constant.MessageConstant;
import ui.GameFrame;
import entity.Room;
import net.message.Message;
import ui.GameLobbyFrame;
import ui.LoginFrame;
import ui.RegisterFrame;
import ui.RoomCreateFrame;
import utils.MyGson;

/**
 * @author Anselm
 * @date 2024/2/5 11 48
 * description
 */

public class ClientMapper implements MessageConstant{
    private static final ClientMapper clientMapper = new ClientMapper();

    private static int LoginControl;

    private ClientMapper() {
    }

    public static ClientMapper getClientMapper() {
        return clientMapper;
    }

    public void acceptMessage(String netMessage){
        Message message = new Gson().fromJson(netMessage,Message.class);
        int type = analyse(message);
        String messageType = message.getMessageName();
        System.out.println(message);
        register(message);
        login(message);
        lobby(message);
        game(message);
    }

    private void game(Message message) {
        if (message.getMessageName().equals("EnterGameUserMessage")){
            GameFrame.getGameFrame().playerEnterRoom(message);
        }
        if (message.getMessageName().equals("EnterRoomResponse")){
            GameFrame.getGameFrame().playerEnterRoom(message);
        }
        if (message.getMessageName().equals("CompareNumMessage")){
            GameFrame.getGameFrame().compareNum(message);
        }
        if (message.getMessageName().equals("AddPieceMessage")){
            GameFrame.getGameFrame().addPiece(message);
        }
        if (message.getMessageName().equals("VictoryMessage")){
            GameFrame.getGameFrame().lose();
        }
        if (message.getMessageName().equals("GiveUpMessage")){
            GameFrame.getGameFrame().victory();
        }
    }

    private void lobby(Message message) {
        if (message.getMessageName().equals("GetRoomsResponse")){
            GameLobbyFrame.getGameLobbyFrame().addRooms(message);
        }
        if (message.getMessageName().equals("AlterRoomMessage")){
            GameLobbyFrame.getGameLobbyFrame().addRoom(message);
        }
        if (message.getMessageName().equals("CreateRoomResponse")){
            if (message.getState()==MessageConstant.OK){
                RoomCreateFrame.getRoomCreateFrame().dispose();
                Room room = MyGson.fromJson(message.getMessage(),Room.class);
//                new GameFrame(room.getGameType(),room.getWhoFirst());
                new GameFrame(room);
            }else {
                System.out.println("房间名重复");
            }
        }
    }

    private static void register(Message message) {
        if (message.getMessageName().equals("RegisterResponse")){
            if (message.getState() == OK){
                RegisterFrame.getRegisterFrame().success();
            }
            if (message.getState() == CLIENT_ERROR){
                RegisterFrame.getRegisterFrame().duplicateUsername();
            }
        }
    }

    private void login(Message message) {
        if (message.getMessageName().equals("LoginResponse")){
            if (message.getState() == OK){
                LoginFrame.getLoginFrame().LoginSuccessful(message);
            }
            if (message.getState() == CLIENT_ERROR){
                LoginFrame.getLoginFrame().LoginFailed(message.getMessage());
            }
        }
    }

    private static int analyse(Message message) {
        return 0;
    }
}
