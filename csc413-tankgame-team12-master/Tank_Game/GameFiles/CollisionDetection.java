/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;


import java.awt.Rectangle;
import java.util.ArrayList;


/**
 *
 * @author jrettinghouse
 */
public class CollisionDetection {
    GameEvents gameEvent1, gameEvent2;
    Rectangle wallHitbox, wallHitbox2, player1box, player2box, bulletHitBox;
    Bullets bullet;
    int respawnx, respawny;
    int respawnTime;
    boolean brokenWall;
  
  
    public CollisionDetection(GameEvents gameEventA, GameEvents gameEventB ){
        this.gameEvent1 = gameEventA;
        this.gameEvent2 = gameEventB;
       
    }    
    public void playerVsPlayer(Player player1, Player player2){
        //Draw Rectangle for tank 1 and 2
        Tank p1 = player1.getTank();
        Tank p2 = player2.getTank();
        player1box = new Rectangle(p1.getX() +(int) p1.getdX(), p1.getY()+(int)p1.getdY(), p1.getWidth()-10, p1.getHeight()-10);
        player2box = new Rectangle(p2.getX()+(int)p2.getdX(), p2.getY()+(int)p2.getdY(), p2.getWidth()-10, p2.getHeight()-10);
        
        //Check to see if they are overlapping
            //Do operation
        if(player1box.intersects(player2box)){
            p1.ranIntoObject(true);
            p2.ranIntoObject(true);
        }
    }    
        
    public void playerVsBullets(Player player1, Player player2){
        //bullet
       
        Tank p1 = player1.getTank();
        Tank p2 = player2.getTank();
        player1box = new Rectangle(p1.getX() +(int) p1.getdX(), p1.getY()+(int)p1.getdY(), p1.getWidth()-10, p1.getHeight()-10);
        ArrayList<Bullets> player1bullets = player1.getTank().getBulletList();
        player2box = new Rectangle(p2.getX()+(int)p2.getdX(), p2.getY()+(int)p2.getdY(), p2.getWidth()-10, p2.getHeight()-10);
        ArrayList<Bullets> player2bullets = player2.getTank().getBulletList();
        //Draw Rectangles for tank 1
        //Draw Rectangle for tank 2
        
        //for loops through tank 1 bullets effecting tank 2
        for(int i = 0; i < player1bullets.size(); i++){
            bullet = player1bullets.get(i);
            bulletHitBox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth()-10, bullet.getHeight()-10);
            if(player2box.intersects(bulletHitBox)){
                 if(p2.didPlayerLose()){
                    player2box.setSize(0,0);
                } else {
                //Damage is done and remove bullet
                p2.loseHealth(bullet.getDamage());
                bullet.setVisability(false);
                 }
            }
        }
        
