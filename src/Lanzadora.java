import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Lanzadora {

	/**
	 * @param args
	 * @throws InterruptedException
	 * Metodo main de la clase Lanzadora contiene la mayor parte del programa, bucle while true, recolecta la informacion 
	 * de cuantas croquetas y el tipo, crea un objeto con los datos y en un bucle for crea los Threads a los que les pasa el objeto
	 */
	public static void main(String[] args) throws InterruptedException {
		Procesadora pro = new Procesadora();
		Scanner scan = new Scanner(System.in);
		Thread t1 = null;
		Double tiempo = 0.0;
		while (true) {
			System.out.println("Dime cuantas croquetas quieres");
			int cantidad = scan.nextInt();

			System.out.println("Dime de que tipo quieres las croquetas: jamon - pollo - bacalao - queso: ");
			String tipo = scan.next();

			if (cantidad % 6 == 0) {
				Lanzadora pedido = new Lanzadora(tipo, cantidad);
				Long in = System.currentTimeMillis();
				int cont = pedido.getCantidad();
				for (int i = pedido.getCantidad(); i > 0; i--) {
					t1 = new Thread(new Runnable() {

						public void run() {
							try {
								pro.procesar(pedido);
								System.out.println("Se ha fabricado una croqueta de " + pedido.getTipo());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});

					t1.start();

					t1.join();
				}
				Long fi = System.currentTimeMillis();
				tiempo = tiempo + ((double) ((fi - in) / 1000));
				System.out.println("Quieres añadir mas croquetas al pedido? (y/n) ");
				String seguir = scan.next();
				System.out.println(seguir);
				if (seguir.equals("n")) {
					break;
				}
			} else {
				System.out.println("Las croquetas se empaquetan en cajas de 6, no pueden quedar huecos vacios");
			}

		}
		JOptionPane.showMessageDialog(null, "En total la creacion de croquetas ha tardado: " + tiempo + " segundos");
		scan.close();
	}

	String tipo;
	int cantidad;

	/**
	 * @param tipo
	 * @param cantidad
	 * Metodo constructor de la clase Lanzadora
	 */
	public Lanzadora(String tipo, int cantidad) {
		this.tipo = tipo;
		this.cantidad = cantidad;
	}

	/**
	 * @return
	 * Metodo para recuperar el tipo de croqueta
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return
	 * Metodo para recuperar la cantidad de croquetas que se van a hacer de golpe
	 */
	public int getCantidad() {
		return cantidad;
	}

}
