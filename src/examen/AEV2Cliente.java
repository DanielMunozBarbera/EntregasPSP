package examen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Daniel Muñoz Barbera
 * Clase que define al que va a ser el jugador, crea el entorno grafico y los metodos que hacen posible
 * que se desarrolle la partida
 *
 */
public class AEV2Cliente {

	private JFrame frmAevPsp;
	public static JPanel panel_topleft, panel_topcenter, panel_topright, panel_centerleft, panel_center,
			panel_centerright;
	public static JPanel panel_bottomleft, panel_bottomcenter, panel_bottomright;
	private JLabel lblNewLabel_TopLeft;
	private JLabel lblNewLabel_TopCenter;
	private JLabel lblNewLabel_TopRight;
	private JLabel lblNewLabel_CenterLeft;
	private JLabel lblNewLabel_Center;
	private JLabel lblNewLabel_CenterRight;
	private JLabel lblNewLabel_BottomLeft;
	private JLabel lblNewLabel_BottomCenter;
	private JLabel lblNewLabel_BottomRight;
	public static int[] resultados = new int[10];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AEV2Cliente window = new AEV2Cliente();
					window.frmAevPsp.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		Scanner teclado = new Scanner(System.in);
		resultados[0] = 0;
		boolean opcionCorrecta = false;
		boolean primerTurno = true;
		System.out.println("Jugador >>> Arranca el juego");
		System.out.println("Jugador >>> Conexion al servidor de juego");

