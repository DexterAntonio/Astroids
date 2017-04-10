import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; 
import java.util.Random.*; 
/**
 * GameWindow is the window that is being animated. It holds all of the graphics objects in an array and draws them on the window. 
 * 
 *  @author Dexter Antonio
 * @version May 12, 2013
 */
public class GameWindow extends JPanel
{

    ArrayList<GameObject> allGameObjects; //an array of all of the gameObjects which will be drawn on the screen
    ArrayList<GameObject> newGameObjects; //an array of all of the gameObjects which will be added  to allGameObjects
    int centerX; //the X center of the window 
    int centerY; //the Y center of the window 
    //timer for how many miliseconds the current level will be displayed.
    int levelNameTimer; 

    //the host AsteroidGame that keeps track of the score, lives and level 
    private AsteroidGame gameStatus;

    //true only when the player has lost the game 
    boolean gameOver; 

    //Playerspaceship variables   
    PlayerSpaceship spaceship;
    boolean rotateLeft; 
    boolean rotateRight;
    boolean thrust; 

    /**
     * GameWindow Constructor
     *
     * @param the AsteroidGame class that holds the score. 
     */
    public GameWindow(AsteroidGame gameStatus)
    {
        this.gameStatus = gameStatus; //instance variable gameStatus is set to the given parameter gameStatus

        centerX = GameFrame.FRAME_LENGTH/2; //centerX set to half of the screen length
        centerY = GameFrame.FRAME_HEIGHT/2; //centerY set to half of the screen width 

        //for keyboard input 
        Keyboard keyboard = new Keyboard(this); 
        addKeyListener(keyboard); 
        setFocusable(true);
        //intitializes the arraylists
        newGameObjects = new ArrayList<GameObject>();
        allGameObjects = new ArrayList<GameObject>(); 
        //calls helper method 
        setupGame(); 

    }

    /**
     * Method setupGame
     *
     */
    public void setupGame(){

        //sets spaceship controls to false 
        rotateLeft = false; 
        rotateRight = false;
        thrust = false; 

        gameOver = false; 
        //sets miliseconds that the level number will be displaced on screen 
        levelNameTimer = 50; 
        //declares spaceship
        spaceship = new PlayerSpaceship(centerX,centerY); 
        //adds spaceship to GameObjects arraylist. 
        allGameObjects.add(spaceship);
        //calls method that setups Asteroids     
        setupAsteroids();
    }

    /**
     * setupAsteroids is a helper method that is responcible for setting up all of the asteroids on the screen.
     * 
     * what it does is draw all of the asteroids the screen, the number of asteroids being equal to one plus the level number. 
     * the location each asteroids is on the boarder of circleRadius. 
     * using polar corordinates the asteroids are equally spaced on that circle.
     * the asteroids are then shifted so that they are centered around the center of the screen. 
     * 
     *
     */
    private void setupAsteroids(){
        int smallestDimention; 
        int AsteroidRadius;
        int circileRadius; 
        int numAsteroids; 
        AsteroidRadius = 25; 

        if(GameFrame.FRAME_LENGTH< GameFrame.FRAME_HEIGHT){ //finds the smallest window dimention 
            smallestDimention = GameFrame.FRAME_LENGTH;
        }
        else
        {
            smallestDimention = GameFrame.FRAME_HEIGHT;
        }

        circileRadius = smallestDimention/2 - AsteroidRadius*2; //asteroids will be created on imaginary circle are at least one asteroid lenght away from sides

        numAsteroids =  gameStatus.getLevel()+1; //asteroids equal to level number plus one

        double angleAppart = 2*Math.PI/numAsteroids; // 2Pi divided by the amount of segments imaginary circle will be divided into 

        for(int i = 0; i < numAsteroids; i++){
            //create polar coordinates 
            double r = circileRadius; 
            double theta = angleAppart*i; //first angle is always zero; 

            //converts to cartesian coordinates
            int x = (int) (r * Math.cos(theta)); 
            int y = (int) (r * Math.sin(theta)); 
            //shifts corordinates so that circle is around center of window 
            x = x + centerX; 
            y = y + centerY; 
            //draws asteroid at corordinates with a speed equal to 3 
            int speedX = 3; 
            int speedY  = 3;
            //creates a new asteroid 
            Asteroid asteroid = new Asteroid(x, y, speedX, speedY, AsteroidRadius * 2 ,this);
            //adds that asteroid to the allGameObjects arraylist 
            allGameObjects.add(asteroid); 
        }
    }

    /**
     * Method resetGame
     *
     *resets the instance variables to their defaults so that the game can be played again
     */
    public void resetGame(){
        gameOver = false; 
        spaceship.setAlive(true); 
        allGameObjects.clear();
        newGameObjects.clear();
        allGameObjects.add(spaceship);
        spaceship.setX(centerX); 
        spaceship.setY(centerY); 
        setupAsteroids();
        levelNameTimer = 50; 
    }

