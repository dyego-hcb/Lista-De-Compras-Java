package Application;

import javax.swing.SwingUtilities;

import UserInterface.ListaDeComprasGUIApp;

public class Main {

	public static void main(String[] args) {
		System.out.println("PROGRAMA INICIADO");
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListaDeComprasGUIApp();
            }
        });
		System.out.println("PROGRAMA FINALIZADO");
	}

}
