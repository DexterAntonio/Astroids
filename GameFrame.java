import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Write a description of class GameFrame here.
 * 
 * @Author Dexter Antonio  
 * @version May 12, 2013 
 */
public class GameFrame implements ActionListener {
    public static final int FRAME_HEIGHT = 700; //the frame height 
    public static final int FRAME_LENGTH = 700; //the frame length  
    
    private AsteroidGame gameStatus; 
    private JFrame frame; //the actual frame we'll be showing
    private GameWindow court; //the window we'll be drawing
    private Timer animator; //a timer to control animation
    
    
    /**
     * Creates a new GameFrame object.
     */
    public GameFrame(GameWindow window)
    {        
        frame = new JFrame("Asteroids"); //make the JFrame 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        court = window; 
        
        court.setPreferredSize(new Dimension(GameFrame.FRAME_LENGTH,GameFrame.FRAME_HEIGHT));
        frame.getContentPane().add(court); //put the court in the frame

        animator = new Timer(30,this); //create the timer, with this object controlling it

        frame.pack(); //make everything the preferred size
        frame.setVisible(true); //show the frame

        startAnimation(); //start the animation!
    }

    /**
     * Method that controls the animation timer
     */
    public void actionPerformed(ActionEvent e)
    {
        court.repaint();
    }

    /**
     * Starts the animation timer
     */

    public void startAnimation()
    {
        animator.start();
    }
    
    /**
     * Method gameOver is exicuted when the player loses the game. It exicutes the court/windows gameOver method, and repaints the court one last time before 
     * stopping the animation. 
     *
     */
    public void gameOver(){
         court.gameOver(); 
         court.repaint();
         animator.stop();
    }
    
    /**
     * method reset. Resests the gameWindow object that this animator is animating and starts the animator 
     *
     */
    public void reset()
    {
        court.resetGame(); 
        animator.start();
    }

    
    
    /**
     * Stops the animation timer
     */
    public void pauseAnimation()
    {
        animator.stop();
    }

}

