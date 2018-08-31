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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class Sound {
   private Clip bgMusic, bExplosionSound, sExplosionSound; // to loop the background music
   private AudioInputStream stream, sound1, sound2; // used to convert into a Clip to play
    
    //default 
   public  Sound() {
    }  
   
    public void playBGMusic() {
        try {
            stream = AudioSystem.getAudioInputStream(Sound.class.getResource("/Tank_Wars_Resourses/Music.wav"));
            bgMusic = AudioSystem.getClip();
            bgMusic.open(stream); // plays the music here 
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY); // loops if music is finished

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void bigExplosion() {

        try {
            sound1 = AudioSystem.getAudioInputStream(Sound.class.getResource("/Tank_Wars_Resourses/Explosion_large.wav"));
            bExplosionSound = AudioSystem.getClip();
            bExplosionSound.open(sound1); // plays the music here
            bExplosionSound.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void smallExplosion() {

        try {
            sound2 = AudioSystem.getAudioInputStream(Sound.class.getResource("/Tank_Wars_Resourses/Explosion_small.wav"));
            sExplosionSound = AudioSystem.getClip();
            sExplosionSound.open(sound2); // plays the music here
            sExplosionSound.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    

}

}