        //for loops through tank 2 bullets effecting tank 1
        for(int i = 0; i < player2bullets.size(); i++){
            bullet = player2bullets.get(i);
            bulletHitBox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth()-10, bullet.getHeight()-10);
            if(player1box.intersects(bulletHitBox)){
                if(p1.didPlayerLose()){
                    player1box.setSize(0,0);
                } else {
                //Damage is done and remove bullet
                p1.loseHealth(bullet.getDamage());
                bullet.setVisability(false);
                }
            }
        }
    }   
    
    public void bulletVsWall(Player player1, Player player2, Walls wall) {
        //bullet

        ArrayList<Bullets> player1bullets = player1.getTank().getBulletList();
        ArrayList<Bullets> player2bullets = player2.getTank().getBulletList();

        int[][] wallcomparison = wall.getWalls();
        int widthWalls = wall.getMapWidth();
        int lengthWalls = wall.getMapHeight();
        
        for (int y = 0; y < widthWalls; y++) {
            for (int x = 0; x < lengthWalls; x++) {
                if (wallcomparison[y][x] == 1) {
                    wallHitbox = new Rectangle(x * wall.getPictureWidth(), y * wall.getPictureHieght(),
                            wall.getPictureHieght(), wall.getPictureWidth());
                }
                if (wallcomparison[y][x] == 2) {
                    wallHitbox2 = new Rectangle(x * wall.getPictureWidth(), y * wall.getPictureHieght(),
                            wall.getPictureHieght(), wall.getPictureWidth());
                }

              for (int i = 0; i < player1bullets.size(); i++) {
                    bullet = player1bullets.get(i);
                    bulletHitBox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                    if (wallHitbox.intersects(bulletHitBox)) {
                        //Damage is done and remove bullet
                        bullet.setVisability(false);
                    }
                    
                    if (wallHitbox2.intersects(bulletHitBox)) {
                        //Damage is done and remove bullet
                        if (wallcomparison[y][x] == 2) {
                            bullet.setVisability(false);
                            wall.breakWallAt(y, x);
                            respawnx = x;
                            respawny = y;
                            // need to save poisition without updating
                           brokenWall = true;
                        }
                    }
                }
            
 
                for (int i = 0; i < player2bullets.size(); i++) {
                    bullet = player2bullets.get(i);
                    bulletHitBox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                    if (wallHitbox.intersects(bulletHitBox)) {
                        //Damage is done and remove bullet
                        bullet.setVisability(false);
                    }
                    
                    if (wallHitbox2.intersects(bulletHitBox)) {
                        //Damage is done and remove bullet
                        if (wallcomparison[y][x] == 2) {
                            bullet.setVisability(false);
                            wall.breakWallAt(y, x);
                            respawnx = x;
                            respawny = y;
                            // need to save poisition without updating
                            brokenWall = true;
                        }
                    }

                }
            }

        }
        // respawns only last brick that was broken
        if (brokenWall) {
            respawnTime++;
          
            if (respawnTime % 250 == 0) {
                wall.respawnWallAt(respawny, respawnx); 
                brokenWall = false;
                respawnTime = 0;
            }
        }

    }



    
    public void playerVsWall(Player player1, Player player2, Walls wall){
        //Draw Rectangle for tank 1
        //Draw Rectangle for tank 2
        int [][] wallcomparision = wall.getWalls();
        int widthWalls = wallcomparision.length;
        int lengthWalls = wallcomparision[0].length;
        Tank p1 = player1.getTank();
        Tank p2 = player2.getTank();
        player1box = new Rectangle(p1.getX() +(int) p1.getdX(), p1.getY()+(int)p1.getdY(), p1.getWidth()-5, p1.getHeight()-5);
        player2box = new Rectangle(p2.getX()+(int)p2.getdX(), p2.getY()+(int)p2.getdY(), p2.getWidth(), p2.getHeight());
        
        for(int y = 0; y < widthWalls; y++){          
            for(int x = 0; x < lengthWalls; x++){
                if(wallcomparision[y][x] == 1){
                    wallHitbox = new Rectangle(x*wall.getPictureWidth(), y*wall.getPictureHieght() ,wall.getPictureHieght(),wall.getPictureWidth());
                    if(wallHitbox.intersects(player1box)){
                        p1.ranIntoObject(true);
                    }
                    
                    if(wallHitbox.intersects(player2box)){
                        p2.ranIntoObject(true);
                    }
                }
                if (wallcomparision[y][x] == 2) {
                    wallHitbox2 = new Rectangle(x * wall.getPictureWidth(), y * wall.getPictureHieght(), wall.getPictureHieght(), wall.getPictureWidth());
                    if (wallHitbox2.intersects(player1box)) {
                        p1.ranIntoObject(true);
                    }

                    if (wallHitbox2.intersects(player2box)) {
                        p2.ranIntoObject(true);
                    }
                }
            }

        }
    }

    public void tankVspickup(Player player1, Player player2) {
        Tank p1 = player1.getTank();
        Tank p2 = player2.getTank();
        Pickups pickup;
        player1box = new Rectangle(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
        player2box = new Rectangle(p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight());
        for(int i = 0; i < GameWorld.pickupsList.size(); i++) {
            pickup = GameWorld.pickupsList.get(i);
            Rectangle pickupHitbox = new Rectangle(pickup.getX(), pickup.getY(), pickup.getWidth(), pickup.getHeight());
            
            if(player1box.intersects(pickupHitbox)){
                pickup.ispickedUp(true);
                  p1.tankPickup(pickup);
            }
             if(player2box.intersects(pickupHitbox)){
                  pickup.ispickedUp(true); 
                  p2.tankPickup(pickup);
            }
        }
    }
    
    
}
