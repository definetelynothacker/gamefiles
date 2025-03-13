import java.awt.*;
import javax.swing.*;

public class ScoringPanel extends JPanel{

    private static JLabel scoreL;
    private static JLabel healthL;
    private static JLabel livesRemL;

	private static JTextField scoreTF;
    private static JTextField healthTF;
    private static JTextField livesRemTF;

    public static int asteroidCollisionPoints = 5;
    public static int asteroidCollisionHealthDecrease = 20;
    public static int laserShotAsteroidScore = 20;
    public static int HEALTH_POINTS = 10;

        public ScoringPanel(){
    
            scoreL = new JLabel ("Score: ");
            healthL = new JLabel("Health: ");
            livesRemL = new JLabel("Lives Remaining: ");
    
            // create text fields and set their colour, etc.
    
            scoreTF = new JTextField (Integer.toString(Spaceship.score),5);
            scoreTF.setEditable(false);
            scoreTF.setBackground(Color.CYAN);

            healthTF = new JTextField (Integer.toString(Spaceship.health),5);
            healthTF.setEditable(false);
            healthTF.setBackground(Color.CYAN);

            livesRemTF = new JTextField(Integer.toString(Spaceship.lives_rem), 5);
            livesRemTF.setEditable(false);
            livesRemTF.setBackground(Color.CYAN);
    
            GridLayout gridLayout = new GridLayout(3, 2);
            this.setLayout(gridLayout);
    
            this.add(scoreL);
            this.add(scoreTF);
            this.add(healthL);
            this.add(healthTF);
            this.add(livesRemL);
            this.add(livesRemTF);
        }

    public static void laserCollisionScore(){
        Spaceship.score+=laserShotAsteroidScore;
        scoreTF.setText(Integer.toString(Spaceship.score));
    }
    public static void addHealth(){
        Spaceship.health+=HEALTH_POINTS;
        healthTF.setText(Integer.toString(Spaceship.health));
    }
    public static void yesAsteroidCollision(){
        if(!Spaceship.getIsExploded()){
            Spaceship.score+=asteroidCollisionPoints;//5
            scoreTF.setText(Integer.toString(Spaceship.score));

            Spaceship.health-=asteroidCollisionHealthDecrease;//-20
            healthTF.setText(Integer.toString(Spaceship.health));
        }
    }
    public static void spaceshipHitByLaser(){
        if(!Spaceship.getIsExploded()){
            Spaceship.score-=asteroidCollisionPoints;//-5
            scoreTF.setText(Integer.toString(Spaceship.score));

            Spaceship.health-=laserShotAsteroidScore;//-20
            healthTF.setText(Integer.toString(Spaceship.health));
        }
    }
    public static void playAgain(){
        Spaceship.playAgain();//reset flags and values to initial
        scoreTF.setText(Integer.toString(Spaceship.score));
        healthTF.setText(Integer.toString(Spaceship.health));
        livesRemTF.setText(Integer.toString(Spaceship.lives_rem));
    }
}
