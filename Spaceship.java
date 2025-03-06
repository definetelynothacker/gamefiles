import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Spaceship{//player

    private final JPanel panel;
    private int xCord;
    private int yCord;

    public static int score=0;
    public static int health=300;
    public static int lives_rem=3;
    public static int amtLasers=25;//amt Ammo

    public boolean hasEnoughLasers;
    
    private final int width;
    private final int height;

    private Rectangle2D.Double spaceship;
    private final Image defaultImage, explosionImage, graveImage;

    //flags
    private static boolean isExploded;
    private boolean is3SecondsElapsed;
    private boolean gotTime;
    private boolean canShoot;
    private boolean isDead, playAgainBtnPushed;

    private long explosionStartTime;
    private long currentTime;
    private long timeElapsed;

    private final int dx;
    private final int dy;

    private final Color bgColor;
    //private final Dimension dimension;

    public Spaceship(JPanel p, int xPos, int yPos){
        panel = p;

        bgColor = panel.getBackground();

        defaultImage = ImageManager.loadImage("spaceship.png");
        explosionImage = ImageManager.loadImage("explode.png");
        graveImage = ImageManager.loadImage("grave.png");

        xCord = xPos;
        yCord = yPos;

        width = 25;
        height = 25;

        dx = 25;//speed
        dy = 25;//speed

        isExploded = is3SecondsElapsed = gotTime = isDead = playAgainBtnPushed = false;
        canShoot = hasEnoughLasers = true;
    }
    public void draw(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        if(health>0){//if not dead draw spaceship
            g2.drawImage(defaultImage, xCord, yCord, width, height, null);
        }
        else if(!is3SecondsElapsed && !playAgainBtnPushed){//if dead and time has not crossed 3 seconds draw explosion
            if(playAgainBtnPushed){
                isDead = true;
                playAgainBtnPushed = false;
            }
            g2.drawImage(explosionImage, xCord, yCord, width, height, null);
            isExploded = true;
            canShoot = false;//should only run once to save memory
            if(!gotTime){
                explosionStartTime = System.currentTimeMillis();
                gotTime = true;
            }
            currentTime = System.currentTimeMillis();
            timeElapsed = currentTime - explosionStartTime;
            if(timeElapsed>3000)
                is3SecondsElapsed = true;
        }
        else if(is3SecondsElapsed){//after 3 seconds passed draw grave image
            g2.drawImage(graveImage, xCord, yCord, width*2, height*2, null);
        }
        g.dispose();
    }
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(bgColor);
        g2.fill(new Rectangle2D.Double(xCord, yCord, width, height));

        g.dispose();
    }
    public void move(int direction){
        if(!panel.isVisible())return;//if visible move, else don't

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        switch(direction) {
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
            case 3 -> {
                //move down
                if((yCord+height)<panelHeight)
                    yCord = yCord + dy;
            }
            case 4 -> {
                //move up
                if((yCord)>0)
                    yCord = yCord - dy;
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
        this.score+=score;
    }

    public int getScore(){return this.score;}
    public int getHealth(){return Spaceship.health;}

    public static void addHealth(int health){
        Spaceship.health+=health;
    }
    public void setIs3SecondsElapsed(boolean is3SecondsElapsed){
        this.is3SecondsElapsed = is3SecondsElapsed;
    }
    public void setIsExploded(boolean isExploded){
        Spaceship.isExploded = isExploded;
    }

    public static boolean getIsExploded(){return Spaceship.isExploded;}
    public boolean getIs3SecondsElapsed(){return this.is3SecondsElapsed;}

    public long getExplosionStartTime(){
        return explosionStartTime;
    }
    public void setTimeElapsed(long timeElapsed){
        this.timeElapsed = timeElapsed;
    }
    public long getTimeElapsed(){
        return this.timeElapsed;
    }
    public boolean canShoot(){
        return this.canShoot;
    }
    public boolean hasEnoughLasers(){
        return this.hasEnoughLasers;
    }
    public void hasEnoughLasers(boolean hasEnough){
        this.hasEnoughLasers = hasEnough;
    }
}
