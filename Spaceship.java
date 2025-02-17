import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Spaceship {
    private JPanel panel;
    private int xCord;
    private int yCord;

    private final int width;
    private final int height;

    Rectangle2D.Double spaceship;
    
    private final int dx;
    private final int dy;

    private final Color bgColor;
    //private final Dimension dimension;

    public Spaceship(JPanel p, int xPos, int yPos){
        panel = p;
        //dimension = panel.getSize();
        bgColor = panel.getBackground();

        xCord = xPos;
        yCord = yPos;

        width = 25;
        height = 25;

        dx = 25;//speed
        dy = 25;//speed
    }
    public void draw(){
        if(!panel.isVisible())return;
        
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        
        spaceship = new Rectangle2D.Double(xCord, yCord, width, height);
        g2.setColor(Color.BLUE);
        g2.fill(spaceship);

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
    public void move(int direction){
        if(!panel.isVisible())return;//if visible move, else don't

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        if(direction == 1){//move left
            if((xCord)>0)
                xCord = xCord - dx;
        } 
        else if(direction == 2){//move right
            if((xCord+width)<panelWidth)
                xCord = xCord + dx;
        }
        else if(direction == 3){//move down
            if((yCord+height)<panelHeight)
                yCord = yCord + dy;   
        }
        else if(direction == 4){//move up
            if((yCord)>0)
                yCord = yCord - dy;
        }
    }
    public Rectangle2D getBounds(){
        return new Rectangle2D.Double(xCord, yCord, width, height);
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
}
