import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public final class Asteroid{

    private final Spaceship spaceship;
    public int xCord;
    public int yCord;

    public int width;
    public int height;

    private int dy;
    private final int dx;

    private final Random random;

    private final JPanel panel;

    private final Image asteroidImage;
    private final Color bgColor;
    
    public Asteroid(JPanel panel, int xCord, int yCord, Spaceship spaceship){
        
        this.spaceship = spaceship;
        this.xCord = xCord;
        this.yCord = yCord;
        this.panel = panel;
        this.bgColor = panel.getBackground();

        random = new Random();

        width = 36;
        height = 36;

        setLocation(); 

        dy = 3;
        dx = 0;

        asteroidImage = ImageManager.loadImage("asteroid.png");
    }
    public void setLocation(){
        int panelWidth = panel.getWidth();
        xCord = random.nextInt(panelWidth - width);
        yCord = 10;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(asteroidImage, xCord, yCord, width, height, null);
    }
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Rectangle2D.Double(xCord, yCord, width, height));

        g.dispose();
    }
    public void move(){
        if(!panel.isVisible ())return;

        xCord = xCord + dx;
        yCord = yCord + dy;

        int panelHeight = panel.getHeight();
        boolean collision = collidesWithSpaceship();
        
        if(collision && !Spaceship.getIsExploded()){
            ScoringPanel.yesAsteroidCollision();
            setLocation();
        }
        if(yCord>panelHeight) {
            setLocation();
            dy = dy + 1;
        }
        if(dy>20)
            dy/=2;
    }
    public boolean isOnAsteroid(int xCord, int yCord){
        if(asteroidImage == null)
            return false;
        
        Rectangle2D.Double myRect = getBoundingRectangle();
        return myRect.contains(xCord, yCord);
    }
    public boolean collidesWithSpaceship(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double spaceshipRect = spaceship.getBoundingRectangle();
      
        return myRect.intersects(spaceshipRect);
    }
    public boolean isOnScreen(){
        int panelHeight = panel.getHeight();
        
        return yCord <= panelHeight;
    }
    public void resetPosition(){
        yCord = 0;
        xCord = new Random().nextInt(364);
    }
    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double (xCord, yCord, width, height);
    }
    public int getXCord(){
        return xCord;
    }
    public int getYCord(){
        return yCord;
    }

}