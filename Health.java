import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Health {

    private final int height;
    private final int width;
    
    private int yCord, xCord, dy;
    private final Spaceship spaceship;

    private final Image image;

    private final JPanel panel;

    private final Random random;

    public static boolean collected;
    private final Color bgColor;

    public Health(JPanel panel, int xCord, int yCord, Spaceship spaceship){
        this.height = 25;
        this.width = 25;

        this.yCord = yCord;
        this.xCord = xCord;
        this.dy = 5;

        this.panel = panel;
        this.bgColor = panel.getBackground();

        this.spaceship = spaceship;

        this.random = new Random();

        image = ImageManager.loadImage("first-aid-kit.png");
        collected = false;

        setLocation();
    }
    public final void setLocation(){
        int panelWidth = panel.getWidth();
        xCord = random.nextInt(panelWidth - width);
        yCord = 10;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(image, xCord, yCord, width, height, null);
    }
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Rectangle2D.Double(xCord, yCord, width, height));

        g.dispose();
    }
    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double (xCord, yCord, width, height);
    }
    public boolean collidesWithSpaceship(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double spaceshipRect = spaceship.getBoundingRectangle();
      
        return myRect.intersects(spaceshipRect);
    }
    public void move(){
        if(!panel.isVisible ())return;

        int panelHeight = panel.getHeight();

        if(yCord > panelHeight)
            setLocation();

        if(collidesWithSpaceship() && !Spaceship.getIsExploded()){
            ScoringPanel.addHealth();
            collected = true;
            setLocation();
        }
        yCord += dy;
    }
}
