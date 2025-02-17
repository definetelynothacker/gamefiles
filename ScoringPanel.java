import java.awt.*;
import javax.swing.*;

public class ScoringPanel extends JPanel{

    private static JLabel scoreL;
    private static JLabel healthL;

	private static JTextField scoreTF;
    private static JTextField healthTF;

    private static int score, health;
    
        public ScoringPanel(){
            
            score = 0;
            health = 10000;
    
            scoreL = new JLabel ("Score: ");
            healthL = new JLabel("Health: ");
    
            // create text fields and set their colour, etc.
    
            scoreTF = new JTextField (Integer.toString(score),5);
            scoreTF.setEditable(false);
            scoreTF.setBackground(Color.CYAN);

            healthTF = new JTextField (Integer.toString(score),5);
            healthTF.setEditable(false);
            healthTF.setBackground(Color.CYAN);
    
            GridLayout gridLayout = new GridLayout(1, 2);
            this.setLayout(gridLayout);
    
            this.add(scoreL);
            this.add(scoreTF);
            this.add(healthL);
            this.add(healthTF);
    
        }
    
    public static void addPoints(int points){
        score+=points;
        scoreTF.setText(Integer.toString(score));
    }
    public static void subtractHealth(int points){
        health-=points;
        healthTF.setText(Integer.toString(health));
    }
}
