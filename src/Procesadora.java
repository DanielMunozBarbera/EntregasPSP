import java.util.LinkedList;

/**
 * @author Daniel Muñoz Barbera
 *	Clase procesadora que llamamos desde Lanzadera, controla el tiempo en el que se hacen las croquetas
 */
public class Procesadora {

	static LinkedList<String> listaCroquetas = new LinkedList<>();
	static int capacidad = 100;

	/**
	 * @param lan
	 * @throws InterruptedException
	 * El metodo principal de la clase Procesadora, añade croquetas a una lista y toma el tiempo necesario dependiendo del tipo
	 */
	public void procesar(Lanzadora lan) throws InterruptedException {

		synchronized (this) {

			if (listaCroquetas.size() >= capacidad) {
				Thread.sleep(3000);
			} else {
				listaCroquetas.add(lan.getTipo());
				if (listaCroquetas.getFirst().equals("jamon"))
					Thread.sleep(5000);
				if (listaCroquetas.getFirst().equals("pollo"))
					Thread.sleep(6000);
				if (listaCroquetas.getFirst().equals("bacalao"))
					Thread.sleep(7000);
				if (listaCroquetas.getFirst().equals("queso"))
					Thread.sleep(8000);

				String croqueta = listaCroquetas.removeFirst();
			}

		}
	}

}
