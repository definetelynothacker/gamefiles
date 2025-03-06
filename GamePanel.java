import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

/**
 * A component that displays all the game entities
 */

public class GamePanel extends JPanel implements Runnable{

   private final int NUM_ASTEROIDS = 5;

   public static Asteroid[] asteroids;
   public static Spaceship spaceship;

   public static Ammo ammoPkg;
   public static Health healthPkg;

   private CopyOnWriteArrayList<LaserBeam> laserBeams;

   private boolean isRunning;

   private final Image backgroundImage;
   private Thread gameThread;
   //private final Time gameTimer;
   private final Random random;

   public GamePanel(){

      spaceship = null;
      asteroids = null;

      healthPkg = null;
      ammoPkg = null;

      laserBeams = null;

      isRunning = false;

      random = new Random();

      backgroundImage = ImageManager.loadImage("bg2.jpg");
   }

   public void createGameEntities(){
      int panelWidth = this.getWidth();

      spaceship = new Spaceship(this, 65, 75);
      asteroids = new Asteroid[NUM_ASTEROIDS];
      ammoPkg = new Ammo(this, random.nextInt(panelWidth), 10, spaceship);
      healthPkg = new Health(this, random.nextInt(panelWidth), 10, spaceship);

      laserBeams = new CopyOnWriteArrayList<>();

      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i] = new Asteroid(this, random.nextInt(panelWidth), 10, spaceship);
      }
   }

   public void drawGameEntities(){
      if (spaceship != null)
         spaceship.draw();
   }
   public void updateGameEntities(int direction){
      if(spaceship == null)
         return;
   
      spaceship.erase();
      spaceship.move(direction);
   }
   public boolean entityCollision(int x1, int y1, int x2, int y2, int width2, int height2){
      if(x1 >= x2 && x1 <= (x2+width2))
         return true;
      return y1 >= y2 && y1 <= (y2+height2);
   }

   //Asteroid
   //
   //
   public void dropAsteroid(){
      gameThread = new Thread(this);
      gameThread.start();
   }
   public void playAgain(){
      //if()
      ScoringPanel.decreaseLivesTF();
   }
   public void createLaser(){
      LaserBeam newLaserBeam = new LaserBeam(this, spaceship.getXCord()+7, spaceship.getYCord()+25);
      laserBeams.add(newLaserBeam);
   }
   public void moveRenderLaser(){
      for(LaserBeam laserBeam: laserBeams){
         if(laserBeam!=null && laserBeam.canMove){
            laserBeam.draw();
            laserBeam.move();
            laserBeam.erase();
         }
         else{
            this.laserBeams.remove(laserBeam);
            laserBeam = null;
         }
      }
   }
   public void gameUpdate(){
      ammoPkg.move();
      healthPkg.move();
      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i].move();
      }
   }
   public void gameRender(){
      Graphics g = getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(backgroundImage, 0, 0, null);

      if(spaceship != null)
         spaceship.draw();

      if(ammoPkg != null)
         ammoPkg.draw();

      if(healthPkg != null)
         healthPkg.draw();

      if(asteroids != null){
         for(int i = 0; i<NUM_ASTEROIDS; i++)
            asteroids[i].draw();
      }
   }
   public boolean isOnAsteroid(int x, int y){
      for(int i = 0; i<NUM_ASTEROIDS; i++){
         return asteroids[i].isOnAsteroid(x, y);
      }
      return false;
   }
   //
   //

   //Spaceship
   //
   //
   public void updateSpaceship(int direction){
      if(spaceship!=null)
         spaceship.move(direction);
   }

   public boolean isOnSpaceShip(int x, int y){
      return spaceship.isOnSpaceShip(x, y);
   }
   //
   //

   @Override
   public void run(){
      try{
         isRunning = true;
         while(isRunning){
            gameUpdate();
            gameRender();
            moveRenderLaser();

            if(GameWindow.spacePressed && Spaceship.amtLasers>0 && spaceship.canShoot()){
               createLaser();
               AmmoPanel.decreaseAmmo();
               GameWindow.spacePressed = false;
            }
            Thread.sleep(50);
         }
      }
      catch(InterruptedException e){}
   }

}