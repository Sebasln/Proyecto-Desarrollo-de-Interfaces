package main;

import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args) {
				
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new programLogic.AutoEmailThread().start();
				System.out.println("Creando barra de progreso...");
				new windowContent.ProgressBarWindow();
			}
		});
	}

	

}