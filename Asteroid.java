import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Asteroid extends Thread{
    private Rectangle2D.Double asteroid;

    public int xCord;
    public int yCord;

    public int width;
    public int height;
    private int topY;

    private boolean isRunning;

    //private final float dy;
    private final int dy;
    private int dx;
    private final Color bgColor;

    private Random random;

    JPanel panel;
    
    public Asteroid(JPanel panel, int xCord, int yCord){
        
        this.xCord = xCord;
        this.yCord = yCord;
        this.panel = panel;

        bgColor = panel.getBackground();  
        
        width = 36;
        height = 36;

        //dy = 0.1f;
        //accumulatedVel  = 0.0f;
        dy = 3;
        dx = 0;

        random = new Random();
    }
    public void move(){
        if(!panel.isVisible())return;

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        xCord = xCord + dx;
        yCord = yCord + dy;

        if(yCord > panelHeight){
            yCord = topY;					
            xCord = random.nextInt (panelWidth - width);
        }
    }
    public void draw(){
        if(!panel.isVisible())return;
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        asteroid = new Rectangle2D.Double(xCord, yCord, width, height); 
        g2.setColor(Color.BLACK);
        g2.fill(asteroid);
        g.dispose();
    }
    public void drawExplosion(){
        if(!panel.isVisible())return;

        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(new Ellipse2D.Double(xCord, yCord, 60, 40));
        g2.fill(new Ellipse2D.Double(xCord+30, yCord-10, 70, 50));
        g2.fill(new Ellipse2D.Double(xCord+70, yCord, 60, 40));
        
        g2.setColor(Color.DARK_GRAY);
        g2.draw(new Ellipse2D.Double(xCord, yCord, 60, 40));
        g2.draw(new Ellipse2D.Double(xCord+30, yCord-10, 70, 50));
        g2.draw(new Ellipse2D.Double(xCord+70, yCord, 60, 40));

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
    public boolean isOnScreen(){
        int panelHeight = panel.getHeight();
        
        return yCord <= panelHeight;
    }
    public void run(){
        isRunning = true;
        try{
            while(isRunning){
                erase();
                move();
                draw();/*
                for (LaserBeam laserBeam : ObjectStorageManager.getLaserBeamList()) {
                    if (isCollidingWith(laserBeam)){
                        this.resetPosition();
                        laserBeam.stopLaser();
                        this.drawExplosion();
                    }
                }*/
                if(isCollidingWith(ObjectStorageManager.getSpaceship()))
                    ScoringPanel.subtractHealth(50);
                Thread.sleep(50);
            } 
        }
        catch (InterruptedException e){}
    }
    private boolean isCollidingWith(LaserBeam laser){
        return getBounds().intersects(laser.getBounds());
    }
    private boolean isCollidingWith(Spaceship spaceship){
        return getBounds().intersects(spaceship.getBounds());
    }
    public Rectangle2D getBounds(){
        return new Rectangle2D.Double(xCord, yCord, width, height);
    }
    public void resetPosition(){
        erase();
        yCord = 0;
        xCord = new Random().nextInt(364);
    }
    public boolean isOnHead(int x, int y) {
        if(asteroid == null)return false;
        return asteroid.contains(x, y);
    }
    public void stopAsteroid() {
        isRunning = false;
        this.interrupt();
    }
    public int getXCord(){
        return xCord;
    }
    public int getYCord(){
        return yCord;
    }

}