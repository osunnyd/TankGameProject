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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyControl extends KeyAdapter{
    private GameEvents gameEvents;

    public KeyControl(){

    }

    public KeyControl(GameEvents anAction ){
        this.gameEvents = anAction;
    }

    public void keyPressed(KeyEvent event){
        gameEvents.setValue(event);
    }

    public void keyReleased(KeyEvent event){
        gameEvents.setValue(event);
    }

}
