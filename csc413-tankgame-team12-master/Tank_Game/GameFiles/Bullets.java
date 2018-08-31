/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 *
 * @author jrettinghouse
 */


public class Bullets extends GameObject{
    private int damage, k;
    private double xspeed, yspeed;
    private boolean visable;
    private int bulletDelay;
    Graphics2D g;
    
    public Bullets(Image image, int x, int y, int damage, int speed ,double Xspeed, double Yspeed, int angle) {
        super(image, x, y, speed);
        bulletDelay = 0;
        this.damage = damage;
        this.xspeed = Xspeed;
        this.yspeed = Yspeed;
        this.visable = true;
        k = angle;
    }
    
    public int getDamage(){
        return this.damage;
    }
    
    public boolean getVisability(){
        return this.visable;
    }
    
    public void setVisability(boolean visable){
        this.visable = visable;
    }
    
    public void update(int w, int h){
       
        if( y > 0 && y < h  && x > 0 && x < w && visable){
            bulletDelay = 10;
            x += xspeed;
            y += yspeed;
        }else{
            visable  = false;
                    }
        
    }
    
    public void draw(Graphics g, ImageObserver obs){
        if(visable){
            this.g = (Graphics2D) g;
        AffineTransform transform = this.g.getTransform(); // save transformation to reset later
        //rotate at center
        this.g.rotate(Math.toRadians(k), super.getX() + super.getWidth()/2, super.getY()+ super.getHeight()/2);
        this.g.drawImage(image, super.getX(), super.getY(), obs); 
        this.g.setTransform(transform); // reset graphic to not rotate everything
        }
    }
    
}
