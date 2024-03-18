package ui.zui;

import entity.Room;
import org.jetbrains.annotations.NotNull;
import utils.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * @author Anselm
 * @date 2024/3/14 20 22
 * description
 */

public class RoomListPanel extends JPanel {
    static Font font;
    int top;
    RoomInformationPanel selectedRIP;
    ArrayList<RoomInformationPanel> roomInformationPanels;
    RoomListPanel roomListPanel = this;
    static {
        font = FontLoader.getFont();
    }
    public RoomListPanel(int x,int y,int width,int height,int rows){
        init(x, y, width, height, rows);
    }

    private void init(int x, int y, int width, int height, int rows) {
        setBounds(x, y, width, height);
        setBackground(new Color(0,0,0,0));
        setLayout(new GridLayout(rows, 1));
        roomInformationPanels = new ArrayList<>();
        addListener();
        for (int i = 0; i < 20; i++) {
            addRoom(new Room(i,String.valueOf(i),String.valueOf(i*3),0,0,0,0,false));
        }
    }
    public void addRoom(Room room){
        roomInformationPanels.add(new RoomInformationPanel(room,this));
        add2Panel();
        flush();
    }
    public void addRoomList(Room @NotNull [] rooms){
        for (Room room : rooms) {
            roomInformationPanels.add(new RoomInformationPanel(room,this));
        }
        add2Panel();
        flush();
    }
    public void removeRoom(Room room){
    }
    private void add2Panel(){
        int sign;
        if (roomInformationPanels.size()-top>10){
            sign = top + 10;
        }else {
            sign = roomInformationPanels.size();
        }
        for (int i = top; i < sign; i++) {
            add(roomInformationPanels.get(i));
        }
    }
    public void flush(){
        setVisible(false);
        setVisible(true);
    }

    private void addListener(){
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (roomInformationPanels.size() - top > 10){
                    removeAll();
                    if (e.getWheelRotation()>0){
                        top++;
                    }else {
                        if (top!=0){
                            top--;
                        }
                    }
                    add2Panel();
                    flush();
                }else {
                    if (e.getWheelRotation()<0){
                        if (top!=0){
                            top--;
                            removeAll();
                            add2Panel();
                            flush();
                        }
                    }
                }
            }
        });
    }

    private static class RoomInformationPanel extends JPanel{
        static Font font = RoomListPanel.font.deriveFont(20.0F);
        int id;
        boolean clicked;
        Room room;
        RoomListPanel roomListPanel;
        RoomInformationPanel roomInformationPanel = this;

        static {
            UIManager.put("Label.font",font);
        }
        public RoomInformationPanel(Room room,RoomListPanel roomListPanel){
            id = room.getID();
            this.roomListPanel = roomListPanel;
            this.room = room;
            setFont(font);
            setLayout(new GridLayout(1,4));
            setBackground(new Color(0,0,0,0));
            JLabel id = new JLabel("ID:"+String.valueOf(room.getID()));
            JLabel introduction = new JLabel("房间名： "+room.getIntroductory());
            JLabel gameType = new JLabel("游戏类型： "+String.valueOf(room.getGameType()));
            JLabel first = new JLabel("先着： "+String.valueOf(room.getWhoFirst()));
            add(id);
            add(introduction);
            add(gameType);
            add(first);
            addListener();
        }

        private void addListener() {
//            addMouseWheelListener(e -> {
//                roomListPanel.removeAll();
//                if (e.getWheelRotation()>0){
//                    roomListPanel.top++;
//                }else {
//                    if (roomListPanel.top!=0){
//                        roomListPanel.top--;
//                    }
//                }
//                roomListPanel.add2Panel();
//                roomListPanel.flush();
//            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    setBackground(new Color(0,0,0,100));
                    if (roomListPanel.selectedRIP != null) {
                        roomListPanel.selectedRIP.setBackground(new Color(0,0,0,0));
                        roomListPanel.selectedRIP.clicked = false;
                    }
                    roomListPanel.selectedRIP = roomInformationPanel;
                    clicked = !clicked;
                    roomListPanel.flush();
                }

//                @Override
//                public void mousePressed(MouseEvent e) {
//                    super.mousePressed(e);
//                    setBackground(new Color(0,0,0,100));
//                    roomListPanel.flush();
//                }
//
//                @Override
//                public void mouseReleased(MouseEvent e) {
//                    super.mouseReleased(e);
//                    setBackground(new Color(0,0,0,0));
//                    roomListPanel.flush();
//                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    if (!clicked) {
                        setBackground(new Color(0,0,0,50));
                        roomListPanel.flush();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    if (!clicked){
                        setBackground(new Color(0,0,0,0));
                        roomListPanel.flush();
                    }

                }
            });
        }

    }
}
