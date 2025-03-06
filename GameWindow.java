import java.awt.*;			// need this for GUI objects
import java.awt.event.*;			// need this for Layout Managers
import javax.swing.*;		// need this to respond to GUI events
	
public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener//,
					   //MouseListener
{
	// declare instance variables for user interface objects

	// declare labels 
	public static boolean spacePressed;

	private JLabel statusBarL;
	private JLabel keyL;
	private JLabel mouseL;

	// declare text fields

	private final JTextField statusBarTF;
	private final JTextField keyTF;
	private final JTextField mouseTF;

	// declare buttons

	private final JButton startB;
	private final JButton dropAsteroid;
	private final JButton playAgain;//"play again"
	private final JButton focusB;
	private final JButton exitB;

	private Container c;

	private final JPanel mainPanel;
	private final GamePanel gamePanel;
	private final ScoringPanel scorePanel;
	private final AmmoPanel ammoPanel;


	@SuppressWarnings({"unchecked"})
	public GameWindow() {

		spacePressed = false;
 
		setTitle ("A Game with a Bat and an Alien");
		setSize (750, 750);

		// create user interface objects

		// create labels

		statusBarL = new JLabel ("Application Status: ");
		keyL = new JLabel("Key Pressed: ");
		mouseL = new JLabel("Location of Mouse Click: ");

		// create text fields and set their colour, etc.

		statusBarTF = new JTextField (25);
		keyTF = new JTextField (25);
		mouseTF = new JTextField (25);

		statusBarTF.setEditable(false);
		keyTF.setEditable(false);
		mouseTF.setEditable(false);

		statusBarTF.setBackground(Color.CYAN);
		keyTF.setBackground(Color.YELLOW);
		mouseTF.setBackground(Color.GREEN);

		// create buttons

		startB = new JButton ("Show Spaceship");
	    dropAsteroid = new JButton ("Drop Asteroids");
		playAgain = new JButton("Play Again");
	    focusB = new JButton ("Focus on Key");
		exitB = new JButton ("Exit");

		// add listener to each button (same as the current object)

		startB.addActionListener(this);
		dropAsteroid.addActionListener(this);
		playAgain.addActionListener(this);
		focusB.addActionListener(this);
		exitB.addActionListener(this);

		
		// create mainPanel

		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		GridLayout gridLayout;

		// create the gamePanel for game entities

		gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(400, 400));

		scorePanel = new ScoringPanel();
		scorePanel.setPreferredSize(new Dimension(150, 150));

		ammoPanel = new AmmoPanel();
		ammoPanel.setPreferredSize(new Dimension(150, 150));

		// create infoPanel

		JPanel infoPanel = new JPanel();
		gridLayout = new GridLayout(3, 2);
		infoPanel.setLayout(gridLayout);
		infoPanel.setBackground(Color.ORANGE);

		// add user interface objects to infoPanel
	
		infoPanel.add (statusBarL);
		infoPanel.add (statusBarTF);

		infoPanel.add (keyL);
		infoPanel.add (keyTF);		

		infoPanel.add (mouseL);
		infoPanel.add (mouseTF);

		
		// create buttonPanel

		JPanel buttonPanel = new JPanel();
		gridLayout = new GridLayout(1, 4);
		buttonPanel.setLayout(gridLayout);

		// add buttons to buttonPanel

		buttonPanel.add(startB);
		buttonPanel.add(dropAsteroid);
		buttonPanel.add(playAgain);
		buttonPanel.add(focusB);
		buttonPanel.add(exitB);

		// add sub-panels with GUI objects to mainPanel and set its colour

		mainPanel.add(infoPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(scorePanel);
		mainPanel.add(ammoPanel);
		mainPanel.add(buttonPanel);
		mainPanel.setBackground(Color.PINK);

		// set up mainPanel to respond to keyboard and mouse

		//gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		// add mainPanel to window surface

		c = getContentPane();
		c.add(mainPanel);

		// set properties of window

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		gamePanel.createGameEntities();

		// set status bar message

		statusBarTF.setText("Application started.");
	}


	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e){

		String command = e.getActionCommand();
		
		statusBarTF.setText(command + " button clicked.");

		if (command.equals(focusB.getText()))
			mainPanel.requestFocus();

		if (command.equals(startB.getText()))
			gamePanel.gameRender();

		if (command.equals(dropAsteroid.getText())){
			gamePanel.dropAsteroid();
		}
		if(command.equals(playAgain.getText())){
			gamePanel.playAgain();
		}
		if (command.equals(exitB.getText()))
			System.exit(0);

		mainPanel.requestFocus();
	}


	// implement methods in KeyListener interface

	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		keyTF.setText(keyText + " pressed.");

		if (keyCode == KeyEvent.VK_LEFT){
			gamePanel.updateSpaceship(1);
			gamePanel.gameRender();
		}
		if (keyCode == KeyEvent.VK_RIGHT){
			gamePanel.updateSpaceship(2);
			gamePanel.gameRender();
		}
		if(keyCode == KeyEvent.VK_DOWN){
			gamePanel.updateSpaceship(3);
			gamePanel.gameRender();
		}
		if(keyCode == KeyEvent.VK_UP){
			gamePanel.updateSpaceship(4);
			gamePanel.gameRender();
		}
		if(keyCode == KeyEvent.VK_SPACE){
			spacePressed = true;
		}
	}
	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	// implement methods in MouseListener interface
/*
	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		if (gamePanel.isOnBat(x, y)) {
			statusBarTF.setText ("Mouse click on bat!");
			statusBarTF.setBackground(Color.RED);
		}
		else {
			statusBarTF.setText ("");
			statusBarTF.setBackground(Color.CYAN);
		}

		mouseTF.setText("(" + x +", " + y + ")");

	}*/


	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

}