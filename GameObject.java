import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
import javax.swing.*; 
/**
 * An abstract object for all the extended by all the objects that are drawn on the window.
 * 
 * @author Dexter Antonio
 * @version May 12, 2013
 */
public  abstract class GameObject extends JPanel  
{
    protected boolean alive;
    protected int locationX;
    protected int locationY;
    protected double speedX;
    protected double speedY;
    protected double diameter;
    protected double trajectoryAngle;
    /**
     * Method torus is the method that causes the screens ends to be glued together so that if a asteroid goes off the right 
     * side of the screen it comes back on the left. If a spaceship travels through the top of the screen it comes back from the bottom of the screen
     * calling this method every time that one of the objects is drawn allows for that functionality. 
     */
    public void torus(){

        if(locationX > GameFrame.FRAME_LENGTH){
            locationX = 0; 
            locationY = GameFrame.FRAME_HEIGHT-locationY; 

        }

        if(locationX < 0){
            locationX = GameFrame.FRAME_LENGTH;
            locationY = GameFrame.FRAME_HEIGHT-locationY; 

        }
        if(locationY > GameFrame.FRAME_HEIGHT){
            locationY = 0; 
            locationX = GameFrame.FRAME_HEIGHT-locationX; 

        }
        if(locationY < 0){
            locationY = GameFrame.FRAME_HEIGHT; 
            locationX = GameFrame.FRAME_HEIGHT-locationX; 
        }
    }
    //getters and setters 
    /**
     * Method getX
     *
     * @return the x location of the gameObject 
     */
    public int getX(){
        return locationX;
    }

    /**
     * Method setX
     *
     * @param the x position that the gameObject will be set to 
     */
    public void setX(int x){
        locationX = x; 
    }

    /**
     * Method getY
     *
     * @return the Y location of the gameObject
     */
    public int getY(){
        return locationY;
    }

    /**
     * Method setY
     *
     * @param the y position that the gameObject will be set to
     */
    public void setY(int y){
        locationY = y; 
    }

    /**
     * Method getDiameter
     *
     * @return returns the diamiter of the gameObject. If gameObject is not a circle then it returns a the diameter of a circle that the game
     * object aproximently fits into. 
     * 
     */
    public double getDiameter(){
        return diameter; 
    }

    /**
     * Method getTrajectoryAngle 
     *
     * @return returns the trajetory angle of the gameObject
     */
    public double getTrajectoryAngle(){
        return trajectoryAngle; 
    }

    /**
     * Method isAlive
     *
     * @return returns the boolean alive, which is true if the gameObject is alive and false if the gameObject is dead
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * Method setAlive
     *
     * @param sets the boolean variable alive to either true or false
     */
    public void setAlive(boolean lifeStatus){
        alive = lifeStatus; 
    }

    /**
     * Method getSpeedX 
     *
     * @return returns the x speed of the gameObject
     */
    public double getSpeedX(){
        return speedX;
    }

    /**
     * Method setSpeedX
     *
     * @param the value that xSpeed will be set too 
     */
    public void setSpeedX(double xSpeed){
        this.speedX = xSpeed;
    }

    /**
     * Method getSpeedY
     *
     * @return returns the y speed of the game object
     */
    public double getSpeedY(){
        return speedY;
    }

    /**
     * Method setSpeedY
     *
     * @param the value that xSpeed will be set too 
     */
    public void setSpeedY(double ySpeed){
        this.speedY = ySpeed;
    }

    /**
     * Method checkCollision determines if given gameObjects have colided with this gameObject 
     *
     * @param other the other object to be checked to see if it has colided with this gameObject
     * @return a boolean indicating if the gameObject given as a paramiter colided with this gameObject
     */
    public boolean checkCollision(GameObject other){
        //code taken from lab F
        
        double distance = Math.sqrt(
                (this.locationX-other.getX())*(this.locationX-other.getX()) +
                (this.locationY-other.getY())*(this.locationY-other.getY())); //pythagorean theorem to determine distance between
                //the two objects
       
        boolean temp = distance < (this.diameter+other.getDiameter()); //touching if distance is less than sum of the diameters

        return temp; 
        //will return true if the distance is less than the sum of the diameters meaning that they are touching. 
    }

    /**
     * Method collision skelliton method for collision with another gameObject
     *
     * @param other the gameObject that the other object has colided with
     */
    public void  collision(GameObject other){
        
    }

    //abstract methods 
    public abstract void draw(Graphics g); 
    
    public abstract void move(); 
}
