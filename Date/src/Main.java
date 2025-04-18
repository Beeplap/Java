import javax.swing.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Current Date and Time");
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on scree

        Date currentDate = new Date();

        JLabel label = new JLabel("Current Date and Time: " + currentDate.toString());
        label.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(label);

        frame.setVisible(true);
    }
}
