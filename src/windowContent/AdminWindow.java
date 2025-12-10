package windowContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import objects.User;

public class AdminWindow extends JFrame {

    private User user;    
    private JLayeredPane menuPane;
    private JLayeredPane createUserPane;
    private JLayeredPane deleteUserPane;
    
    private JTextField usernameTField;
    private JTextField deleteTField;
    private JTextField passwordTField;

    public AdminWindow(User user) {
        this.user = user;
        initialize();
        ImageIcon icono = new ImageIcon("images/logoNoticias.png");
        this.setIconImage(icono.getImage());
        this.setVisible(true);
    }

    private void initialize() {
        this.setBounds(100, 100, 600, 400); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null); 
        this.setLocationRelativeTo(null);
        
        menuPane = new JLayeredPane();
        menuPane.setLayout(null);
        menuPane.setOpaque(true); 
        menuPane.setBackground(new Color(40, 40, 40));
        menuPane.setBounds(0, 0, 584, 361); 
        this.getContentPane().add(menuPane);
        
        JLabel headerAdminLbl = new JLabel("Menú de admin");
        headerAdminLbl.setHorizontalAlignment(SwingConstants.CENTER);
        headerAdminLbl.setForeground(Color.WHITE);
        headerAdminLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
        headerAdminLbl.setBounds(10, 11, 566, 73);
        menuPane.add(headerAdminLbl);
        
        JButton goToCreateUserBtn = new JButton("Crear usuario");
        goToCreateUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        goToCreateUserBtn.setForeground(new Color(100, 100, 100));
        goToCreateUserBtn.setBackground(new Color(255, 255, 255));
        goToCreateUserBtn.setBounds(87, 156, 137, 85);
        menuPane.add(goToCreateUserBtn);
        
        JButton goToDeleteUserBtn = new JButton("Borrar usuario");
        goToDeleteUserBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        goToDeleteUserBtn.setForeground(new Color(0, 0, 0));
        goToDeleteUserBtn.setBackground(new Color(255, 255, 255));
        goToDeleteUserBtn.setBounds(358, 156, 137, 85);
        menuPane.add(goToDeleteUserBtn);
        
        JButton backMainBtn = new JButton("Ir al main");
        backMainBtn.setBounds(10, 330, 98, 22);
        menuPane.add(backMainBtn);
        
        createUserPane = new JLayeredPane();
        createUserPane.setLayout(null);
        createUserPane.setOpaque(true); 
        createUserPane.setBackground(new Color(40, 40, 40));
        createUserPane.setBounds(0, 0, 584, 361); 
        createUserPane.setVisible(false); 
        this.getContentPane().add(createUserPane);
        
        JLabel createUserLbl = new JLabel("Crear un usuario nuevo");
        createUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
        createUserLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
        createUserLbl.setForeground(new Color(255, 255, 255));
        createUserLbl.setBounds(10, 0, 566, 73);
        createUserPane.add(createUserLbl);
    
        JLabel createUsernameLbl = new JLabel("Introduzca el nombre del nuevo usuario:");
        createUsernameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
        createUsernameLbl.setForeground(new Color(255, 255, 255));
        createUsernameLbl.setHorizontalAlignment(SwingConstants.LEFT);
        createUsernameLbl.setBounds(164, 93, 280, 14);
        createUserPane.add(createUsernameLbl);
        
        usernameTField = new JTextField();
        usernameTField.setBounds(252, 127, 86, 20);
        createUserPane.add(usernameTField);
        usernameTField.setColumns(10);
        
        JButton backFromCreateBtn = new JButton("Volver");
        backFromCreateBtn.setBounds(10, 330, 88, 22);
        createUserPane.add(backFromCreateBtn);
        
        JLabel passwordLbl = new JLabel("Introduzca la contraseña del usuario:");
        passwordLbl.setHorizontalAlignment(SwingConstants.LEFT);
        passwordLbl.setForeground(Color.WHITE);
        passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
        passwordLbl.setBounds(164, 172, 280, 14);
        createUserPane.add(passwordLbl);
        
        passwordTField = new JTextField();
        passwordTField.setColumns(10);
        passwordTField.setBounds(252, 211, 86, 20);
        createUserPane.add(passwordTField);
        
        JButton createUserBtn = new JButton("Crear");
        createUserBtn.setBounds(252, 290, 88, 22);
        createUserPane.add(createUserBtn);
        
        deleteUserPane = new JLayeredPane();
        deleteUserPane.setLayout(null);
        deleteUserPane.setOpaque(true);
        deleteUserPane.setBackground(new Color(40, 40, 40));
        deleteUserPane.setBounds(0, 0, 584, 361); 
        deleteUserPane.setVisible(false); 
        this.getContentPane().add(deleteUserPane);
        
        JLabel deleteUserLbl = new JLabel("Eliminar un usario");
        deleteUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
        deleteUserLbl.setForeground(Color.WHITE);
        deleteUserLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
        deleteUserLbl.setBounds(10, 11, 566, 73);
        deleteUserPane.add(deleteUserLbl);
        
        JLabel deleteUsernameLbl = new JLabel("Introduzca el nombre del usuario al borrar:");
        deleteUsernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
        deleteUsernameLbl.setForeground(Color.WHITE);
        deleteUsernameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
        deleteUsernameLbl.setBounds(10, 153, 320, 14);
        deleteUserPane.add(deleteUsernameLbl);
        
        deleteTField = new JTextField();
        deleteTField.setColumns(10);
        deleteTField.setBounds(340, 151, 86, 20);
        deleteUserPane.add(deleteTField);
        
        JButton backFromDeleteBtn = new JButton("Volver");
        backFromDeleteBtn.setBounds(10, 330, 88, 22);
        deleteUserPane.add(backFromDeleteBtn);
        
        JButton btnBorrarAccion = new JButton("Borrar");
        btnBorrarAccion.setBounds(340, 242, 88, 22);
        deleteUserPane.add(btnBorrarAccion);
        
        
        goToCreateUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPane.setVisible(false);
                createUserPane.setVisible(true);
                deleteUserPane.setVisible(false);
            }
        });
        
        goToDeleteUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPane.setVisible(false);
                createUserPane.setVisible(false);
                deleteUserPane.setVisible(true);
            }
        });
        
        backFromCreateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUserPane.setVisible(false);
                menuPane.setVisible(true);
            }
        });
        
        backFromDeleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserPane.setVisible(false);
                menuPane.setVisible(true);
            }
        });
    }
}
