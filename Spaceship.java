import javax.swing.*;
/**
 * Spaceship class extends gameObject and had the code to fire missiles. 
 * 
 * @author Dexter Antonio  
 * @version May 12, 2013 
 */
public abstract class Spaceship extends GameObject 
{
    protected double angle; 
    protected double fireAngle; 
    /**
     * Method fireMissile creates a new missile object with the fireAngle equal to the fireAngle of the 
     * spaceship. The created missile is added to the window that the spaceship is being drawn in. 
     * The missile is created at the same x and y corordinates as the spaceship. 
     *
     * @param window A parameter
     */
    public  void fireMissile(GameWindow window){
        Missile missile = new Missile(fireAngle, getX(), getY()); 
        window.addObject(missile); 
    }
    
}