/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;


/**
 *
 * @author jrettinghouse
 */
public class Pickups extends GameObject {
  
    Image img;
    int type;
    private boolean pickedUp;
    
    public Pickups(Image image, int x, int y, int speed, int type) {
        super(image, x, y, speed);
        this.img = image;
        this.x = x;
        this.y = y;
        this.type = type;
        pickedUp = false;
  }
    public int getPickup() {
        return this.type;
    }
  public boolean checkPickedUp(){
      return pickedUp;
  }
  public void ispickedUp(boolean p){
      this.pickedUp = p;
  }
    @Override
      public void draw(Graphics g, ImageObserver i) {
      g.drawImage(img, x, y, i);
  }  
}


