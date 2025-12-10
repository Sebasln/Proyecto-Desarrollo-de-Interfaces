package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.ImageIcon;
import objects.NewsContent;
import objects.User;
import fileLogic.WebLogic;

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
		this.getContentPane().setBackground(new Color(40, 40, 40));
		this.setBounds(200, 200, 900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel mainLabel = new JLabel("Estas son las noticias más recientes según sus intereses:");
		mainLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setForeground(new Color(255, 255, 255));
		mainLabel.setBounds(10, 11, 864, 77);
		this.getContentPane().add(mainLabel);
		
		JLabel label1 = new JLabel("Cargando noticia...");
		label1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label1.setForeground(Color.WHITE);
		label1.setBounds(50, 100, 380, 26);
		this.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("");
		label2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label2.setForeground(Color.WHITE);
		label2.setBounds(50, 145, 380, 26);
		this.getContentPane().add(label2);
		
		JLabel label3 = new JLabel("");
		label3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label3.setForeground(Color.WHITE);
		label3.setBounds(50, 190, 380, 26);
		this.getContentPane().add(label3);
		
		JLabel label4 = new JLabel("");
		label4.setFont(new Font("Tahoma", Font.BOLD, 13));
		label4.setForeground(Color.WHITE);
		label4.setBounds(50, 235, 380, 26);
		this.getContentPane().add(label4);
		
		JLabel label5 = new JLabel("");
		label5.setFont(new Font("Tahoma", Font.BOLD, 13));
		label5.setForeground(Color.WHITE);
		label5.setBounds(50, 280, 380, 26);
		this.getContentPane().add(label5);
		
		JLabel label6 = new JLabel("");
		label6.setFont(new Font("Tahoma", Font.BOLD, 13));
		label6.setForeground(Color.WHITE);
		label6.setBounds(50, 325, 380, 26);
		this.getContentPane().add(label6);
		
		JLabel label7 = new JLabel("");
		label7.setFont(new Font("Tahoma", Font.BOLD, 13));
		label7.setForeground(Color.WHITE);
		label7.setBounds(50, 370, 380, 26);
		this.getContentPane().add(label7);
		
		JLabel label8 = new JLabel("");
		label8.setFont(new Font("Tahoma", Font.BOLD, 13));
		label8.setForeground(Color.WHITE);
		label8.setBounds(50, 415, 380, 26);
		this.getContentPane().add(label8);
		
		JLabel label9 = new JLabel("");
		label9.setFont(new Font("Tahoma", Font.BOLD, 13));
		label9.setForeground(Color.WHITE);
		label9.setBounds(50, 460, 380, 26);
		this.getContentPane().add(label9);
		
		JLabel label10 = new JLabel("");
		label10.setFont(new Font("Tahoma", Font.BOLD, 13));
		label10.setForeground(Color.WHITE);
		label10.setBounds(470, 100, 380, 26);
		this.getContentPane().add(label10);
		
		JLabel label11 = new JLabel("");
		label11.setFont(new Font("Tahoma", Font.BOLD, 13));
		label11.setForeground(Color.WHITE);
		label11.setBounds(470, 145, 380, 26);
		this.getContentPane().add(label11);
		
		JLabel label12 = new JLabel("");
		label12.setFont(new Font("Tahoma", Font.BOLD, 13));
		label12.setForeground(Color.WHITE);
		label12.setBounds(470, 190, 380, 26);
		this.getContentPane().add(label12);
		
		JLabel label13 = new JLabel("");
		label13.setFont(new Font("Tahoma", Font.BOLD, 13));
		label13.setForeground(Color.WHITE);
		label13.setBounds(470, 235, 380, 26);
		this.getContentPane().add(label13);
		
		JLabel label14 = new JLabel("");
		label14.setFont(new Font("Tahoma", Font.BOLD, 13));
		label14.setForeground(Color.WHITE);
		label14.setBounds(470, 280, 380, 26);
		this.getContentPane().add(label14);
		
		JLabel label15 = new JLabel("");
		label15.setFont(new Font("Tahoma", Font.BOLD, 13));
		label15.setForeground(Color.WHITE);
		label15.setBounds(470, 325, 380, 26);
		this.getContentPane().add(label15);
		
		JLabel label16 = new JLabel("");
		label16.setFont(new Font("Tahoma", Font.BOLD, 13));
		label16.setForeground(Color.WHITE);
		label16.setBounds(470, 370, 380, 26);
		this.getContentPane().add(label16);
		
		JLabel label17 = new JLabel("");
		label17.setFont(new Font("Tahoma", Font.BOLD, 13));
		label17.setForeground(Color.WHITE);
		label17.setBounds(470, 415, 380, 26);
		this.getContentPane().add(label17);
		
		JLabel label18 = new JLabel("");
		label18.setFont(new Font("Tahoma", Font.BOLD, 13));
		label18.setForeground(Color.WHITE);
		label18.setBounds(470, 460, 380, 26);
		this.getContentPane().add(label18);

		JLabel[] labels = {label1, label10, label2, label11, label3, label12, label4, label13, label5, label14, label6, label15, label7, label16, label8, label17, label9, label18};

		JButton btnNewButton = new JButton("Volver al login");
		btnNewButton.setBounds(10, 527, 132, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginWindow();
				dispose();
			}
		});
		this.getContentPane().add(btnNewButton);
	}
}
