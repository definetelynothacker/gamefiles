import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 * A component that displays all the game entities
 */

public class GamePanel extends JPanel implements Runnable{

   private final int NUM_ASTEROIDS = 5;

   public static Asteroid[] asteroids;
   public static Spaceship spaceship;
   public static UFO ufo;

   public static Ammo ammoPkg;
   public static Health healthPkg;

   private static CopyOnWriteArrayList<LaserBeam> laserBeams;
   private static CopyOnWriteArrayList<LaserBeam> laserBeamsUFO;
   private static CopyOnWriteArrayList<ForceField> forceFields;

   private boolean isRunning, isPaused;

   private BufferedImage image;
   private Background background;

   private SoundManager soundManager;
   private Thread gameThread;
   private long lastTime, lastTimeExploded=0, currentTime;
   private final Random random;

   private HeatAnimation heatAnimation;
   

   //sound flags
   private boolean explosionSoundPlayedUFO, explosionSoundPlayedSpaceship;

   public GamePanel(){

      spaceship = null;
      ufo = null;
      asteroids = null;
      forceFields = null;


      healthPkg = null;
      ammoPkg = null;

      laserBeams = null;

      explosionSoundPlayedUFO = explosionSoundPlayedSpaceship = isRunning = false;
      isPaused = false;

      random = new Random();
      lastTime = System.currentTimeMillis();

      image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
      soundManager = SoundManager.getInstance();
      //backgroundImage = ImageManager.loadImage("bg2.jpg");
   }

