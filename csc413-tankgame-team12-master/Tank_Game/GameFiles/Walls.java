/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank_Game.GameFiles;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author jrettinghouse
 */
public class Walls {
     BufferedImage wallTexture, wallTexture2;
     BufferedReader r;
     private int [][] walls;
     private int mapWidth, mapHeight;

    public Walls(String path) {

        try {
            wallTexture = ImageIO.read(Walls.class.getResource("/Tank_Wars_Resourses/Wall1.gif")); // load image
            wallTexture2 = ImageIO.read(Walls.class.getResource("/Tank_Wars_Resourses/Wall2.gif")); // load image
            r = new BufferedReader(new FileReader(path)); // load txt file what will create walls 
            mapWidth = Integer.parseInt(r.readLine()); // first line read from walls.txt is width
            mapHeight = Integer.parseInt(r.readLine()); // second line read is height
            walls = new int[mapHeight][mapWidth];
            System.out.println(mapHeight);
            System.out.println(mapWidth);
             
            for (int row = 0; row < mapHeight; row++){
                String line = r.readLine();
                String[] tokens = line.split("\\s+"); // delimits by whitespace " "
                for (int col = 0; col < mapWidth; col++){
                    // x,y coordinate for the walls that will be drawn will be represented by 1's adn 2's
                    walls[row][col] = Integer.parseInt(tokens[col]);     
                }
                 
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       
    }
    
    public void draw(Graphics2D g) {
        for (int x = 0; x < mapWidth; x++ ) {
            for (int y = 0; y < mapHeight; y++) {
                int wallHere = walls[x][y];
                
                if (wallHere == 1) {
                      g.drawImage(wallTexture, y*wallTexture.getHeight(), x*wallTexture.getWidth(), null);
                } 
                
                if (wallHere == 2) {
                      g.drawImage(wallTexture2, y*wallTexture.getHeight(), x*wallTexture.getWidth(), null);
                } 
            }
        } 
    }
    
    public void breakWallAt (int x, int y) {
        walls[x][y] = 0;
    }
    
    public void respawnWallAt (int x, int y) {
        walls[x][y] = 2;
    }
    
    public int[][] getWalls(){
        return walls;
    }
  
    public int getPictureWidth(){
        return wallTexture.getWidth();
    }
    
    public int getPictureHieght(){
        return wallTexture.getHeight();
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    
}
