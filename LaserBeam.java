import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class LaserBeam{

    private JPanel panel;
    private int xCord;
    private int yCord;

    private int width;
    private int height;

    Rectangle2D.Double laserBeam;

    //flags
    public boolean canMove;
    boolean canDestroyAsteroid;
    boolean isUfoLaser;

    private float dy;
    public static boolean isCollideWithAsteroid;
    //private float accumulatedMove;

    private final Color bgColor;
    private Color laserColor;

    public LaserBeam(JPanel p, int xPos, int yPos, boolean canDestroyAsteroid, Color laserColor, boolean isUfoLaser){
        panel = p;
        bgColor = panel.getBackground();

        xCord = xPos;
        yCord = yPos;

        width = 10;
        height = 20;
        
        dy = 5;

        isCollideWithAsteroid = false;
        canMove = true;
        this.canDestroyAsteroid=canDestroyAsteroid;
        this.isUfoLaser = isUfoLaser;
        this.laserColor = laserColor;
        //accumulatedMove = 0.0f;
    }

    public void draw(){
        if(!panel.isVisible())return;

        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        laserBeam = new Rectangle2D.Double(xCord, yCord, width, height);
        
        g2.setColor(laserColor);
        g2.fill(laserBeam);

        g.dispose();
    }
    public void erase(){
        if(!panel.isVisible())return;
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Rectangle2D.Double(xCord, yCord, width, height));
        g.dispose();
    }
    public void move(int direction){
        if (!panel.isVisible()) return;

        isCollideWithAsteroid = collidesWithAsteroid();
        
        if(canMove && direction==1)
            yCord-=dy;
        else if(canMove && direction==2)
            yCord+=dy;

        if(collidesWithSpaceship() && !canDestroyAsteroid){//if shot by spaceship, cannot do damage to spaceship
            ScoringPanel.spaceshipHitByLaser();
            GamePanel.removeUFOLaser(this);
        }

        if(isCollideWithAsteroid){
            ScoringPanel.laserCollisionScore();
            canMove = false;
        }

        if(collidesWithUFO()){
            GamePanel.ufo.gotShootBySpaceshipLaser();
            GamePanel.removeSpaceshipLaser(this);
        }
    }
    public boolean collidesWithAsteroid(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        for(Asteroid asteroid: GamePanel.asteroids){
            Rectangle2D.Double asteroidRect = asteroid.getBoundingRectangle();
            if(myRect.intersects(asteroidRect) && canDestroyAsteroid){
                asteroid.setLocation();//after colliding with asteroid move to top;
                return true;
            }
        }
        return false;
    }
    public boolean collidesWithSpaceship(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double spaceshipRect = GamePanel.spaceship.getBoundingRectangle();
        return myRect.intersects(spaceshipRect);
    }
    public boolean collidesWithUFO(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double ufoRect = GamePanel.ufo.getBoundingRectangle();
        return myRect.intersects(ufoRect) && !isUfoLaser;
    }
    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double (xCord, yCord, width, height);
    }
    public boolean isOffScreen(){
        return yCord <= 0;
    }
    public int getXCord(){
        return xCord;
    }
    public int getYCord(){
        return yCord;
    }
}
