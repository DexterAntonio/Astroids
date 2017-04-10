import java.lang.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
/**
 * A missile class that represents the missile in the game 
 * 
 *  @author Dexter Antonio
 * @version May 12, 2013)
 */
public class Missile  extends GameObject
{
    public static final double  MISSILE_VEOLOCITY_MAGNITIDE = 10; //the static variable of hte magitude of the missile velocity
      /**
       * Missile Constructor
       *
       * @param angle 
       * @param locationX 
       * @param locationY 
       */
      public Missile(double angle, int locationX, int locationY){
        alive = true; 
        this.locationX = locationX;
        this.locationY = locationY;
        this.trajectoryAngle = angle + Math.PI;
        speedX = MISSILE_VEOLOCITY_MAGNITIDE*Math.cos(trajectoryAngle); //vector math used to find speedX and speedY
        speedY =  MISSILE_VEOLOCITY_MAGNITIDE*Math.sin(trajectoryAngle); 
        diameter = 4; //length of the missile 
    }

    /**
     * Method draw
     *
     * @param g the graphics object where the missile will be drawn 
     */
    public  void draw(Graphics g){
        killOffScreen(); //calls helper method that sets the missile to alive = false when it is out of the window

        g.setColor(Color.WHITE); 

        int newXpos =  getX() + (int) (MISSILE_VEOLOCITY_MAGNITIDE *Math.cos(trajectoryAngle)); 
        int newYpos =  getY() + (int) (MISSILE_VEOLOCITY_MAGNITIDE* Math.sin(trajectoryAngle));

        g.drawLine(getX(), getY(),newXpos, newYpos); //draws the missile according to its next x position and y position 
    }

    /**
     * Method killOffScreen sets alive to false the missile is out of the bounds of the screen. 
     *
     */
    private  void killOffScreen(){
        // if statements to determine if it is off the screen 
        if(locationX< 0 || locationY<0){
            alive = false; 
        }
        if(locationX >GameFrame.FRAME_LENGTH || locationY > GameFrame.FRAME_HEIGHT){
            alive = false; 
        }
    }

    /**
     * Method move moves the missile 
     *
     */
    public void move(){
        //moves the missile  
        locationX = locationX + (int) speedX; 
        locationY = locationY + (int) speedY;
    }

}
