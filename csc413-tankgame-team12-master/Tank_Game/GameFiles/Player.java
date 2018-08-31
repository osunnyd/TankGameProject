
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;

/**
 *
 * @author jrettinghouse
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.awt.image.ImageObserver;




public class Player {
    private int life, health, bonus, scoreKills, scoreDeaths;
    private int ID, fire;
    private int forward, backward, rotateLeft, rotateRight;
    private int tankDamage = 0; // ??? what should be the default
    private Tank theTank;
    private Image tankImage;

    public Player(int life, int health, int bonus, int forward, int backward, int rotateLeft, int rotateRight, int fire, int id){
        this.life = life;
        this.health = health;
        this.bonus = bonus;

        this.forward = forward;
        this.backward = backward;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;

        this.fire = fire;

        this.ID = id;
        this.scoreKills = 0;
        this.scoreDeaths = 0;

        try{
            if (id == 1) {
                tankImage = ImageIO.read(Player.class.getResource("/Tank_Wars_Resourses/Tank1.png"));
                theTank = new Tank(tankImage, 400, 350, forward, backward, rotateLeft, rotateRight, fire, 1, 1);
            }else {
                tankImage = ImageIO.read(Player.class.getResource("/Tank_Wars_Resourses/Tank2.png"));
                theTank = new Tank(tankImage, 1500, 1000, forward, backward, rotateLeft, rotateRight, fire, 1, 2);
            } 
            System.out.println("Resource Read");
        } catch (Exception e) {
            System.out.print("Could not find resources.");
        }
    }

    //getters
    public int getScoreKills(){
        return this.scoreKills;
    }

    public int getScoreDeaths(){
        return this.scoreDeaths;
    }

    public int getPlayerId(){
        return this.ID;
    }

    public Tank getTank(){
        return this.theTank;
    }


    //Modifiers
    public void addScoreKills(){
        this.scoreKills++;
    }

    public void addScoreDeaths(){
        this.scoreDeaths++;
    }
    


}

