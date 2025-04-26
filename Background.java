import java.awt.Graphics2D;
import java.awt.Image;

public class Background {
  	private Image bgImage;
  	private int bgImageWidth, bgImageHeight;      		// width of the background (>= panel Width)
	private int panelWidth, panelHeight;

	private GamePanel panel;

 	private int bgX;			// X-coordinate of "actual" position
	private int bgY;

	private int bg1X;			// X-coordinate of first background
	private int bg1Y;
	private int bgDX;			// size of the background move (in pixels)
	private int bgDY;


	public Background(GamePanel panel, String imageFile, int bgDX) {
		this.panel = panel;
		this.bgImage = ImageManager.loadImage(imageFile);
		bgImageWidth = bgImage.getWidth(null);	// get width of the background
		bgImageHeight = bgImage.getHeight(null);
		System.out.println ("bgImageWidth = " + bgImageWidth);

		if(bgImageWidth < panel.getWidth())
			System.out.println("Background width < panel width");
		if(bgImageHeight < panel.getWidth())
			System.out.println("Background ehgiht < panel height");

		this.bgDX = 200; // bgDX;
		this.bgDY = 200;

		this.panelWidth = panel.getWidth();
		this.panelHeight = panel.getHeight();

		bgX = 0;bgY = 0;
		bg1X = 0;bg1Y = 0;
	}


  public void move (int direction) {
	if(direction == 1)
		moveRight();
	else if(direction == 2)
		moveLeft();
	else if(direction==3)
		moveDown();
	else if(direction==4)
		moveUp();
  }


  public void moveLeft() {

	bgX = bgX - bgDX;

	bg1X = bg1X - bgDX;

	String mess = "Moving background left: bgX=" + bgX + " bg1X=" + bg1X;
	System.out.println (mess);

	if(bg1X < (bgImageWidth * -1)){
		System.out.println ("Background change: bgX = " + bgX); 
		bg1X = 0;
	}

  }


	public void moveRight() {

	bgX = bgX + bgDX;
				
	bg1X = bg1X + bgDX;

	String mess = "Moving background right: bgX=" + bgX + " bg1X=" + bg1X;
	System.out.println (mess);

		if (bg1X > 0) {
			System.out.println ("Background change: bgX = " + bgX); 
			bg1X = bgImageWidth * -1;
		}
	}
	public void moveDown(){
		bgY = bgY + bgDY;
		bg1Y = bg1Y + bgDY;

		String mess = "Moving background Down: bgY=" + bgY + " bg1Y=" + bg1X;
		System.out.println(mess);
		
		if(bg1Y>0){
			System.out.println("Background change: bgY="+bgY);
			bg1Y = bgImageHeight*-1;
		}
	}
	public void moveUp(){
		bgY = bgY - bgDY;
		bg1Y = bg1Y - bgDY;

		String mess = "Moving background upward: bgY=" + bgY + " bg1Y=" + bg1X;
		System.out.println(mess);

		if(bg1Y< (bgImageHeight * -1)){
			System.out.println("Background change: bgY="+bgY);
			bg1Y = 0;
		}
	}
 

  public void draw (Graphics2D g2) {
	//g2.drawImage(bgImage, bg1X, bg1Y, null);
	g2.drawImage(bgImage, bg1X, bg1Y, null);
	g2.drawImage(bgImage, bg1X + bgImageWidth, bg1Y, null);

	// Vertical scrolling
	g2.drawImage(bgImage, bg1X, bg1Y + bgImageHeight, null);
	g2.drawImage(bgImage, bg1X + bgImageWidth, bg1Y + bgImageHeight, null);
  }

}
