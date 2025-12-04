package windowContent;
import javax.swing.JWindow;
import javax.swing.JProgressBar;

public class ProgressBarWindow extends JWindow{
	/*Aquí irá la barra de carga del inicio del programa*/
	private JProgressBar progressBar;

	public ProgressBarWindow(JProgressBar progressBar) {
		this.add(progressBar);
		this.setSize(300, 50);
		this.setLocationRelativeTo(null);
		this.setVisible(true);


	}
}
