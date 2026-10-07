import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class App {
    JFrame frame = new JFrame("Homepage");
    JPanel panel = new JPanel();
    JButton button = new JButton("Open Coverage Calculator");
    JButton button2 = new JButton("Open defensive Coverage Calculator");

    public App() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel.add(button);
        panel.add(button2);
        frame.add(panel);

        button.addActionListener(e -> {
            new coverageCalc();
        });
        button2.addActionListener(e -> {
            new defensiveCalc();
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}

