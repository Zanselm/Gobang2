package ui.zui;

import entity.Room;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import utils.FontLoader;
import utils.MyGson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;


/**
 * @author Anselm
 * @date 2024/3/14 20 22
 * description
 */

public class RoomListPanel extends JPanel {
    static Font font;
    int top;
    RoomInformationPanel selectedRIP;
    LinkedList<RoomInformationPanel> roomInformationPanels;
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
        roomInformationPanels = new LinkedList<>();
        addListener();
    }
    public void addRoom(Room room){
        roomInformationPanels.add(new RoomInformationPanel(room,this));
//        RoomInformationPanel newPanel = new RoomInformationPanel(room, this);
//        if (roomInformationPanels.contains(newPanel)){
//            int i = roomInformationPanels.indexOf(newPanel);
//            roomInformationPanels.remove(i);
//            roomInformationPanels.add(i,newPanel);
//        }else {
//            roomInformationPanels.add(newPanel);
//        }
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
        removeAll();
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
        addMouseWheelListener(e -> {
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
        public RoomInformationPanel(@NotNull Room room, RoomListPanel roomListPanel){
            id = room.getID();
            this.roomListPanel = roomListPanel;
            this.room = room;
            setFont(font);
            setLayout(new GridLayout(1,4));
            setBackground(new Color(0,0,0,0));
            JLabel id = new JLabel("ID:"+String.valueOf(room.getID()));
            JLabel introduction = new JLabel("房间名： "+room.getName());
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
//                    setBackground(new Color(0,0,0,100));
//                    if (roomListPanel.selectedRIP != null) {
//                        roomListPanel.selectedRIP.setBackground(new Color(0,0,0,0));
//                        roomListPanel.selectedRIP.clicked = false;
//                    }
//                    roomListPanel.selectedRIP = roomInformationPanel;
                    if (roomListPanel.selectedRIP == null){
                        roomListPanel.selectedRIP = roomInformationPanel;
                        setBackground(new Color(0,0,0,100));
                        clicked = true;
                    }else {
                        if (clicked){
                            setBackground(new Color(0,0,0,0));
                            roomListPanel.selectedRIP = null;
                            clicked = false;
                        }else {
                            roomListPanel.selectedRIP.clicked = false;
                            roomListPanel.selectedRIP.setBackground(new Color(0,0,0,0));
                            roomListPanel.selectedRIP = roomInformationPanel;
                            setBackground(new Color(0,0,0,100));
                            clicked = true;
                        }
                    }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RoomInformationPanel that = (RoomInformationPanel) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    @TestOnly
    public static void main(String[] args) {
        LinkedList<RoomInformationPanel> roomInformationPanels = new LinkedList<>();
        Room room1 = new Room(1,"","",0,0,0,0,false);
        Room room2 = new Room(2,"","",0,0,0,0,false);
        Room room3 = new Room(3,"123","123",1,2,3,0,false);
        Room room4 = new Room(2,"2222","2222",1,2,3,0,false);
        RoomInformationPanel roomInformationPanel1 = new RoomInformationPanel(room1, null);
        RoomInformationPanel roomInformationPanel2 = new RoomInformationPanel(room2, null);
        RoomInformationPanel roomInformationPanel3 = new RoomInformationPanel(room3, null);
        RoomInformationPanel roomInformationPanel4 = new RoomInformationPanel(room4, null);
        roomInformationPanels.add(roomInformationPanel1);
        roomInformationPanels.add(roomInformationPanel2);
        roomInformationPanels.add(roomInformationPanel3);
        roomInformationPanels.add(roomInformationPanel4);
//        System.out.println(roomInformationPanels.indexOf(roomInformationPanel1));
//        System.out.println(roomInformationPanels.indexOf(roomInformationPanel2));
//        System.out.println(roomInformationPanels.indexOf(roomInformationPanel3));
        System.out.println(roomInformationPanels.indexOf(new RoomInformationPanel(new Room(),null)));
        for (RoomInformationPanel roomInformationPanel:roomInformationPanels){
            System.out.println(roomInformationPanel.id+""+roomInformationPanel.equals(roomInformationPanel4));
        }

    }
}
