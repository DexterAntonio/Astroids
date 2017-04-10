import java.io.*;
import javax.swing.*;
import java.util.*;
/**
 * Asks the user if they would like to play a game of Asteroids. If the user presses yes then a new instance of AsteroidGame.
 * 
 * @author Dexter Antonio
 * @version May 10, 2013
 */
public class AstroidGameTester
{
    public static void main(String[] args)
    {
        //dialog box asking the user if they would like to play asteroids. 
        int response = JOptionPane.showConfirmDialog(null, "Would You like to play Asteroids? \n" , "ASTEROIDS", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if(response == JOptionPane.YES_OPTION){ //if the user presses the yes button
            //Diolog box explaining the controls of the game 
            JOptionPane.showMessageDialog(null, "Press the left or right arrow keys to rotate the spaceship." + 
                "\nPress the forward arrow key to thrust. Press the spacebar to fire. \n" + "Press A to transport the spaceship and R to reset the spacehip", "Controls", JOptionPane.INFORMATION_MESSAGE);

            AsteroidGame game = new AsteroidGame(); 
        }

    }
}