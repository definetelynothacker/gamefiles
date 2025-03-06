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

    private float dy;
    public static boolean isCollideWithAsteroid;
    //private float accumulatedMove;

    private Color bgColor;

    public LaserBeam(JPanel p, int xPos, int yPos){
        panel = p;
        bgColor = panel.getBackground();

        xCord = xPos;
        yCord = yPos;

        width = 10;
        height = 20;
        
        dy = 5;

        isCollideWithAsteroid = false;
        canMove = true;
        //accumulatedMove = 0.0f;
    }

    public void draw(){
        if(!panel.isVisible())return;

        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        laserBeam = new Rectangle2D.Double(xCord, yCord, width, height);
        g2.setColor(Color.BLUE);
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
    public void move(){
        if (!panel.isVisible()) return;

        isCollideWithAsteroid = collidesWithAsteroid();
        
        if(canMove)
            yCord -= dy;
        if(isCollideWithAsteroid){
            ScoringPanel.laserCollisionScore();
            canMove = false;
        }
    }
    public boolean collidesWithAsteroid(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        for(Asteroid asteroid: GamePanel.asteroids){
            Rectangle2D.Double asteroidRect = asteroid.getBoundingRectangle();
            if(myRect.intersects(asteroidRect)){
                asteroid.setLocation();//after colliding with asteroid move to top;
                return true;
            }
        }
        return false;
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
