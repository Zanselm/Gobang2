package entity;

import lombok.*;

/**
 * @author Anselm
 * @date 2024/2/4 12 10
 * description
 */
@Data
@Getter
@Setter
@NoArgsConstructor

public class Room {
    private int ID;
    private String name;
    private String introductory;
    private int userL;
    private int userR;
    private int gameType;
    private int whoFirst;
    private boolean observable;
    
    
    public Room(int ID, String name, String introductory, int userL, int userR, int gameType, int whoFirst, boolean observable) {
        this.ID = ID;
        this.name = name;
        this.introductory = introductory;
        this.userL = userL;
        this.userR = userR;
        this.gameType = gameType;
        this.whoFirst = whoFirst;
        this.observable = observable;
    }


    
}
