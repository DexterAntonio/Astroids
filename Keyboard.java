import javax.swing.*; 
import java.awt.*;
import java.awt.event.*; 
import java.awt.event.KeyEvent.*; 

/**
 * Takes keybord input and uses it to control spaceship in window which is passes as a parameter.  
 * 
 * @author Dexter Antonio
 * @version 5/01/13
 */
public class Keyboard implements KeyListener 
{
    GameWindow window; 

    public Keyboard(GameWindow window){
        this.window = window; 
    }

    public void	keyPressed(KeyEvent e){
        int key = e.getKeyCode(); 
        if(key == KeyEvent.VK_LEFT){
            window.setRotateLeft(true);
        }
        if(key == KeyEvent.VK_RIGHT){
            window.setRotateRight(true);
        }
        if(key == KeyEvent.VK_UP){
            window.setThrust(true);
        }
        if(key == KeyEvent.VK_SPACE){
            window.fireMissile();
        }

    }

    public void	keyReleased(KeyEvent e){
        int key = e.getKeyCode(); 
        if(key == KeyEvent.VK_LEFT){
            window.setRotateLeft(false);
        }
        if(key == KeyEvent.VK_RIGHT){
            window.setRotateRight(false);
        }
        if(key == KeyEvent.VK_UP){
            window.setThrust(false);
        }
        if(key == KeyEvent.VK_R){
            window.respawn(); 
        }
          if(key == KeyEvent.VK_A){
            window.transportSpaceship(); 
        }

    }

    public void	keyTyped(KeyEvent e){
       
    }
}
