import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class LaserBeam{

    private JPanel panel;
    private int xCord;
    private int yCord;

    private int width;
    private int height;

    Rectangle2D.Double laserBeam;

    //private int dy;
    private float dy;
    private float accumulatedMove;

    private Color bgColor;

    boolean isRunning;

    public LaserBeam(JPanel p, int xPos, int yPos){
        panel = p;
        bgColor = panel.getBackground();

        xCord = xPos;
        yCord = yPos;

        width = 10;
        height = 20;
        
        dy = -0.05f;
        accumulatedMove = 0.0f;
        
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
    public void move() {
        if (!panel.isVisible()) return;

        accumulatedMove += dy;

        if (accumulatedMove <= -1.0f) {
            yCord -= 1;
            accumulatedMove = 0.0f;
        }
    }
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(xCord, yCord, width, height);
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
