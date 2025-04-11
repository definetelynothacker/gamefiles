import java.awt.Image;
import java.awt.Graphics2D;


/**
    The FaceAnimation class creates a face animation containing six frames 
    based on three images.
*/
public class HeatAnimation {
	
	Animation animation;

	private int x;		// x position of animation
	private int y;		// y position of animation


	public HeatAnimation(int yCord, int xCord) {

		this.x = yCord;
		this.y = xCord;

		animation = new Animation(true);	
		// load images for blinking face animation

		Image animImage1 = ImageManager.loadImage("images/flame_frame_1.png");
		Image animImage2 = ImageManager.loadImage("images/flame_frame_2.png");
		Image animImage3 = ImageManager.loadImage("images/flame_frame_3.png");
		Image animImage4 = ImageManager.loadImage("images/flame_frame_4.png");
	
		// create animation object and insert frames

		animation.addFrame(animImage1, 250);
		animation.addFrame(animImage2, 150);
		animation.addFrame(animImage1, 150);
		animation.addFrame(animImage2, 150);
		animation.addFrame(animImage3, 200);
		animation.addFrame(animImage4, 150);

	}


	public void start() {
		animation.start();
	}

	
	public void update() {
		if (!animation.isStillActive())
			return;

		animation.update();
		x = Spaceship.getXCord()-3;
		y = Spaceship.getYCord()+12;
	}


	public void draw(Graphics2D g2) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, 45, 45, null);
	}

}
