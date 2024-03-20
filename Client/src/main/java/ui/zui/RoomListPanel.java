package ui.zui;

import entity.Room;
import org.jetbrains.annotations.NotNull;
import utils.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        RoomInformationPanel newPanel = new RoomInformationPanel(room, this);
        int index = getIndex(room);
        if (index==-1){
            roomInformationPanels.add(newPanel);
        }else{
            selectedRIP = null;
            roomInformationPanels.remove(index);
            roomInformationPanels.add(index,newPanel);
        }
        add2Panel();
        flush();
    }

    private int getIndex(Room room) {
        int i;
        for (i = 0; i < roomInformationPanels.size(); i++) {
            if (roomInformationPanels.get(i).getRoom().getID() == room.getID()){
                return i;
            }
        }
        return -1;
    }

    public void addRoomList(Room @NotNull [] rooms){
        for (Room room : rooms) {
            roomInformationPanels.add(new RoomInformationPanel(room,this));
        }
        add2Panel();
        flush();
    }
    public void removeRoom(Room room){
        int index = getIndex(room);
        if (index != -1){
            roomInformationPanels.remove(index);
            add2Panel();
            flush();
        }

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

    public RoomInformationPanel getSelectedRIP() {
        return selectedRIP;
    }

    public static class RoomInformationPanel extends JPanel{
        static Font font = RoomListPanel.font.deriveFont(20.0F);
        static int id;
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

            JLabel id = new JLabel("ID:"+String.valueOf(String.format("%07d", room.getID())),JLabel.CENTER);
            JLabel introduction = new JLabel("房间名： "+room.getName(),JLabel.CENTER);
            JLabel gameType;
            if (room.getUserR() == 0){
                gameType = new JLabel("人数： "+"缺",JLabel.CENTER);
            }else {
                gameType = new JLabel("人数： "+"满",JLabel.CENTER);
            }

            JLabel first = new JLabel("先着： "+String.valueOf(room.getWhoFirst()),JLabel.CENTER);
            add(id);
            add(introduction);
            add(gameType);
            add(first);
            addListener();
        }

        public Room getRoom() {
            return room;
        }
        private void addListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
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
            return id == id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
