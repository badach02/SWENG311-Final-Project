import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LandingPagePanel extends JPanel {
    private JButton startButton;

    public LandingPagePanel() {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Car Dealership Program", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("by Daniel Badach and Dan Vanicek", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));  // Spacer
        centerPanel.add(startButton);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}
