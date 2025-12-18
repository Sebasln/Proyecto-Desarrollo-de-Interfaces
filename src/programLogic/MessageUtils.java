package programLogic;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageUtils {

    public static void showError(Component parent, String message, Throwable e) {
        String finalMessage = message;
        if (e != null) {
            finalMessage += "\nDetalles: " + e.toString();
        }

        JTextArea textArea = new JTextArea(finalMessage);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(null);
        textArea.setFont(new JLabel().getFont());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(400, 60));

        JOptionPane.showMessageDialog(parent, scrollPane, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        showError(parent, message, null);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}