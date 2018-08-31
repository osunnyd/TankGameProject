
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;

import java.awt.*;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.awt.Image;

/**
 *
 * @author jrettinghouse
 */
public class GameObject {
    protected int x = 0, y = 0, width, height, speed; //anything else???? also private or protected???
    protected Image image;
    

    public GameObject(Image img, int x, int y, int speed){
        this.image = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public void setX(int a){
        this.x = a;
    }
    
    public void setY(int b){
        this.y = b;
    }
    
    public void draw(Graphics g, ImageObserver obs){
        g.drawImage(image, x, y, obs);
    }

}

