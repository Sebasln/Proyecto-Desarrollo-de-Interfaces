package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import objects.User;
import fileLogic.UserLogic;

public class UserConfigWindow extends JFrame {

    private User user;

    public UserConfigWindow(User user) {
        this.user = user;
        
        
        initialize();
        ImageIcon icono = new ImageIcon("images/logoNoticias.png");
        this.setIconImage(icono.getImage());
        this.setVisible(true);
    }

    private void initialize() {
        this.getContentPane().setBackground(new Color(40, 40, 40));
        this.setBounds(100, 100, 450, 300);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Confirmación al cerrar
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (javax.swing.JOptionPane.showConfirmDialog(null, 
					"¿Estás seguro de que quieres salir?", "Cerrar Aplicación", 
					javax.swing.JOptionPane.YES_NO_OPTION,
					javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);

        JLabel headerLabel = new JLabel("Elija el tipo de noticias que le gustaría ver:");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        headerLabel.setForeground(new Color(255, 255, 255));
        headerLabel.setBounds(10, 11, 416, 69);
        this.getContentPane().add(headerLabel);

        JCheckBox economyCBox = new JCheckBox("Economía");
        economyCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        economyCBox.setBackground(new Color(40, 40, 40));
        economyCBox.setForeground(new Color(255, 255, 255));
        economyCBox.setBounds(23, 87, 98, 22);
        this.getContentPane().add(economyCBox);

        JCheckBox sportsCBox = new JCheckBox("Deportes");
        sportsCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        sportsCBox.setBackground(new Color(40, 40, 40));
        sportsCBox.setForeground(new Color(255, 255, 255));
        sportsCBox.setBounds(23, 138, 98, 22);
        this.getContentPane().add(sportsCBox);

        JCheckBox nationalCBox = new JCheckBox("Nacional");
        nationalCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        nationalCBox.setBackground(new Color(40, 40, 40));
        nationalCBox.setForeground(new Color(255, 255, 255));
        nationalCBox.setBounds(153, 87, 118, 22);
        this.getContentPane().add(nationalCBox);

        JCheckBox internationalCBox = new JCheckBox("Internacional");
        internationalCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        internationalCBox.setBackground(new Color(40, 40, 40));
        internationalCBox.setForeground(new Color(255, 255, 255));
        internationalCBox.setBounds(153, 138, 118, 22);
        this.getContentPane().add(internationalCBox);

        JCheckBox videogamesCBox = new JCheckBox("Videojuegos");
        videogamesCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        videogamesCBox.setBackground(new Color(40, 40, 40));
        videogamesCBox.setForeground(new Color(255, 255, 255));
        videogamesCBox.setBounds(290, 87, 114, 22);
        this.getContentPane().add(videogamesCBox);

        JCheckBox techCBox = new JCheckBox("Tecnología");
        techCBox.setFont(new Font("Tahoma", Font.BOLD, 12));
        techCBox.setBackground(new Color(40, 40, 40));
        techCBox.setForeground(new Color(255, 255, 255));
        techCBox.setBounds(290, 138, 114, 22);
        this.getContentPane().add(techCBox);

        JButton submitCategoriesBtn = new JButton("Seleccionar");
        submitCategoriesBtn.setBounds(167, 214, 88, 22);
        this.getContentPane().add(submitCategoriesBtn);

        JLabel emptinessWarningLabel = new JLabel("Seleccione al menos una categoría");
		emptinessWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		emptinessWarningLabel.setVisible(false);
		emptinessWarningLabel.setForeground(new Color(255, 0, 0));
		emptinessWarningLabel.setBounds(124, 199, 158, 14);
		this.getContentPane().add(emptinessWarningLabel);


        submitCategoriesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(economyCBox.isSelected()) {
                    user.getPreferencesList().add("ECONOMIA");
                }
                if(sportsCBox.isSelected()) {
                    user.getPreferencesList().add("DEPORTES");
                }
                if(nationalCBox.isSelected()) {
                    user.getPreferencesList().add("NACIONAL");
                }
                if(internationalCBox.isSelected()) {
                    user.getPreferencesList().add("INTERNACIONAL");
                }
                if(videogamesCBox.isSelected()) {
                    user.getPreferencesList().add("VIDEOJUEGOS");
                }
                if(techCBox.isSelected()) {
                    user.getPreferencesList().add("TECNOLOGIA");
                }

                if(user.getPreferencesList().isEmpty()) {
                    emptinessWarningLabel.setVisible(true); 
                    return;
                }

                user.setNew(false);
                UserLogic.writeUserPreferences(user);
                emptinessWarningLabel.setVisible(false); 
                dispose();
            }
        });
    }
}