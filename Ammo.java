import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import javax.swing.JPanel;

public class Ammo {
    private int xCord;
    private int yCord;

    private int width;
    private int height;

    private final int MOVE_DOWN;

    private final Random random;

    private final Image image;
    private final Color bgColor;

    private final JPanel panel;

    public Ammo(JPanel panel, int xCord, int yCord){
        image = ImageManager.loadImage("bullets.png");
        this.panel = panel;
        bgColor = panel.getBackground();
        
        this.xCord = xCord;
        this.yCord = yCord;

        this.MOVE_DOWN = 5;

        random = new Random();
    }
    public void draw(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, xCord, yCord, width, height, null);
        g.dispose();
    }
    public void move(){
        xCord += random.nextInt(-10, 10);
        yCord += MOVE_DOWN;
    }
    public void erase(){
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(bgColor);
        g.dispose();
    }
}
