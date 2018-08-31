
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
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;


public class GameWorld extends JApplet implements Runnable {  
    private Thread thread;
    private Random rand = new Random(); // for generating random pickups
    Image theTank, pickup;
    static ArrayList<Explosion> explosionList = new ArrayList<>();
    static ArrayList<Pickups> pickupsList = new ArrayList<>();
    static Image [] smallExplosion = new Image[6];
    static Image [] largeExplosion = new Image[7];
    static Image [] pickupImage = new Image[5];

    Graphics2D g2D, g2D2;
    Player player1, player2;
    GameEvents gameEvents1, gameEvents2;
    private BufferedImage bimg, bimgPlayer1Cam, bimgPlayer2Cam, bimg4, bimgPlayer1Stats, bimgPlayer2Stats, minimapImage;
    Sound bgMusic = new Sound(); // background music for now; maybe need other sounds as well 
    Background background = new Background();
    Walls wall;
    CollisionDetection collisionCheck;
   // Pickups pickups;
   // int typeofPickup;
    
    private final static int width = 1000, height = 780; //for outer dimentions for bullet


    @Override
    public void init(){
        setFocusable(true);
        try{
            wall = new Walls ("walls.txt"); // making walls through text file
            //replace with numbers later for balencing
            player1 = new Player(1, 1, 1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 1, 1);
            player2 = new Player(1, 1, 1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 1, 2); //numbers don't mean anything replace later
            gameEvents1 = new GameEvents();
            gameEvents2 = new GameEvents();
            gameEvents1.addObserver(player1.getTank());
            gameEvents2.addObserver(player2.getTank());
            KeyControl key1 = new KeyControl(gameEvents1);
            KeyControl key2 = new KeyControl(gameEvents2);
            addKeyListener(key1);
            addKeyListener(key2);
           // typeofPickup = rand.nextInt(5);
            
           
           // pickups
            pickupImage[0] = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Weapon.gif"));
            pickupImage[1] = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Bouncing.gif"));
            pickupImage[2] = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Rocket.gif"));
            pickupImage[3] = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Shield1.gif"));
            pickupImage[4] = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Shield2.gif"));
            
            //testing each pickup
            pickupsList.add(new Pickups(pickupImage[0], 600, 350  , 0, 1));
            pickupsList.add(new Pickups(pickupImage[1], 600, 450, 0, 2));
            pickupsList.add(new Pickups(pickupImage[2], 425, 600, 0, 3));
            pickupsList.add(new Pickups(pickupImage[3], 300, 600, 0, 4));
            pickupsList.add(new Pickups(pickupImage[4], 400, 1000, 0, 5));
            
            pickupsList.add(new Pickups(pickupImage[0], 1500, 925  , 0, 1));
            pickupsList.add(new Pickups(pickupImage[1], 1400, 925, 0, 2));
            pickupsList.add(new Pickups(pickupImage[2], 1300, 1100, 0, 3));
            pickupsList.add(new Pickups(pickupImage[3], 1400, 1100, 0, 4));
            pickupsList.add(new Pickups(pickupImage[4], 1500, 350, 0, 5));
            
            //smallexplosion
            smallExplosion[0] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-0.png"));
            smallExplosion[1] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-1.png"));
            smallExplosion[2] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-2.png"));
            smallExplosion[3] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-3.png"));
            smallExplosion[4] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-4.png"));
            smallExplosion[5] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_small/smallEXPframe-5.png"));
            
            //largeexplosion
            largeExplosion[0] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-0.png"));
            largeExplosion[1] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-1.png"));
            largeExplosion[2] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-2.png"));
            largeExplosion[3] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-3.png"));
            largeExplosion[4] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-4.png"));
            largeExplosion[5] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-5.png"));
            largeExplosion[6] = ImageIO.read(GameWorld.class.getResource("/Tank_Wars_Resourses/Explosion_large/largeEXPframe-6.png"));
            
            
            bgMusic.playBGMusic();
            collisionCheck = new CollisionDetection(gameEvents1, gameEvents2);
        
        } catch (Exception e){
            System.out.print(e.getStackTrace() + " No Resources were found. (init)");

        }
    }
    

