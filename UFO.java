import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class UFO{//player

    private final JPanel panel;
    private int xCord;
    private int yCord;
    private Random random;

    public static int score=0;
    public static int health=300;
    public static int lives_rem=3;
    public static int amtLasers=1000000000;//amt Ammo
    
    private final int width;
    private final int height;

    private Rectangle2D.Double spaceship;
    private final Image defaultImage, explosionImage;

    //flags
    private static boolean isExploded;

    private final int dx;
    private final int dy;

    private final Color bgColor;
    //private final Dimension dimension;

    public UFO(JPanel p, int xPos, int yPos){
        panel = p;

        bgColor = panel.getBackground();

        defaultImage = ImageManager.loadImage("ufo.png");
        explosionImage = ImageManager.loadImage("explode.png");

        xCord = xPos;
        yCord = yPos;

        random = new Random();

        width = 25;
        height = 25;

        dx = 25;//speed
        dy = 25;//speed

        isExploded = false;
    }
    public void draw(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        if(health>0)
            g2.drawImage(defaultImage, xCord, yCord, width, height, null);
        else
            g2.drawImage(explosionImage, xCord, yCord, width*2, height*2, null);
        g.dispose();
    }
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Rectangle2D.Double(xCord, yCord, width, height));

        g.dispose();
    }
    public void move(){
        if(!panel.isVisible())return;//if visible move, else don't

        int panelWidth = panel.getWidth();

        int number = random.nextInt(2) + 1;

        switch(number) {
            case 1 -> {
                //move left
                if((xCord)>0)
                    xCord = xCord - dx;
            }
            case 2 -> {
                //move right
                if((xCord+width)<panelWidth)
                    xCord = xCord + dx;
            }
            default -> {
            }
        }
    }
    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double (xCord, yCord, width, height);
    }
    public boolean isOnSpaceShip(int xCord, int yCord){
        if(spaceship == null)
            return false;
        return spaceship.contains(xCord, yCord);
    }
    public int getXCord(){
        return xCord;
    }
    public int getYCord(){
        return yCord;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void addScore(int score){
        Spaceship.score+=score;
    }

    public int getScore(){return Spaceship.score;}
    public int getHealth(){return Spaceship.health;}

    public static void addHealth(int health){
        UFO.health+=health;
    }
    public void setIsExploded(boolean isExploded){
        UFO.isExploded = isExploded;
    }
    public static boolean getIsExploded(){return UFO.isExploded;}
}
