import java.awt.*;
import javax.swing.*;

public class ScoringPanel extends JPanel{

    private static JLabel scoreL;
    private static JLabel healthL;

	private static JTextField scoreTF;
    private static JTextField healthTF;

    private static int score, health;
    private static int asteroidCollisionHealth = 50;//health
    private static int asteroidCollisionPoints = 20;
    
        public ScoringPanel(){
            
            score = 0;
            health = 300;
    
            scoreL = new JLabel ("Score: ");
            healthL = new JLabel("Health: ");
    
            // create text fields and set their colour, etc.
    
            scoreTF = new JTextField (Integer.toString(score),5);
            scoreTF.setEditable(false);
            scoreTF.setBackground(Color.CYAN);

            healthTF = new JTextField (Integer.toString(score),5);
            healthTF.setEditable(false);
            healthTF.setBackground(Color.CYAN);
            healthTF.setText("300");
    
            GridLayout gridLayout = new GridLayout(1, 2);
            this.setLayout(gridLayout);
    
            this.add(scoreL);
            this.add(scoreTF);
            this.add(healthL);
            this.add(healthTF);
    
        }
    
    public static void addPoints(){
        score+=asteroidCollisionPoints;
        scoreTF.setText(Integer.toString(score));
    }
    public static void yesCollision(){
        if(!Spaceship.isExploded()){
            score+=asteroidCollisionPoints;
            scoreTF.setText(Integer.toString(score));

            health-=asteroidCollisionHealth;
            healthTF.setText(Integer.toString(health));
        }
    }
}
