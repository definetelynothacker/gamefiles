import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Ammo {
    private int xCord;
    private int yCord;

    private final int width;
    private final int height;

    private final Spaceship spaceship;

    private final int dy;

    private final Random random;

    private final Image image;

    private final JPanel panel;
    public static boolean collected;
    private final Color bgColor;

    public Ammo(JPanel panel, int xCord, int yCord, Spaceship spaceship){
        image = ImageManager.loadImage("bullets.png");
        this.panel = panel;
        bgColor = panel.getBackground();
        
        this.width = 25;
        this.height = 25;

        this.xCord = xCord;
        this.yCord = yCord;

        this.spaceship = spaceship;

        this.dy = 5;

        random = new Random();
        collected = false;

        setLocation();
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
    public void move(){
        if(!panel.isVisible ())return;
        int panelHeight = panel.getHeight();

        if(yCord > panelHeight)
            setLocation();

        if(collidesWithSpaceship() && !Spaceship.getIsExploded()){
            setLocation();
            collected = true;
            AmmoPanel.collectAmmoPackage();
        }

        //xCord += random.nextInt(-10, 10);
        yCord += dy;
    }
    public final void setLocation(){
        int panelWidth = panel.getWidth();
        this.xCord = random.nextInt(panelWidth - width);
        this.yCord = 10;
    }
    public boolean collidesWithSpaceship(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double spaceshipRect = spaceship.getBoundingRectangle();
      
        return myRect.intersects(spaceshipRect);
    }
    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double (xCord, yCord, width, height);
    }
}
