import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class ForceField{

    private final JPanel panel;
    public static int xCord;
    public static int yCord;

    private int radius;

    Ellipse2D.Double forceField;

    private final float growth;

    public static boolean isCollideWithAsteroid;

    private final Color color, bgColor;

    public ForceField(JPanel p, int xPos, int yPos){
        panel = p;
        bgColor = panel.getBackground();
        color = Color.BLUE;

        xCord = xPos;
        yCord = yPos;

        radius = 10;
        
        growth = 4.0f;

        isCollideWithAsteroid = false;
    }

    public void draw(Graphics2D g2){
        forceField = new Ellipse2D.Double(xCord, yCord, radius, radius);
        g2.setColor(color);
        g2.draw(forceField);
    }
    public void erase(){
        if(!panel.isVisible())return;
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Ellipse2D.Double(xCord, yCord, radius, radius));
        g.dispose();
    }
    public void move(){
        if (!panel.isVisible()) return;

        radius += growth;

        isCollideWithAsteroid = collidesWithAsteroid();

        if(collidesWithSpaceship()){

        }
        
        if(isCollideWithAsteroid){
            ScoringPanel.laserCollisionScore();
        }

        if(collidesWithUFO()){
            GamePanel.ufo.gotShootBySpaceshipLaser();
        }
    }
    public boolean collidesWithAsteroid(){
        Ellipse2D.Double myRect = getBoundingEllipse();
        for(Asteroid asteroid: GamePanel.asteroids){
            Rectangle2D.Double asteroidRect = asteroid.getBoundingRectangle();
            if(myRect.intersects(asteroidRect)){
                asteroid.setLocation();
                return true;
            }
        }
        return false;
    }
    public boolean collidesWithSpaceship(){
        Ellipse2D.Double myRect = getBoundingEllipse();
        Rectangle2D.Double spaceshipRect = GamePanel.spaceship.getBoundingRectangle();
        return myRect.intersects(spaceshipRect);
    }
    public boolean collidesWithUFO(){
        Ellipse2D.Double myRect = getBoundingEllipse();
        Rectangle2D.Double ufoRect = GamePanel.ufo.getBoundingRectangle();
        return myRect.intersects(ufoRect);
    }
    public Ellipse2D.Double getBoundingEllipse(){
        return new Ellipse2D.Double (xCord, yCord, radius, radius);
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
    public int getRadius(){
        return radius;
    }
}
