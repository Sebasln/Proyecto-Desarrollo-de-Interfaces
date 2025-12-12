package main;

import javax.swing.SwingUtilities;
import programLogic.AutoEmailThread;
import windowContent.ProgressBarWindow;

public class Main {

	public static void main(String[] args) {
				
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AutoEmailThread().start();
				new ProgressBarWindow();
			}
		});
	}
}