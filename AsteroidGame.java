import java.io.*;
import javax.swing.*;
import java.util.*;
import java.util.*;
/**
 *AsteroidGame is the class that is responsible for creating a new game of asteroids and keeping track of the player’s lives and score, and level.
 * 
 * @author Dexter Antonio
 * @version May 10, 2013
 */
public class AsteroidGame
{

    private int lives; //private instance variables that keep track of the players score
    private int score;
    private int level; 
    private GameFrame animator; //the frame that will animate a GameWindow
    private GameWindow window; //the GameWindow which will be animated
    /**
     * AsteroidGame Constructor
     */
    public AsteroidGame()
    {
        GameWindow window = new GameWindow(this);  //creates a new game window and passes in this classes reference variable as a parameter 
        animator = new GameFrame(window);  //creates a new animator and passes in window as a paramter 
        lives = 3; //gives the player three lives to start out
        score = 0; //sets the score to zero 
        level = 1; //sets the level to one. 
    }

    /**
     * Method resetGame
     * used to reset the game after the player loses the game and wants to play another game of asteroids. 
     */
    public void resetGame(){
        lives = 3; 
        score = 0; //score is set to zero 
        level = 1; 
        animator.reset(); 
    }

    /**
     *getScore, A getter method  for the getScore. 
     *
     * @return The current score 
     */
    public int getScore(){
        return score; 
    }

    /**
     *Method nextlevel resets the animator and increases the level by one. 
     *
     */
    public void nextLevel(){
        level = level + 1; 
        animator.reset(); 
    }

    /**
     * Method gameOverDiolog 
     * Displays a diolog box asking the user if they would like to play another game of asteroids. If the yes option is pressed then the game is reset. If the no option
     * is clicked then the game remains stopped. 
     */
    public void gameOverDiolog(){
        int response = JOptionPane.showConfirmDialog(null, "GAMEOVER \n  Your score was " + score + "\n" + "Would you like to play again?" , "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.YES_OPTION){
            resetGame(); 
        }

    }

    /**
     * getter for the private instance variable level 
     *
     * @return level 
     */
    public int getLevel(){
        return level; 
    }

    /**
     * Method getLives
     *
     * @return The number of lives that the player has remaining. 
     */
    public int getLives(){
        return lives; 
    }

    /**
     * Method addScore adds an interger to the current score
     *
     * @param the amount of points that will be added to the current score
     */
    public void addScore(int newPoints){
        score = score + newPoints; 
    }

    /**
     * Method lifeLost casues the player to lose one life. If no lives are remaining them the game is over.
     *
     */
    public void lifeLost(){
        lives = lives -1; //number of lives go down by one
        
        if(lives <= 0){ //if the amount of lives is equal or less than zero then the animator.gameOver method and the gameoverdiolog method are exicuted
            animator.gameOver(); 
            gameOverDiolog(); 
        }

    }
}