		try {
			Socket conexion = new Socket("localhost", 1234);
			
			while (resultados[0] == 0) { // Empieza el bucle del cliente
				
				if (primerTurno == true) {
					primerTurno = false;
				} else {
					ObjectInputStream inObject = new ObjectInputStream(conexion.getInputStream());
					resultados = (int[]) inObject.readObject();
				}
				System.out.println("Turno del Jugador.");
				pintar();
				checkEndGame(resultados);
				if (resultados[0] == 0) {
					System.out.println("Jugador >>> Elige una posicion: ");
					int posicion = Integer.parseInt(teclado.nextLine());
					opcionCorrecta = false;
					// Comprobamos que el jugador elige una opcion correcta
					do {
						if (resultados[posicion] != 0) {
							System.out.println("Jugador >>> Elige una posicion correcta: ");
							posicion = Integer.parseInt(teclado.nextLine());
						} else {
							opcionCorrecta = true;
						}
					} while (opcionCorrecta == false);
					resultados[posicion] = 1;
					pintar();

					checkEndGame(resultados);

					if (resultados[0] == 0) {
						System.out.println("Pasa el turno.");
					}

					ObjectOutputStream outObject = new ObjectOutputStream(conexion.getOutputStream());
					outObject.writeObject(resultados);
				}

			}
			if (resultados[0] == 1) {
				System.out.println("El Jugador gana!");
			} else if (resultados[0] == 2) {
				System.out.println("El Jugador Pierde!");
			} else {
				System.out.println("Empate!");
			}

			ObjectOutputStream finPartida = new ObjectOutputStream(conexion.getOutputStream());
			String fin = "Fin del juego";
			finPartida.writeObject(fin);
			conexion.close();
			teclado.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public AEV2Cliente() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAevPsp = new JFrame();
		frmAevPsp.setAlwaysOnTop(true);
		frmAevPsp.getContentPane().setBackground(new Color(151, 184, 251));
		frmAevPsp.setTitle("AEV2 PSP - Tres en Raya");
		frmAevPsp.setBounds(100, 100, 436, 356);
		frmAevPsp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAevPsp.getContentPane().setLayout(null);

		panel_topleft = new JPanel();
		panel_topleft.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_topleft.setBackground(Color.WHITE);
		panel_topleft.setBounds(81, 61, 68, 61);
		frmAevPsp.getContentPane().add(panel_topleft);

		lblNewLabel_TopLeft = new JLabel("1");
		panel_topleft.add(lblNewLabel_TopLeft);
		lblNewLabel_TopLeft.setFont(new Font("Times New Roman", Font.BOLD, 20));

		panel_topcenter = new JPanel();
		panel_topcenter.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_topcenter.setBackground(Color.WHITE);
		panel_topcenter.setBounds(174, 61, 68, 61);
		frmAevPsp.getContentPane().add(panel_topcenter);

		lblNewLabel_TopCenter = new JLabel("2");
		lblNewLabel_TopCenter.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_topcenter.add(lblNewLabel_TopCenter);

		panel_topright = new JPanel();
		panel_topright.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_topright.setBackground(Color.WHITE);
		panel_topright.setBounds(266, 61, 68, 61);
		frmAevPsp.getContentPane().add(panel_topright);

		lblNewLabel_TopRight = new JLabel("3");
		lblNewLabel_TopRight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_topright.add(lblNewLabel_TopRight);

		panel_centerleft = new JPanel();
		panel_centerleft.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_centerleft.setBackground(Color.WHITE);
		panel_centerleft.setBounds(81, 148, 68, 61);
		frmAevPsp.getContentPane().add(panel_centerleft);

		lblNewLabel_CenterLeft = new JLabel("4");
		lblNewLabel_CenterLeft.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_centerleft.add(lblNewLabel_CenterLeft);

		panel_center = new JPanel();
		panel_center.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_center.setBackground(Color.WHITE);
		panel_center.setBounds(174, 148, 68, 61);
		frmAevPsp.getContentPane().add(panel_center);

		lblNewLabel_Center = new JLabel("5");
		lblNewLabel_Center.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_center.add(lblNewLabel_Center);

		panel_centerright = new JPanel();
		panel_centerright.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_centerright.setBackground(Color.WHITE);
		panel_centerright.setBounds(266, 148, 68, 61);
		frmAevPsp.getContentPane().add(panel_centerright);

		lblNewLabel_CenterRight = new JLabel("6");
		lblNewLabel_CenterRight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_centerright.add(lblNewLabel_CenterRight);

		panel_bottomleft = new JPanel();
		panel_bottomleft.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_bottomleft.setBackground(Color.WHITE);
		panel_bottomleft.setBounds(81, 236, 68, 61);
		frmAevPsp.getContentPane().add(panel_bottomleft);

		lblNewLabel_BottomLeft = new JLabel("7");
		lblNewLabel_BottomLeft.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_bottomleft.add(lblNewLabel_BottomLeft);

		panel_bottomcenter = new JPanel();
		panel_bottomcenter.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_bottomcenter.setBackground(Color.WHITE);
		panel_bottomcenter.setBounds(174, 236, 68, 61);
		frmAevPsp.getContentPane().add(panel_bottomcenter);

		lblNewLabel_BottomCenter = new JLabel("8");
		lblNewLabel_BottomCenter.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_bottomcenter.add(lblNewLabel_BottomCenter);

		panel_bottomright = new JPanel();
		panel_bottomright.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_bottomright.setBackground(Color.WHITE);
		panel_bottomright.setBounds(266, 236, 68, 61);
		frmAevPsp.getContentPane().add(panel_bottomright);

		lblNewLabel_BottomRight = new JLabel("9");
		lblNewLabel_BottomRight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_bottomright.add(lblNewLabel_BottomRight);

		JLabel lblNewLabel = new JLabel("TRES EN RAYA");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 31));
		lblNewLabel.setBounds(100, 27, 253, 22);
		frmAevPsp.getContentPane().add(lblNewLabel);
	}

	/**
	 * Metodo que utilizamos para pintar los JPanels de la interfaz grafica de los colores que correspoden,
	 * Verde para las casillas elegidas por el jugador y Rojo para las casillas que elige el Servidor.
	 */
	public static void pintar() {
		if (resultados[1] == 1)
			panel_topleft.setBackground(Color.GREEN);
		else if (resultados[1] == 2)
			panel_topleft.setBackground(Color.RED);

		if (resultados[2] == 1)
			panel_topcenter.setBackground(Color.GREEN);
		else if (resultados[2] == 2)
			panel_topcenter.setBackground(Color.RED);

		if (resultados[3] == 1)
			panel_topright.setBackground(Color.GREEN);
		else if (resultados[3] == 2)
			panel_topright.setBackground(Color.RED);

		if (resultados[4] == 1)
			panel_centerleft.setBackground(Color.GREEN);
		else if (resultados[4] == 2)
			panel_centerleft.setBackground(Color.RED);

		if (resultados[5] == 1)
			panel_center.setBackground(Color.GREEN);
		else if (resultados[5] == 2)
			panel_center.setBackground(Color.RED);

		if (resultados[6] == 1)
			panel_centerright.setBackground(Color.GREEN);
		else if (resultados[6] == 2)
			panel_centerright.setBackground(Color.RED);

		if (resultados[7] == 1)
			panel_bottomleft.setBackground(Color.GREEN);
		else if (resultados[7] == 2)
			panel_bottomleft.setBackground(Color.RED);

		if (resultados[8] == 1)
			panel_bottomcenter.setBackground(Color.GREEN);
		else if (resultados[8] == 2)
			panel_bottomcenter.setBackground(Color.RED);

		if (resultados[9] == 1)
			panel_bottomright.setBackground(Color.GREEN);
		else if (resultados[9] == 2)
			panel_bottomright.setBackground(Color.RED);
	}

	/**
	 * @param resultados Metodo que utilizamos para comprobar si la partida ha llegado a su fin, ya sea por que hay un ganador,
	 * comprueba las 8 combinaciones posibles de victoria a las que el jugador o el servidor pueden llegar o tambien
	 * comprueba si se han marcado todas las casillas y no hay ganador de forma que la partida termine en empate.
	 */
	public static void checkEndGame(int[] resultados) {

		if (resultados[1] != 0 && resultados[2] != 0 && resultados[3] != 0 && resultados[4] != 0 && resultados[5] != 0
				&& resultados[6] != 0 && resultados[7] != 0 && resultados[8] != 0 && resultados[9] != 0) {
			resultados[0] = 3;
		}

		if (resultados[1] == 1 && resultados[2] == 1 && resultados[3] == 1)
			resultados[0] = 1;
		if (resultados[1] == 2 && resultados[2] == 2 && resultados[3] == 2)
			resultados[0] = 2;

		if (resultados[1] == 1 && resultados[4] == 1 && resultados[7] == 1)
			resultados[0] = 1;
		if (resultados[1] == 2 && resultados[4] == 2 && resultados[7] == 2)
			resultados[0] = 2;

		if (resultados[1] == 1 && resultados[5] == 1 && resultados[9] == 1)
			resultados[0] = 1;
		if (resultados[1] == 2 && resultados[5] == 2 && resultados[9] == 2)
			resultados[0] = 2;

		if (resultados[2] == 1 && resultados[5] == 1 && resultados[8] == 1)
			resultados[0] = 1;
		if (resultados[2] == 2 && resultados[5] == 2 && resultados[8] == 2)
			resultados[0] = 2;

		if (resultados[3] == 1 && resultados[6] == 1 && resultados[9] == 1)
			resultados[0] = 1;
		if (resultados[3] == 2 && resultados[6] == 2 && resultados[9] == 2)
			resultados[0] = 2;

		if (resultados[3] == 1 && resultados[5] == 1 && resultados[7] == 1)
			resultados[0] = 1;
		if (resultados[3] == 2 && resultados[5] == 2 && resultados[7] == 2)
			resultados[0] = 2;

		if (resultados[4] == 1 && resultados[5] == 1 && resultados[6] == 1)
			resultados[0] = 1;
		if (resultados[4] == 2 && resultados[5] == 2 && resultados[6] == 2)
			resultados[0] = 2;

		if (resultados[7] == 1 && resultados[8] == 1 && resultados[9] == 1)
			resultados[0] = 1;
		if (resultados[7] == 2 && resultados[8] == 2 && resultados[9] == 2)
			resultados[0] = 2;

	}
}
