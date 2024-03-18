package ui.zui;

import entity.User;
import utils.FontLoader;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/14 19 19
 * description
 */

public class UserPanel extends JPanel {
    static Font font;
    User user;
    HeadshotPanel headshotPanel;
    JPanel userInformationPanel;
    static {
        font = FontLoader.getFont();
    }
    public UserPanel(User user,int x,int y,int width,int height){
        this.user = user;
        init(x,y,width,height);
        addUerInformation();
    }

    private void addUerInformation() {
        headshotPanel = new HeadshotPanel(0,0,getHeight(),0,HeadshotPanel.GIRL,HeadshotPanel.NOT);

        userInformationPanel = new JPanel(new GridLayout(2,1));
        userInformationPanel.setBounds(getHeight()+getHeight()/5,0,getWidth()-getHeight()-getHeight()/5,getHeight());
        userInformationPanel.setBackground(new Color(0,0,0,0));

        JLabel name = new JLabel("姓名： "+ user.getName());
        JLabel id = new JLabel("ID: "+ user.getId());
        JLabel win = new JLabel("胜利场数： "+user.getWin());
        JLabel lose = new JLabel("失败场数： "+user.getLose());

        name.setFont(font);
        id.setFont(font);
        win.setFont(font);
        lose.setFont(font);

        add(headshotPanel);
        add(userInformationPanel);
        userInformationPanel.add(name);
        userInformationPanel.add(id);
        userInformationPanel.add(win);
        userInformationPanel.add(lose);
    }

    private void init(int x,int y,int width,int height){
        setBackground(new Color(0,0,0,0));
        setLayout(null);
        setBounds(x,y,width,height);
    }
}
