package main;

import javax.swing.SwingUtilities;
import programLogic.AutoEmailThread;
import windowContent.ProgressBarWindow;

public class Main {

	public static void main(String[] args) {

		//ANTES DE EJECUTARME REVISA LOS TXT
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AutoEmailThread().start(); // para leer emails
				new ProgressBarWindow(); // para la barra de progreso
			}
		});
	}
}