    public void drawDemo() {
        
        if(player1.getTank().didPlayerLose()) {
            gameEvents1.deleteObservers();
           
        }
        if(player2.getTank().didPlayerLose()) {
            gameEvents2.deleteObservers();
        }
        
         
        background.paint(g2D);   
        wall.draw(g2D);

        repaint();
        player1.getTank().draw(g2D, this); 
        player2.getTank().draw(g2D, this);
        
        for (int i = 0; i < player1.getTank().getBulletList().size(); i++) {
            player1.getTank().getBulletList().get(i).update(3000, 3000);
            if (player1.getTank().getBulletList().get(i).getVisability() == true) {
                player1.getTank().getBulletList().get(i).draw(g2D, this);
            } else {
                player1.getTank().getBulletList().remove(i);
                i--;
            }

        }
        for (int i = 0; i < player2.getTank().getBulletList().size(); i++) {
            player2.getTank().getBulletList().get(i).update(3000, 3000);
            if (player2.getTank().getBulletList().get(i).getVisability() == true) {
                player2.getTank().getBulletList().get(i).draw(g2D, this);
            } else {
                player2.getTank().getBulletList().remove(i);
                i--;
            }
        }
        

        collisionCheck.bulletVsWall(player1, player2, wall);
        collisionCheck.playerVsBullets(player1, player2);
        collisionCheck.playerVsPlayer(player1, player2);
        collisionCheck.playerVsWall(player1, player2, wall);
        collisionCheck.tankVspickup(player1, player2); // updates pickups but automatically fire bullet
        
        for(int i = 0; i < explosionList.size() ; i++){
            explosionList.get(i).update();
            if(explosionList.get(i).getVisability() == true){
                explosionList.get(i).draw(g2D, this);
            } else {
                explosionList.remove(i);
                i--;      
            }
        }
      
        for (int i = 0; i < pickupsList.size(); i++) {
            if (pickupsList.get(i).checkPickedUp() == false) {
                pickupsList.get(i).draw(g2D, this);
            } else {    
               pickupsList.remove(i);
               i--;
            } 
        }
    }

    
    @Override
    public void start() {
        System.out.println("Start() here");
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);       
        thread.start();
        
    }
    
    @Override
    public void run () {
       Thread theThread = Thread.currentThread();
       while(thread == theThread) {
           repaint();
           try{
               thread.sleep(25);
           } catch (InterruptedException e){
               break;
           }
       }
    }
    
    @Override
    public void paint(Graphics g) {
        Dimension windowSize = getSize();
        if(bimg == null) {
            //Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(3000, 
                    3000);
            g2D = bimg.createGraphics();
            
        }
        //draw everything for graphics
        drawDemo();
        if(!player1.getTank().didPlayerLose() && !player2.getTank().didPlayerLose()){
            //draw subimage around 2 tank based on their locations
            bimgPlayer1Cam = bimg.getSubimage(player1.getTank().getX() - 200, player1.getTank().getY() - 200 , 500 , 500);    
            bimgPlayer2Cam = bimg.getSubimage(player2.getTank().getX() - 200, player2.getTank().getY() - 200 , 500 , 500);

            //grab minimap subimage
            bimg4 = bimg.getSubimage(0,0, 400, 297 );

        
            //player 1 stats
            bimgPlayer1Stats = bimg.getSubimage(0, 2200, 300, 257);
            //player 2 stats        
            bimgPlayer2Stats = bimg.getSubimage(0, 2500, 300, 257);
       
            //draw player1
            g.drawImage(bimgPlayer1Cam, 0, 0, this);
        
            //draw player2
            g.drawImage(bimgPlayer2Cam, width/2 + 3, 0, this);
        
            //draw minimap
            minimapImage = bimg.getSubimage(220, 220, 1500, 2000);
            Image minimap = minimapImage.getScaledInstance(400, 500, Image.SCALE_FAST);
            //Image minimap = bimg.getScaledInstance(400, 500, Image.SCALE_FAST);
            g.drawImage(minimap, width / 2 - 200, 503, this);
            // g.drawImage(bimg4, width/2-200 , 503  , this);
        
            //draw dividers
            g.setColor(Color.black);
            g.fillRect( width/2 , 0 , 3 , 500);
            g.fillRect(0  , 500 , 1000 , 3);
            
            //Stats drawn
            g.drawImage(bimgPlayer1Stats, 0, 503, this);
            g.drawImage(bimgPlayer2Stats, 700, 503, this);
        } else {
            g.setColor(Color.green);
            Font stringFont = new Font("TimesRoman", Font.PLAIN, 70);
            g.setFont(stringFont);
            
            
            String whoWon = "";
            if (player1.getTank().didPlayerLose()){
                whoWon = "2";
            } else {
                whoWon = "1";
            }
            
            //String lives = "Lives left: " + this.getLife();
            String winner = "Player: " + whoWon + " wins!";
            
            g.drawString(winner, 300, 300 );
            
        }
        
        
    }

    public static void main(String args[]) {   
        final GameWorld demo = new GameWorld();
        demo.init();
       
        JFrame frame = new JFrame("Tank Game");   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){});
        frame.add(demo);
        frame.setSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setVisible(true);
        demo.start();
    }
}


