import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
import java.util.Random.*; 
/**
 * An extention of gameObject, which represents an asteroid in the game. 
 * 
 * @author Dexter Antonio
 * @version 5/12/13 
 */
public class Asteroid extends GameObject
{
    public final static double ASTEROID_SPEED_INCREASE = 1;  //mantitude that the speed increases when asteroid is split
    boolean tempBoolean; 
    GameWindow window; 
       public Asteroid(int x, int y, double speedX,double speedY, double diameter,GameWindow window){
        alive = true; 
        locationX = x;
        locationY = y;
        this.speedX = speedX; 
        this.speedY= speedY; 
        this.diameter = diameter; 
        this.window = window; 
    }

    public void  draw(Graphics g){
        torus(); 
        g.setColor(Color.WHITE); 
        
        //asteroid is an oval shape 
        g.fillOval(locationX, locationY, (int) diameter, (int) diameter);
        if(diameter< 10){ //if the asteroid has a diamiter less than 10 pixes then it is destroyed 
            alive = false; 
        }
    }

    /**
     * Method collision determines what happens when an object colides with an asteroid 
     *
     * @param other A parameter
     */
    public void collision(GameObject other){
        if (other instanceof Missile){ //if the asteroid has colided with a Missile then something happens 
            alive = false; //asteroid is no longer alive
            other.setAlive(false);  //the missile is no longer alive 
            window.pointScored(); //scores a point 

            
            double missileAngle = other.getTrajectoryAngle();  //the missile trajectory is used to calculate how the asteroid will break
            double splitAngle1 = missileAngle + Math.PI*(7/4); //one of the asteroids this the new trajectory
            double splitAngle2 = 2*Math.PI- missileAngle; //the other one has this trajectory 
            double newDiameter = diameter/2 ; // the new diamiter of the asteroid is half of the first one

            double newSpeedMagnitude= Math.sqrt(speedX*speedX+speedY*speedY)+ASTEROID_SPEED_INCREASE; //the magatude of the speed is equal
            //to sqrt(x^2+y^2) + asteroid speed increase. 
            

 
            double speed1X = newSpeedMagnitude * Math.cos(splitAngle1); //calculating speed based on vectors 
            double speed1Y = newSpeedMagnitude * Math.sin(splitAngle1); //caculating speed based no vectors 

            double speed2X = newSpeedMagnitude * Math.cos(splitAngle2); //caculating speed based on vectors
            double speed2Y = newSpeedMagnitude * Math.sin(splitAngle2); //caculating speed based on vectors

            Asteroid asteroid1 = new Asteroid(locationX, locationY,speed1X ,  speed1Y, newDiameter, window); //constructor for asteroid one
            Asteroid asteroid2 = new Asteroid(locationX, locationY, speed2X, speed2Y, newDiameter, window);   //constructor for asteroid two 

            window.addObject(asteroid1); //asteroid one added to allGameObjects
            window.addObject(asteroid2); //asteroid two added to allGameObjects
        }
        // if asteroid has colided with any other type of gamObject nothing happens. 

    }


    /**
     * method that moves the asteroid
     *
     */
    public void  move(){
        locationX = locationX + (int) speedX; 
        locationY = locationY + (int) speedY;
    }
}