    /**
     * Method checkNextLevel is a helper method that determines if their are any asteroids in allGameObjects. If none are in allGameObjects then the player has
     * beaten the level and then he moves on to the next level
     *
     */
    private void checkNextLevel(){
        if(!(containsAsteroids(allGameObjects)) ) { //if allGameObjects contains no asteroids 
            nextLevel(); 
        }
    }

    /**
     * Method containsAsteroids is a helper method that takes an arraylist and determines if it contains asteroids 
     *
     * @param toTest A arraylist to be tested to see if it contains any Asteroid objects
     * @return returns true or false depending on if the arraylist contains asteroids 
     */
    private boolean containsAsteroids(ArrayList<GameObject> toTest){
        for(GameObject item: toTest){
            if(item instanceof Asteroid){
                return true;  
            }
        }
        return false; 
    }

    /**
     * Method checkCollision checks to see if any of the objects in allGameObjects have colided with eachother. If they have then the collision method is 
     * called for the gameObject
     * 
     */
    private void checkCollision(){
        for(GameObject item1: allGameObjects){
            for(GameObject item2: allGameObjects){
                if(item1.checkCollision(item2)&&  !(item1.equals(item2)) ){  //if they have collided and are not the same object    
                    item1.collision(item2);
                }
            }
        }
    }

    /**
     * Method removeDeadObjects checks to see if all of the objects in allGameObjects are alive. If they are not alive then they are removed from allGameObjects
     *
     */
    private void removeDeadObjects(){
        ArrayList<GameObject> tempGameObjects = new ArrayList<GameObject>();  //creates a new arraylist to hold GameObjects
        for(GameObject item: allGameObjects){
            if(item.isAlive()){ //if a gameObject is alive then it is added to tempGameObjects 
                tempGameObjects.add(item); 
            }
            //if the item is alive then it isn't added to tempGameObjects 
        }

        allGameObjects.clear(); //clears allGameObjects to prevent referenceless arrays from hogging up memmory (might make no difference) 
        allGameObjects = tempGameObjects;  //allGameObjects is set to tempGameObjects so all allGameObjects nolonger has any dead items in it. 

    }

    /**
     * Method addNewGameObjects adds all the items in newGameObjects to allGameObjects
     *
     */
    private void addNewGameObjects(){
        for(GameObject item: newGameObjects){
            allGameObjects.add(item); 
        }
        newGameObjects.clear(); 
    }

    /**
     * Method moveGameObjects moves all of the items in allGameObjects 
     *
     */
    private void moveGameObjects(){
        for(GameObject item: allGameObjects){
            item.move(); 
        }
    }

    /**
     * Method paintComponent is the method whichs draws everything on the window. It is what is called repeatly in the aminator class. 
     *
     * @param g a graphics object which is where everything will be drawn
     */
    public void paintComponent(Graphics g)
    {
        addNewGameObjects(); //first checks to see if there are any new objects (such as new asteroids) that should be added to the allGameObjects array. 
        checkNextLevel(); //must go after addNewGameObjects so that an asteroid that the children of a split asteroid are counted 
        moveSpaceship();  //calls helper method which moves the spaceship 
        moveGameObjects(); //calls the helper method that moves the other game objects 
        erraseWindow(g); 
        drawGameObjects(g); 

        checkCollision(); 
        removeDeadObjects(); 
        drawHud(g); 

    }

    //helper draw methods 

    /**
     * Method drawHud draws the heads up display on the window
     *
     * @param g is the graphics object on which the stuff will be drawn 
     */
    private void drawHud(Graphics g){
        int hudX = 20;  //upper bounds of the HUD (heads up display) 
        int hudY = 20; 
        int scoreHeight = 10; //the height of the score to be printed on the gameWindow 
        int score = gameStatus.getScore(); 

        String stringScore = "SCORE: " + Integer.toString(score); //string to be printed on gameWindow
        g.drawString(stringScore, hudX, hudY); //line which prints the string 

        if(gameOver){//if gameOver is true then 
            int x = centerX - 25; // this is to center GameOver in the middle of the screen. 

            g.drawString("GAME OVER", x, centerY); //this string is printed in the center of the screen 
        }

        if(levelNameTimer >= 0){
            int level = gameStatus.getLevel();
            int x = centerX -20; // so the text is centered (instead of being drawn starting at the center point) 
            int y = centerY - 200; // so the text is not covering the spaceship. 
            String stringLevel = "LEVEL: " + Integer.toString(level);
            g.drawString( stringLevel , x, y);
            levelNameTimer = levelNameTimer - 1; 
        }

        //the code below draws a series of triangles representing the number of lives that the player has remaining. 
        int lives = gameStatus.getLives(); 
        for(int i = 0; i <lives; i++){

            //everytime the for loop is exicuted one triangle is drawn 

            int triangleHeight = 20; //height of the triangles representing lives
            int triWidth = 5; //acually half width of triangles that are to be drawn 
            int bottomTriangleY = hudY+scoreHeight+2 + triangleHeight; //the y location of the bottom of the triangle. the 2 is to add a space between score and lives

            int[] xPoints = {hudX + triWidth +i*20, hudX +i*20  ,hudX + triWidth*2 +i*20 }; //array of points required to draw polygon  
            int[] yPoints = {hudY+scoreHeight+2, bottomTriangleY, bottomTriangleY};  
            g.drawPolygon(xPoints,yPoints,3); 
        }

    }

