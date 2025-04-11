import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public final class Planet{

    public int xCord;
    public int yCord;

    public int width;
    public int height;

    private final JPanel panel;

    private Image[] images;
    private final Image chooseImage;
    Spaceship spaceship;
    
    public Planet(JPanel panel, int xCord, int yCord, Spaceship spaceship){
        this.xCord = xCord;
        this.yCord = yCord;
        this.panel = panel;
        this.spaceship = spaceship;

        width = 36;
        height = 36;

        images[0] = ImageManager.loadImage("/planetImages/earth.png");
        images[1] = ImageManager.loadImage("/planetImages/mars.png");
        images[2] = ImageManager.loadImage("/planetImages/saturn_b.png");
        images[3] = ImageManager.loadImage("/planetImages/saturn_y.png");
        Random random = new Random();
        chooseImage = images[random.nextInt(4)];
    }
    public void draw(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(chooseImage, xCord, yCord, width, height, null);
        g.dispose();
    }
    public boolean isOnAsteroid(int xCord, int yCord){
        if(chooseImage == null)
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
