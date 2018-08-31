
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.Observer;
import java.util.Observable;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.util.ArrayList;


public class Tank extends GameObject implements Observer, ActionListener{
    GameEvents events;
    Sound explosion = new Sound();
    Image bulletImage;
    int damage;
    int k; // to rotate tank
    int ANGLE;
    private static boolean[] keys = new boolean [120]; // for reading keycodes of arrow keys or wasd
    private static boolean rotateLeft, rotateRight;
    private ArrayList <Bullets> BulletList = new ArrayList<>();
    private ArrayList<Explosion> explosionList = new ArrayList<>();
    Image [] smallExplosion = new Image[6];
    Image [] largeExplosion = new Image[7];
    Pickups typeofPickup;
    //private variables
    private double velX = 0, velY = 0;
    private int id;
    private int health, life, bonus, bulletDam, healthpercentage;
    private int livesLeft;
    private boolean hasLost; //for when tank dies
    private Image img, victory, death, defeat;
    private GameObject healthIndicator;
    Graphics2D g;
    Timer t;
    Bullets bullet;
    private int readyToFire;
    private int respawnX, respawnY;
    private boolean ranIntoObject;
    boolean poweredUp = false; // initially not powered up
    

    //default constructor
    Tank(Image img, int x, int y, int up, int down, int left, int right, int fire, int speed, int id){
        super(img, x, y, speed);
        readyToFire = 0; // fire rate
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.id = id;
        respawnX = x;
        respawnY = y;
        damage = 10; // default bullet damage
        t = new Timer(10, this);
        t.start();
        health = 100;
        bulletDam = 10;
        life = 3;
        hasLost = false;
       
       // addKeyListener(this);
       try{
           bulletImage = ImageIO.read(Tank.class.getResource("/Tank_Wars_Resourses/Shell.png"));
           victory = ImageIO.read(Tank.class.getResource("/Tank_Wars_Resourses/Victory.png"));
           defeat = ImageIO.read(Tank.class.getResource("/Tank_Wars_Resourses/Defeat.png"));
           death = ImageIO.read(Tank.class.getResource("/Tank_Wars_Resourses/lose.png"));
           
           image = bulletImage; // for when firing
          
       } catch (Exception e) {
           System.out.print("No image found for tank bullet!");
       }
          
         
    }
    
    private Bullets makeBullet(Image bulletType, int damage) {
         int bulletCenterX = (int) x + (this.getWidth() / 3);
         int bulletCenterY = (int) y + (this.getHeight() / 3);
        if(!poweredUp) {
            this.damage = damage;
            bullet = new Bullets(image, bulletCenterX, bulletCenterY, this.damage, 1,
                    Math.cos(Math.toRadians(k)) * 10, Math.sin(Math.toRadians(k)) * 10, k);
        }
         return bullet;
    }
    public void fire(){
       if(readyToFire == 0){
        bullet = makeBullet(image, this.damage);
        BulletList.add(bullet);
        explosion.smallExplosion();// need to reset sound after; only plays once
        readyToFire = 7;
       } 
    }
    
    public ArrayList<Bullets> getBulletList(){
        // BulletList.add(bullet); // initial bullet
        return BulletList;
    }
    
    public Bullets getBullet() {
       return bullet;
   }

     @Override
    public void draw( Graphics g, ImageObserver i) {
        // draw health bar
        g.setColor(Color.green);
        g.fillRect(x, y-10, (this.getWidth() * health)/100 , 10);
        
        //draw stats
        if(id == 1){
            //background
            g.setColor(Color.yellow);
            g.fillRect(0, 2200, 300 , 257 );
            
            //text
            g.setColor(Color.black);
            Font stringFont = new Font("TimesRoman", Font.PLAIN, 50);
            g.setFont(stringFont);
            g.drawString("Player 1", 10, 2235);
            String lives = "Lives left : " + life;
            g.drawString(lives, 10, 2280);
            if (life == 0 && health <= 0) {
                this.k = 0;
                this.img = death;
               // g.drawImage(defeat, x, y + 50, i);
             }   
        } else {
            //background
            g.setColor(Color.yellow);
            g.fillRect(0, 2500, 300 , 257 );
            g.setColor(Color.black);
            Font stringFont = new Font("TimesRoman", Font.PLAIN, 50);
            g.setFont(stringFont);
            g.drawString("Player 2", 10, 2535);
             String lives = "Lives left : " + life;
             g.drawString(lives, 10, 2580);
             if (life == 0 && health <= 0) {
                 this.k = 0;
                 this.img = death;
                 //g.drawImage(defeat, x, y + 50, i);
             }
         }

        
        this.g = (Graphics2D) g;
        AffineTransform transform = this.g.getTransform(); // save transformation to reset later
        //rotate at center
        this.g.rotate(Math.toRadians(k), super.getX() + super.getWidth()/2, super.getY()+ super.getHeight()/2);
        this.g.drawImage(this.img, super.getX(), super.getY(), i); 
        this.g.setTransform(transform); // reset graphic to not rotate everything 
    }
    public double getdX() {
        return velX;
    }
      public double getdY() {
        return velY;
    }

