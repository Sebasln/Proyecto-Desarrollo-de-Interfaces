package windowContent;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import objects.User;

public class MainWindow extends JFrame {
    private User user;

    public MainWindow(User user) {
        this.user = user;
        initialize();
        ImageIcon icono = new ImageIcon("images/logoNoticias.png");
        this.setIconImage(icono.getImage());
        this.setVisible(true);
    }

    private void initialize() {
        this.setBounds(100, 100, 800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        // Add components here
    }
}
