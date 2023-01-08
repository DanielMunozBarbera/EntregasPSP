package examen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AEV2Servidor {

	/**
	 * @param args
	 * @throws IOException
	 * Metodo main de la clase AEV2Servidor, es la ejecucion del servidor en si misma, se conecta a la escucha del 
	 * puerto 1234 y genera un hilo para cada conexion que recibe, siempre se mantendra a la espera de una nueva conexion.
	 */
	public static void main(String[] args) throws IOException {
		System.err.println("SERVIDOR >>> Arranca el servidor, espera peticion de juego");
		ServerSocket socketEscucha = null;
		try {
			socketEscucha = new ServerSocket(1234);
		}catch(IOException e) {
			System.err.println("SERVIDOR >>> Error");
			return;
		}
		while(true) {
			Socket conexion = socketEscucha.accept();
			System.err.println("SERVIDOR >>> Conexion recibida -> Lanza partida(hilo)");
			
			AEV2Hilos p = new AEV2Hilos(conexion);
			Thread hilo = new Thread(p);
			hilo.start();
			
		}
	}

}