  // reads key input
  // observable passes key presses to the observer which updates
  // and goes to key adapter in
  // the KeyControl class
    @Override
    public void update(Observable o, Object o1) {
        GameEvents event1 = (GameEvents) o;
        if (event1.type == 1) {
            KeyEvent e = (KeyEvent) event1.event;
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                keys[e.getKeyCode()] = false; // if a arrow key or wasd is released then false
                // stops movement
                velX = 0;
                velY = 0;
                ANGLE = 0;
                rotateLeft = false;
                rotateRight = false;
                update();
            }
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                keys[e.getKeyCode()] = true; // if  arrow key or wasd is pressed then true
                rotateLeft = true;
                rotateRight = true;
                update();
            }
        }
    }
    
     public void update() {
         
         // movement; true or false based on key presses 
         if (this.id == 2) {
             if (keys[KeyEvent.VK_UP]) {
                 //need to multiply by some factor at the end so it doesn't instantly int cast to 0
                velX =  (speed * Math.cos(Math.toRadians(k))*5);
                velY =  (speed * Math.sin(Math.toRadians(k))*5);

             }
             if (keys[KeyEvent.VK_DOWN]) {
                velX =  -(speed * Math.cos(Math.toRadians(k))*5);
                velY =  -(speed * Math.sin(Math.toRadians(k))*5);
             }
             if (keys[KeyEvent.VK_LEFT]) { 
                ANGLE = -5;
             }
             if (keys[KeyEvent.VK_RIGHT]) {
               ANGLE = 5;
             }     
             if (keys[KeyEvent.VK_ENTER]) {
                 //fire bullet
                 fire();
             }
              
            } else {
            if (keys[KeyEvent.VK_W]) {
                velX = (speed * Math.cos(Math.toRadians(k)) * 5);
                velY = (speed * Math.sin(Math.toRadians(k)) * 5);
            }
            if (keys[KeyEvent.VK_S]) {
                velX = -(speed * Math.cos(Math.toRadians(k)) * 5);
                velY = -(speed * Math.sin(Math.toRadians(k)) * 5);
            }
            if (keys[KeyEvent.VK_A]) {
               ANGLE = -5; // rotate counter-clockwise
            }
            if (keys[KeyEvent.VK_D]) {
                 ANGLE = 5;  //rotate clockwise
            }
            if (keys[KeyEvent.VK_SPACE]) {
                //fire bullet
                fire();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         if(readyToFire != 0){
             readyToFire--;
         }
        k+=ANGLE;
        // need x+=velX for smooth movement; constantly updates positon 
        if(ranIntoObject == true){ //for collision of tank vs tank and tank vs wall
            x -= velX;
            y -= velY;
        }else{
            x += velX;
            y += velY;  
        }
        ranIntoObject = false;
    }
    
    public void loseHealth(int damageDone){
        if(damageDone >= health){
            health = health - damageDone;
            System.out.println("Tank is dead: " + health);
            this.tankDies();
        } else {
            health = health - damageDone;
            System.out.println("Current health: "+health);
        } 
    }
    
    public void tankPickup(Pickups type) {
       
        int pickupType = type.getPickup();
        
        if (!poweredUp) {
            
        if (pickupType == 1) {
             image = type.img;
            bullet = makeBullet(image, 20);  
        }
        
        if (pickupType == 2) {
             image = type.img;
           bullet = makeBullet(type.img, 50);        
        }
        
        if (pickupType == 3) {
             image = type.img;
            bullet = makeBullet(type.img, 80);
        }

        if (pickupType == 4) {
            gainHealth(30);
            g.setColor(Color.red);
            g.fillRect(x, y - 10, (this.getWidth() * health) / 100, 10);
        }
        if (pickupType == 5) {
            gainHealth(50);
            g.setColor(Color.blue);
            g.fillRect(x, y - 10, (this.getWidth() * health) / 100, 10);
        }
        } else {
           
        }
        
    }


    
    
    public void tankDies(){
        if (life > 0){
          GameWorld.explosionList.add(new Explosion(x,y,GameWorld.largeExplosion));
            this.x = respawnX;
            this.y = respawnY;
              explosion.bigExplosion();// need to reset sound after; only plays once
            health = 100;
            life--;
        } else {
            GameWorld.explosionList.add(new Explosion(x,y,GameWorld.largeExplosion));
            hasLost = true;
        }
        
    }
    
    public void gainHealth(int healthGained){
        health = health + healthGained;
    }
    
    public void ranIntoObject(boolean changeDirection){
        ranIntoObject = changeDirection;
    } 
    public Image player1Win() {
        return victory;
    }
    public boolean didPlayerLose(){
        return hasLost;
    }
}