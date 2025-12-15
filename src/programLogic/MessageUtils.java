package programLogic;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageUtils {

    public static void showError(Component parent, String message, Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append("\n\n");
        if (e != null) {
            sb.append("Excepción: ").append(e.toString()).append("\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\tat ").append(ste.toString()).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(false); // Stack traces are better without wrap usually, or we can enable it
        textArea.setWrapStyleWord(true); // If we enable wrap
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(parent, scrollPane, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        showError(parent, message, null);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
