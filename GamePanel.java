import java.util.Random;
import javax.swing.JPanel;

/**
 * A component that displays all the game entities
 */

public class GamePanel extends JPanel{

   public static Spaceship spaceship;
   private Asteroid asteroid;

   private Random random;

   public GamePanel(){
      spaceship = null;
      asteroid = null;

      random = new Random();
   }

   public void createGameEntities(){
      spaceship = new Spaceship(this, 65, 75);
      asteroid = new Asteroid(this, random.nextInt(400), 36);
      ObjectStorageManager.getAsteroidsList().add(asteroid);
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
   public void removeLaser(LaserBeam laserBeam){
      ObjectStorageManager.getLaserBeamList().remove(laserBeam);
      laserBeam = null;
   }/*
   public void removeAsteroid(Asteroid asteroid){
      asteroids.remove(asteroid);
      asteroid = null;
   }*/
  /* 
   public void shootLaser(){
      LaserBeam laserBeam = new LaserBeam(this, spaceship.getXCord() + spaceship.getWidth()/2, (spaceship.getYCord() - spaceship.getHeight()));
      ObjectStorageManager.getLaserBeamList().add(laserBeam);
      while(!laserBeam.isOffScreen()){
         laserBeam.draw();
         laserBeam.erase();
         laserBeam.move();
      }
      removeLaser(laserBeam);
   }*/
   public void shootLaser(){
      LaserBeam laserBeam = new LaserBeam(this, spaceship.getXCord() + spaceship.getWidth()/2, (spaceship.getYCord() - spaceship.getHeight()));
      ObjectStorageManager.getLaserBeamList().add(laserBeam);
      laserBeam.start();
      ObjectStorageManager.getLaserBeamList().remove(laserBeam);
   }
   public boolean entityCollision(int x1, int y1, int x2, int y2, int width2, int height2){
      if(x1 >= x2 && x1 <= (x2+width2))
         return true;
      return y1 >= y2 && y1 <= (y2+height2);
   }
   public void dropAsteroid(){
      if(asteroid!=null){
         asteroid.start();
      }
   }
   /*
   public void startGameLoop(){
      gameTimer.start();
   }
   public void stopGameLoop(){
      gameTimer.stop();
   }*/
/*
   public boolean isOnBat(int x, int y) {
      if (bat != null)
         return bat.isOnBat(x, y);
      else
         return false;
   }*/

}