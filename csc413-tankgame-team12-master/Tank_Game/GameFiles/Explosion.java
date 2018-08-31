/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.*;
/**
 *
 * @author jrettinghouse
 */
public class Explosion {
    private Image [] img;
    private int x, y;
    private int explosionFrame;
    private int explosionDelay;
    private boolean Visable = true;
    
    public Explosion(int x, int y, Image[] img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.Visable = true;
        this.explosionFrame = 1;
    }
    
    public boolean getVisability(){
        return this.Visable;
    }
    
    public void update(){
        if(explosionFrame <= img.length){ //&& explosionDelay == 0){
            explosionFrame++;
          //  explosionDelay = 10;
        }else if (explosionFrame < img.length){ // && explosionDelay > 0){
            explosionDelay--;
        } else {
            Visable = false;
        }
    }
    
    public void draw(Graphics g, ImageObserver obs){
        if (Visable = true && (explosionFrame-1 != img.length)){ // explosion frame doesnt draw past the array bound
            g.drawImage(img[explosionFrame-1], x, y, obs);
        }
    }
    
}
