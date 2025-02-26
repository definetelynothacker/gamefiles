import java.awt.GridLayout;
import javax.swing.*;

public class AmmoPanel extends JPanel{

    private static final int AMT_AMMO_PCKG=35;//35 shots

    private static JLabel amtAmmoL;
    private static JTextField amtAmmoTF; 

    public AmmoPanel(){

        amtAmmoL = new JLabel("# Ammo: ");
        amtAmmoTF = new JTextField(Integer.toString(Spaceship.amtLasers), 5);

        GridLayout newGridLayout = new GridLayout(1, 2);
        this.setLayout(newGridLayout);
        this.add(amtAmmoL);
        this.add(amtAmmoTF);
    }
    public static void collectAmmoPackage(){
        Spaceship.amtLasers+=AMT_AMMO_PCKG;
        amtAmmoTF.setText(Integer.toString(Spaceship.amtLasers));
    }
    public static void decreaseAmmo(){
        Spaceship.amtLasers-=1;
        amtAmmoTF.setText(Integer.toString(Spaceship.amtLasers));
    }
}
