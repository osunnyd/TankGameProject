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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Background extends JPanel {

    BufferedImage background;

    public Background() {

        try {
            background = ImageIO.read(Background.class.getResource("/Tank_Wars_Resourses/Background.bmp"));
        } catch (Exception e) {
     }
 }

    @Override
    public void paint(Graphics g) {
        int tileW = background.getWidth(this); // width of image  
        int tileH = background.getHeight(this); // height of image
        int NumberY = (int) ( 3000 / tileH); //hardcoded it -> getHeight and getWidth were not working in new context
        //intNumberY = (int) (getHeight() / tileH);
        int NumberX = (int) ( 3000 / tileW);
        //intNumberX = (int) (getWidth() / tileW);
        for (int i = 0; i <= NumberX; i++) {
            for (int j = 0; j <= NumberY; j++) {
                g.drawImage(background, i * tileW, j * tileH, null);
            }
        }
    }
}
