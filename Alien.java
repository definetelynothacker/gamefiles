import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Alien extends Thread {

   private JPanel panel;
   private int x;		// x-coordinate of Alien's position
   private int y;		// y-coordinate of Alien's position

   private int width;		// width of Alien
   private int height; 		// height of Alien

   private int topY;		// regenerated alien drawn at this y coordinate

   Ellipse2D.Double head;	// ellipse drawn for face

   private int dx;		// increment to move along x-axis
   private int dy;		// increment to move along y-axis

   private Color backgroundColour;
   private Dimension dimension;

   boolean isRunning;

   Random random;

   public Alien (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();

      x = xPos;
      y = yPos;

      width = 30;
      height = 45;

      topY = yPos;
	
      dx = 0;			// no movement along x-axis
      dy = 10;			// would like the alien to drop down

      random = new Random();
   }


   public void draw () {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      // Draw the head

      head = new Ellipse2D.Double(x, y, width, height);

      g2.setColor(Color.YELLOW); 
      g2.fill(head);		// colour the head

      g2.setColor(Color.BLACK);	 
      g2.draw(head);		// draw outline around head

      g2.setColor(Color.BLACK);

      // Draw the eyes
      Line2D.Double eye1 = new Line2D.Double(x+8, y+15, x+12, y+24);
      g2.draw(eye1);

      Line2D.Double eye2 = new Line2D.Double(x+20, y+24, x+24, y+15);
      g2.draw(eye2);

      // Draw the mouth
      Rectangle2D.Double mouth = new Rectangle2D.Double(x+9, y+33, 14, 3);
      g2.setColor(Color.GREEN);
      g2.fill(mouth);

      g.dispose();
   }


   public void erase(){
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;

      // erase face by drawing a rectangle on top of it

      g2.setColor (backgroundColour);
      g2.fill (new Rectangle2D.Double (x-10, y-10, 30+20, 45+20));

      g.dispose();
   }


   public void move() {

      if (!panel.isVisible ()) return;

      int panelWidth = panel.getWidth();
      int panelHeight = panel.getHeight();

      x = x + dx;
      y = y + dy;
 
      if (y > panelHeight) {
	      y = topY;					// regenerate alien at this y-coordinate
	      x = random.nextInt (panelWidth - width);	// regenerate at random x-coordinate
      }
   }


   public void run () {

      isRunning = true;

      try{
        while(isRunning) {
            erase();
	         move();
            draw();
            sleep(100);
         }
      }
      catch(InterruptedException e) {}
   }


   public boolean isOnHead (int x, int y) {
      if (head == null)
      	  return false;

      return head.contains(x, y);
   }

}