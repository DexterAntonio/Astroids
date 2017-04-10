import java.lang.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
import java.lang.*; 

/**
 * Write a description of class PlayerSpaceship here.
 * 
 * @author Dexter Antonio
 * @version 5/12/13
 */
public class PlayerSpaceship extends Spaceship
{
    int length ;
    double shipAngle;     
    private static final double THUSTER_SPEED = 2; //determine the speed increase by the thruster
    private static final double SLOWDOWN_SPEED = .05; //determines the speed at which the spaceship slows down

    /**
     * PlayerSpaceship Constructor
     *
     * @param x is the xlocation where the spaceship will be drawn
     * @param y is the ylocation where the spacehip will be drawn
     */
    public PlayerSpaceship(int x, int y){
        alive = true; //sets spaceship alive to true
        locationX = x; 
        locationY = y; 
        angle = Math.PI; //sets the angle that the spaceship right side has to the x axis. 
        speedX = 0; //3*Math.cos(angle); //sets the players speed 
        speedY= 0; //sets players starting speed to zero 
        shipAngle = Math.PI/6; //sets the angle that the two equal length sides are appart from eachother
        length = 40; //sets the length of the sides 
        diameter = 10; //sets the diamiter of the hitbox for the spaceship. The circle does not do a good job as a hitbox for the thin triangle, so 10 is
        //a somewhat arbitary number. Sometimes asteroids will pass through the spaceship drawing but not kill it. That is because of this bad aproximation. 
    }

    /**
     * setter for the angle variable 
     *
     * @param new angle 
     */
    public void setAngle(double newAngle){
        angle = newAngle;
    }

    /**
     * Method rotateRight rotes the spaceship Pi/16 radiants clockwise 
     *
     */
    public void rotateRight(){
        angle = angle + Math.PI/16; 
        trajectoryAngle = shipAngle/2 + angle;
        fireAngle = (2*Math.PI)+(trajectoryAngle); 
    }

    /**
     * Method rotateLeft  rotes the spaceship Pi/16 radiants counter clockwise 
     *
     */
    public void rotateLeft(){
        angle = angle - Math.PI/16; 
        trajectoryAngle = shipAngle/2 + angle;
        fireAngle = (2*Math.PI)+(trajectoryAngle); 
    }

    /**
     * Method thrust moves the spaceship forward. 
     *
     */
    public void thrust(){
        speedX = speedX + -1*THUSTER_SPEED*Math.cos(angle); //changes coordinates system 
        speedY= speedY + -1* THUSTER_SPEED*Math.sin(angle);
    }

    /**
     * Method getAngle returns the angle of the spaceship is oriented 
     *
     * @return returns the angle of the spaceship is oriented
     */
    public double getAngle(){
        return angle;
    }

    public void move(){
        //these if statements slow down the spaceship 
        if(speedX > 0){   // if Xspeed is positive 
            speedX = speedX - speedX*SLOWDOWN_SPEED; //slowdown speed is subtracted from speedX 
        }

        if(speedY > 0){ // if speedY is positive 
            speedY = speedY - speedY*SLOWDOWN_SPEED; //slowdown speed is subtracted from speedY
        }

        if(speedX < 0){ //if speedX is negitive 
            speedX = speedX  + Math.abs(speedX)*SLOWDOWN_SPEED; //adds speed which slows down the spaceship. 
        }

        if(speedY < 0){ //if speedY is negitive 
            speedY = speedY  + Math.abs(speedY)*SLOWDOWN_SPEED; //adds slowadown speed which slows down the spaceship
        }
        //if speed == 0 do nothing 
        //moves spaceship according to speed 
        locationX = locationX + (int) speedX; 
        locationY = locationY + (int) speedY;
    }

    /**
     * Method draw 
     *
     * @param  the graphics object where the spaceship will be drawn 
     */
    public void draw(Graphics g){
        //surprisingly this was the most problimatic code in the game. It is very difficult to find the x,y coordinates of all three of 
        //triangles sides when rotating it around a point. It is not as difficult to find the x,y coordinates of two points equal distance
        //from another point. That methdo was used for this project.    

        torus(); //method defined in gameObject

        g.setColor(Color.WHITE); 
        int baseSize = length; 

        //uses poloar corordinates to find location of edges of spaceship. 

        double x = locationX;
        double y = locationY; 

        double x1 = x+ (baseSize)*Math.cos(angle); 
        double y1 = y+ (baseSize)*Math.sin(angle);
        double x2 = x+ (baseSize)*Math.cos(angle+shipAngle);
        double y2 = y+ (baseSize)*Math.sin(angle+shipAngle);
        
        double x3 = x;
        double y3 = y;
        
        int[] xPoints = { (int) x1, (int) x2, (int) x3}; 
        int[] yPoints = {(int)  y1 , (int) y2, (int) y3 };
        g.drawPolygon(xPoints,yPoints,3); 
    }

    /**
     * Method collision determines what the object will do when it colides with certain object
     *
     * @param other the object that the spaceship colided with
     */
    public void collision(GameObject other){
        if(other instanceof Asteroid){
            setAlive(false); 
        }
    }
}

