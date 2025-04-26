import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class UFO{//player

    private final JPanel panel;
    private int xCord;
    private final int yCord;
    private final Random random;

    public static double health;
    public static int amtDestroyed=0;
    
    private final int width;
    private final int height;

    private Rectangle2D healthBar, healthBarBorder;
    private static double healthBarAmt;
    public static int ufoLives=4;
    private static double ufoHealthToDecrease[] = {1, 2, 3, 4, 5};//50 is the length so -1= 5 shots to kill

    private Rectangle2D.Double spaceship;
    private final Image defaultImage, explosionImage;

    //flags
    private static boolean isExploded;
    private static boolean is3SecondsElapsed;
    private static boolean gotTime, playerGotPoints;

    private long explosionStartTime;
    private long currentTime;
    private long timeElapsed;

    private final int dx;

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
        health = 200;//since at arr[4] = 5, 50/5 = 10 shots, 200 health 
        healthBarAmt = width = 50;
        height = 50;

        dx = 25;//speed

        isExploded = is3SecondsElapsed = gotTime = playerGotPoints = false;
    }
    public void draw(Graphics2D g2){
        healthBar = new Rectangle2D.Double(xCord, yCord+height+3, healthBarAmt, height/4);
        healthBarBorder = new Rectangle2D.Double(xCord, yCord+height+3, width, height/4);

        g2.setColor(Color.RED);

        if(amtDestroyed<=5){
        if(!isExploded){
            g2.draw(healthBarBorder);
            g2.fill(healthBar);
            g2.draw(healthBar);
        }

        if(health>0)
            g2.drawImage(defaultImage, xCord, yCord, width, height, null);
        
        else if(!is3SecondsElapsed){//if dead and time has not crossed 3 seconds draw explosion
            g2.drawImage(explosionImage, xCord, yCord, width, height, null);
            isExploded = true;
            if(!gotTime){
                explosionStartTime = System.currentTimeMillis();
                gotTime = true;
                amtDestroyed++;
            }
            if(!playerGotPoints){
                ScoringPanel.destroyUFO();
            }
            currentTime = System.currentTimeMillis();
            timeElapsed = currentTime - explosionStartTime;
            if(timeElapsed>3000)
                is3SecondsElapsed = true;
        }
        else if(is3SecondsElapsed){
            g2.drawImage(ImageManager.loadImage("bg2.jpg"), xCord, yCord, xCord + width, yCord + height, xCord, yCord, xCord + width, yCord + height, null);
            
        }
    }
        
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
    public void gotShootBySpaceshipLaser(){
        health-=20;
        healthBarAmt -= ufoHealthToDecrease[ufoLives];///essentially how much laser shots will equate health to zero
    }
    public static boolean getIsExploded(){return UFO.isExploded;}

    public static void resetUFO(){
        if(ufoLives>0){
            UFO.ufoLives-=1;//initially = 4
            UFO.healthBarAmt = 50;//width
            UFO.health = (50/ufoHealthToDecrease[ufoLives])*20;
            //flags
            UFO.isExploded = false;
            UFO.gotTime = false;
            UFO.is3SecondsElapsed = false;
            UFO.playerGotPoints = false;
        }
    }
}