   public void createGameEntities(){
      int panelWidth = this.getWidth();

      background = new Background(this, "bg2.jpg", 96);
      spaceship = new Spaceship(this, 65, 75);
      ufo = new UFO(this, 150, 0);
      asteroids = new Asteroid[NUM_ASTEROIDS];
      ammoPkg = new Ammo(this, random.nextInt(panelWidth), 10, spaceship);
      healthPkg = new Health(this, random.nextInt(panelWidth), 10, spaceship);
      laserBeams = new CopyOnWriteArrayList<>();
      forceFields = new CopyOnWriteArrayList<>();
      laserBeamsUFO = new CopyOnWriteArrayList<>();
      heatAnimation = new HeatAnimation(spaceship.getXCord()+25, spaceship.getYCord()+25);

      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i] = new Asteroid(this, random.nextInt(panelWidth), 10, spaceship);
      }
   }
   public void updateGameEntities(int direction){
      if(spaceship == null)
         return;

      if(background!=null)
         background.move(direction);
   
      spaceship.erase();
      spaceship.move(direction);
   }
   public boolean entityCollision(int x1, int y1, int x2, int y2, int width2, int height2){
      if(x1 >= x2 && x1 <= (x2+width2))
         return true;
      return y1 >= y2 && y1 <= (y2+height2);
   }
   public void updateSpaceship(int direction){
      spaceship.move(direction);
   }
   //Asteroid
   //
   //
   public void dropAsteroid(){
      gameThread = new Thread(this);
      gameThread.start();
   }
   public void playAgain(){
      if(Spaceship.lives_rem>0 && Spaceship.health<=0)
         ScoringPanel.playAgain();
      else{
         JWindow popup = new JWindow();
         popup.setLayout(new BorderLayout());

         JLabel messageLabel = new JLabel("Health > 0 or No lives remaining", SwingConstants.CENTER);
         messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
         popup.add(messageLabel, BorderLayout.CENTER);

         popup.setSize(300, 100);
         popup.setLocationRelativeTo(null);

         popup.setVisible(true);

         new Timer().schedule(new TimerTask() {
               @Override
               public void run() {
                  popup.dispose();
               }
         }, 3000);
      }
   }
   public void createLaser(){
      LaserBeam newLaserBeam = new LaserBeam(this, spaceship.getXCord()+7, spaceship.getYCord()+25, true, Color.BLUE, false);
      boolean laserSoundPlayer=false;
      if(!laserSoundPlayer){
         soundManager.playClip("laser", false);
         laserSoundPlayer=true;
      }
      laserBeams.add(newLaserBeam);
   }
   public void moveRenderLaser(Graphics2D imageContext){
      for(LaserBeam laserBeam: laserBeams){
         if(laserBeam!=null && laserBeam.canMove){
            laserBeam.draw(imageContext);
            laserBeam.move(1);
            laserBeam.erase();
         }
         else if(laserBeam!=null && laserBeam.getYCord()<0){
            laserBeams.remove(laserBeam);
            laserBeam = null;
         }
      }
   }
   public void shootUFOLaser(){
      LaserBeam newLaserBeam = new LaserBeam(this, ufo.getXCord()+7, ufo.getYCord()+ufo.getHeight(), false, Color.RED, true);
      laserBeamsUFO.add(newLaserBeam);
   }
   public void renderUFOLaser(Graphics2D imageContext){
      for(LaserBeam laserBeam: laserBeamsUFO){
         if(laserBeam!=null && laserBeam.canMove && !UFO.getIsExploded()){
            laserBeam.draw(imageContext);
            laserBeam.move(2);
            laserBeam.erase();
         }
         else if(laserBeam!=null && laserBeam.getYCord()>this.getWidth()){
            laserBeamsUFO.remove(laserBeam);
            laserBeam = null;
         }
      }
   }
   public void gameUpdate(){
      ammoPkg.move();
      healthPkg.move();
      ufo.move();
      for(int i = 0; i<NUM_ASTEROIDS; i++){
         asteroids[i].move();
      }
      heatAnimation.update();
   }
   public void gameRender(){
      Graphics2D imageContext = (Graphics2D) image.getGraphics();
      background.draw(imageContext);

      if(spaceship != null)
         spaceship.draw(imageContext);

      if(ammoPkg != null)
         ammoPkg.draw(imageContext);

      if(healthPkg != null)
         healthPkg.draw(imageContext);

      if(ufo != null)
         ufo.draw(imageContext);
      if(heatAnimation!=null){
         heatAnimation.draw(imageContext);
      }
      if(asteroids != null){
         for(int i = 0; i<NUM_ASTEROIDS; i++)
            asteroids[i].draw(imageContext);
      }
      moveRenderLaser(imageContext);
      renderUFOLaser(imageContext);
      renderForceField(imageContext);
      Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
   }
   public static void switchContext(){
      
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
   public boolean isOnSpaceShip(int x, int y){
      return spaceship.isOnSpaceShip(x, y);
   }
   public static void removeUFOLaser(LaserBeam laserBeam){
      laserBeamsUFO.remove(laserBeam);
   }
   public static void removeSpaceshipLaser(LaserBeam laserBeam){
      laserBeams.remove(laserBeam);
   }
   //
   //
   public void startForceField(){
      ForceField newForceField = new ForceField(this, spaceship.getXCord()+10, spaceship.getXCord()+10);
      forceFields.add(newForceField);
      Spaceship.amtForcefields-=1;
      GameWindow.xPressed = false;
   }
   public void renderForceField(Graphics2D imageContext){
      for(ForceField forceField: forceFields){
         forceField.draw(imageContext);
         forceField.move();
         //when rad = screen width: STOP
         if(forceField.getRadius() >= 400){
            forceFields.remove(forceField);//after can't grow, remove form list
         }
      }
   }
   @Override
   public void run(){
      try{
         isRunning = true;
         while(isRunning){
            if(!isPaused){
               gameUpdate();
               gameRender();
            currentTime = System.currentTimeMillis();
            if(heatAnimation!=null){
               heatAnimation.start();
            }
            if(currentTime - lastTime > 1500 && !UFO.getIsExploded()){//only shoots if not exploded
               shootUFOLaser();
               lastTime = currentTime;
            }
            if(Spaceship.isExploded && !explosionSoundPlayedSpaceship){
               soundManager.playClip("explosion", false);
               explosionSoundPlayedSpaceship = true;
            }
            if(Ammo.collected){
               Ammo.collected = false;
               soundManager.playClip("reload", false);
            }
            if(Health.collected){
               Health.collected = false;
               soundManager.playClip("powerup", false);
            }
            if(UFO.getIsExploded()){
               if(!explosionSoundPlayedUFO){
                  soundManager.playClip("explosions", false);
                  //explosionSoundPlayedUFO=true;
               }
               if (lastTimeExploded == 0) {
                   lastTimeExploded = System.currentTimeMillis();
               }
               if(currentTime - lastTimeExploded > 15000){
                   UFO.resetUFO();
                   lastTimeExploded = 0;
               }
            }
               if(GameWindow.xPressed && !Spaceship.isExploded){//xpressed=true assumed player had enough points to buy laser
                  startForceField();
               }

               if(GameWindow.spacePressed && Spaceship.amtLasers>0 && spaceship.canShoot()){
                  createLaser();
                  AmmoPanel.decreaseAmmo();
                  GameWindow.spacePressed = false;
               }
            }
            Thread.sleep(50);
            /*
            gameRender();
            currentTime = System.currentTimeMillis();

            if(currentTime - lastTime > 1500 && !UFO.getIsExploded()){//only shoots if not exploded
               shootUFOLaser();
               lastTime = currentTime;
            }
            if(Spaceship.isExploded && !explosionSoundPlayedSpaceship){
               soundManager.playClip("explosion", false);
               explosionSoundPlayedSpaceship = true;
            }
            if(Ammo.collected){
               Ammo.collected = false;
               soundManager.playClip("reload", false);
            }
            if(Health.collected){
               Health.collected = false;
               soundManager.playClip("powerup", false);
            }
            if(UFO.getIsExploded()){
               if(!explosionSoundPlayedUFO){
                  soundManager.playClip("explosions", false);
                  //explosionSoundPlayedUFO=true;
               }
               
               if (lastTimeExploded == 0) {
                   lastTimeExploded = System.currentTimeMillis();
               }
               if(currentTime - lastTimeExploded > 15000){
                   UFO.resetUFO();
                   lastTimeExploded = 0;
               }
            }
            if(GameWindow.xPressed && !Spaceship.isExploded){//xpressed=true assumed player had enough points to buy laser
               startForceField();
            }

            if(GameWindow.spacePressed && Spaceship.amtLasers>0 && spaceship.canShoot()){
               createLaser();
               AmmoPanel.decreaseAmmo();
               GameWindow.spacePressed = false;
            }
            Thread.sleep(50);*/
         }
      }
      catch(InterruptedException e){}
   }
   public void pauseGame(){
		isPaused = !isPaused;
	}


	public void endGame(){
		isRunning = false;
	}

}