    /**
     * Method erraseWindow, errases the window by drawing a black rectangle the size of the window. 
     *
     * @param g a graphics object
     */
    public void erraseWindow(Graphics g){
        g.setColor(Color.BLACK); 
        g.fillRect(0,0,GameFrame.FRAME_LENGTH,GameFrame.FRAME_HEIGHT); //draw the black rectangle over window, errasing it. 
    }

    /**
     * Method drawGameObjects goes through all of the gameObjects in allGameObjects and draws them
     *
     * @param g, the graphics object where they will be drawn 
     */
    public void drawGameObjects(Graphics g){

        for(GameObject item: allGameObjects){
            item.draw(g); 
        }

    }

    /**
     * Method gameOver sets gameOver to true
     *
     */
    public void gameOver(){
        gameOver = true;
    }

    /**
     * Method moveSpaceship moves the spaceship if certain boolean variables are true. 
     * if rotateRight is true then the spaceship's rotateRight() method is called 
     * if rotateLeft is true then the spaceship's rotateLeft() method is called
     * if thrust is true then the spaceship's thrust is called. 
     * these boolean variables are set by keyboard input. 
     */
    private void moveSpaceship(){
        //code for moving spaceship 
        if(rotateRight){
            spaceship.rotateRight(); 
        }

        if(rotateLeft){
            spaceship.rotateLeft(); 
        }

        if(thrust){
            spaceship.thrust();
        }

    }

    /**
     * Method getSpaceship
     *
     * @return returns the spaceship reverence variable 
     */
    public PlayerSpaceship getSpaceship(){
        return spaceship; 
    }

    /**
     * Method setRotateRight sets the boolean variable rotateRight either true or false
     *
     * @param a boolean rotate
     */
    public void setRotateRight(boolean rotate){
        this.rotateRight = rotate; 
    }

    /**
     * Method setRotateLeft sets the boolean variable rotateLeft either true or false
     *
     * @param a boolean rotate
     */
    public void setRotateLeft(boolean rotate){
        this.rotateLeft = rotate; 
    }

    /**
     * Method setThrust sets the boolean variable thrust either true or false
     *
     * @param a boolean thrust
     */
    public void setThrust(boolean thrust){
        this.thrust = thrust; 
    }

    /**
     * Method respawn recreates the spaceship in the center of the screen when the method is called only if spaceship is dead.   
     *
     */
    public void respawn(){
        if(!(spaceship == null) &&!(spaceship.isAlive())){
            gameStatus.lifeLost(); //player loses a life 
            spaceship.setX(centerX);
            spaceship.setY(centerY); 
            spaceship.setAlive(true); 
            allGameObjects.add(spaceship);  //needed because spaceship removed from allGameObjects when it is dead
        }
    }

    /**
     * Method transportSpaceship transports the spaceship to a random location on the screen. 
     *
     */
    public void transportSpaceship(){
        Random rand = new Random(); //new random number geneoratior
        int randomX = rand.nextInt(GameFrame.FRAME_LENGTH);  
        int randomY = rand.nextInt( GameFrame.FRAME_HEIGHT/2);
        spaceship.setX(randomX);
        spaceship.setY(randomY); 
    }

    /**
     * Method fireMissile, fires a missile using the method in spaceship. 
     *
     */
    public void fireMissile(){
        if(spaceship.isAlive()){
            spaceship.fireMissile(this);
        }
    }

    /**
     * Method addObject takes a GameObject and adds it to newGameObjects which will add the given object to allGameObjects next time the window is drawn. 
     *
     * @param newObject A parameter
     */
    public void addObject(GameObject newObject){
        newGameObjects.add(newObject); 
    }

    /**
     * Method pointScored adds 20 points to the score everysingle time it is called 
     *
     */
    public void pointScored()
    {
        gameStatus.addScore(20); 
    }

    /**
     * Method nextLevel calls the gameStatus.nextLevel() method. 
     *
     */
    public void nextLevel(){ 
        gameStatus.nextLevel(); 
    }
}
