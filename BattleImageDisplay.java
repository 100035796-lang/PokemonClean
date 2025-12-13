import javax.swing.*;
import java.awt.*;

public class BattleImageDisplay {

    private static JFrame frame;
    private static JLabel attackerLabel;
    private static JLabel defenderLabel;

    public static void initializeWindow() {
        frame = new JFrame("Pokémon Battle Display");
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);

        defenderLabel = new JLabel();

        attackerLabel.setBounds(280, 20, 200, 150);
        attackerLabel.setHorizontalAlignment(JLabel.CENTER);
        attackerLabel.setVerticalAlignment(JLabel.CENTER);

        defenderLabel.setBounds(20, 250, 200, 150);
        defenderLabel.setHorizontalAlignment(JLabel.CENTER);
        defenderLabel.setVerticalAlignment(JLabel.CENTER);

        frame.add(attackerLabel);
        frame.add(defenderLabel);

        frame.setVisible(true);
    }

    public static void updateImages(String bottomLeftPokemon, String topRightPokemon) {

        String atkPath = "src/images/" + topRightPokemon.toLowerCase().replace(" ", "") + "f.png";
        ImageIcon atkIcon = new ImageIcon(atkPath);
        Image atkImage = atkIcon.getImage().getScaledInstance(attackerLabel.getWidth(),
                                                             attackerLabel.getHeight(),
                                                             Image.SCALE_SMOOTH);
        attackerLabel.setIcon(new ImageIcon(atkImage));

        String defPath = "src/images/" + bottomLeftPokemon.toLowerCase().replace(" ", "") + "b.png";
        ImageIcon defIcon = new ImageIcon(defPath);
        Image defImage = defIcon.getImage().getScaledInstance(defenderLabel.getWidth(),
                                                             defenderLabel.getHeight(),
                                                             Image.SCALE_SMOOTH);
        defenderLabel.setIcon(new ImageIcon(defImage));

        frame.repaint();
    }
}
