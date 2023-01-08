package examen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Daniel Muñoz Barbera
 * Clase que nos permite generar multiples conexiones de los clientes al servidor de forma simultanea e independiente,
 * Junto a la clase AEV2Cliente conforman el nucleo de este ejercicio.
 *
 */
public class AEV2Hilos implements Runnable {
	/**
	 * Objeto socket principal de la clase AEV2Hilos
	 */
	Socket socket;

	/**
	 * @param socket Metodo constructor de la clase AEV2Hilos, nos sirve para crear un objeto con una conexion que
	 * se atribuye a un hilo cada vez que un cliente se conecta al servidor, permitiendo multiples conexiones al 
	 * mismo tiempo.
	 */
	public AEV2Hilos(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Metodo run, lo que hay dentro se ejecuta en cada hilo.
	 */
	public void run() {

		int[] resultados = new int[10];
		boolean opcionCorrecta = false;

		do {

			try {

				ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
				resultados = (int[]) inObject.readObject();

				if (resultados[0] == 0) {
					System.err.println("Turno del Servidor.");

					AEV2Cliente.checkEndGame(resultados);

					System.err.println("Servidor elige numero");
					int posicion = (int) (Math.random() * 9 + 1);
					opcionCorrecta = false;
					// Comprobamos que el servidor elige una opcion correcta
					do {
						if (resultados[posicion] != 0) {
							posicion = (int) (Math.random() * 9 + 1);
						} else {
							opcionCorrecta = true;
						}
					} while (opcionCorrecta == false);
					resultados[posicion] = 2;

					AEV2Cliente.checkEndGame(resultados);

					System.err.println("Pasa el turno.");
					ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
					outObject.writeObject(resultados);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (resultados[0] == 0);

		if (resultados[0] == 1) {
			System.err.println("El Servidor Pierde!");
		} else if (resultados[0] == 2) {
			System.err.println("El Servidor Gana!");
		} else {
			System.err.println("Empate!");
		}

		try {
			ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
			System.err.println("Fin del juego!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
