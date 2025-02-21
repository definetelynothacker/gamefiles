import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import javax.swing.JPanel;

/**
 * A component that displays all the game entities
 */

public class GamePanel extends JPanel implements Runnable{

   private final int NUM_ASTEROIDS = 5;

   private Asteroid[] asteroids;
   //private ArrayList<LaserBeam> laserBeams;

   public Spaceship spaceship;

   private boolean asteroidDropped;
   private boolean laserShot;
   private boolean isRunning;

   private final Image backgroundImage;
   private Thread gameThread;
   private Random random;

   public GamePanel(){

      spaceship = null;
      asteroids = null;

      asteroidDropped = false;
      laserShot = false;
      isRunning = false;

      random = new Random();

      backgroundImage = ImageManager.loadImage("bg2.jpg");
   }

   public void createGameEntities(){
      spaceship = new Spaceship(this, 65, 75);
      asteroids = new Asteroid[NUM_ASTEROIDS];

      //laserBeams = new ArrayList<>();

      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i] = new Asteroid(this, random.nextInt(this.getWidth()), 10, spaceship);
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
      if(!asteroidDropped){
         gameThread = new Thread(this);
         gameThread.start();
         asteroidDropped = true;
      }
   }
   public void shootLaser(){
      if(!laserShot){
         gameThread = new Thread(this);
         gameThread.start();
         laserShot = true;
      }
   }

   public void gameUpdate(){
      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i].move();
      }
   }
   public void renderLaser(){
      LaserBeam laserBeam = new LaserBeam(this, spaceship.getXCord()+10, spaceship.getYCord()+7);//25 - 10 /2 = 7.5 ~ 7 or 8
      laserBeam.move();
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

   public void gameRender(){
      Graphics g = getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(backgroundImage, 0, 0, null);

      if(spaceship != null)
         spaceship.draw();

      if(asteroids != null){
         for(int i = 0; i<NUM_ASTEROIDS; i++)
            asteroids[i].draw();
      }
   }

   @Override
   public void run(){
      try{
         isRunning = true;
         while(isRunning){
            gameUpdate();
            gameRender();
            Thread.sleep(50);
         }
      }
      catch(InterruptedException e){}
   }

}