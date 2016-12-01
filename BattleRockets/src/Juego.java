import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Aitor Ugarte
 * Juego para prog1 de la UD
 */
public class Juego {

	// Declaramos las variables
	private static double player1X = 200, player1Y = 300; // Coordenadas
															// jugador1
	private static double player2X = 200, player2Y = 200; // Coordenadas
															// jugador2
	private static double rocket1X = 0, rocket1Y = 0; // Coordenadas cohete 1
	private static double rocket2X = 0, rocket2Y = 0; // Coordenadas cohete 2
	private static int score1 = 0, score2 = 0, time = 1000;
	private static String user1Image = "images/player1/user1-right.png";
	private static String user2Image = "images/player2/user2-right.png";
	private static String rocketImage1 = "images/player1/rocket1-right.png";
	private static String rocketImage2 = "images/player2/rocket2-right.png";
	private static char lastPosition1 = ' '; // Orientación jugador1
	private static char lastPosition2 = ' '; // Orientación jugador2
	private static int cont1 = 0; // Con este valor el cohete 1 se dispara
	private static int cont2 = 0; // Con este valor el cohete 2 se dispara

	public static void main(String[] args) {

		// Sacamos las dimensiones de la pantalla
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = (int) (screenSize.width * 0.5);
		int alto = (int) (screenSize.height * 0.5);
		// Ajustamos la ventana a la pantalla
		StdDraw.setCanvasSize(ancho, alto);

		// Definimos los máximos y los mínimos
		StdDraw.setXscale(0, ancho);
		StdDraw.setYscale(0, alto);
		// Modo sin rastro activado
		StdDraw.enableDoubleBuffering();

		// Movimiento de los usuarios
		while (true) {
			// Eventos de teclado
			if (StdDraw.isKeyPressed(10)) { // Enter
				restart();
			}
			if (StdDraw.isKeyPressed(32)) { // Barra espaciadora
											// Jugador 1 dispara
				disparoJ1();
				
				// Ajustamos el límite de vuelo del cochete
				if (rocket1X > ancho || rocket1X < 0 || rocket1Y > alto || rocket1Y < 0) {
					rocket1X = -1000;
					rocket1Y = -1100;
					cont1 = 0;
				}
			}
			//Reseteo del cohete 1
			if (StdDraw.isKeyPressed(16)){ // Shift
				rocketImage1 = "images/vacio.png";
				cont1 = 0;
			}
			//Reseteo del cohete 2
			if (StdDraw.isKeyPressed(107)) { // Símbolo suma (+)
				rocketImage2 = "images/vacio.png";
				cont2 = 0;
			}
			
			if (StdDraw.isKeyPressed(96)) { // Numpad 0
											// Jugador 2 dispara

				disparoJ2();

				// Ajustamos el límite de vuelo del cochete
				if (rocket2X > ancho || rocket2X < 0 || rocket2Y > alto || rocket2Y < 0) {
					rocket2X = -1000;
					rocket2Y = -1100;
					cont2 = 0;
				}
			}
			if (StdDraw.isKeyPressed(68)) { // Letra D
				user1Image = "images/player1/user1-right.png";
				lastPosition1 = 'r';
				
				if (player1X < (ancho - 20)){
					player1X += 10;
				}
			}
			if (StdDraw.isKeyPressed(65)) { // Letra A
				user1Image = "images/player1/user1-left.png";
				lastPosition1 = 'l';
				
				if (player1X > 20){
					player1X -= 10;
				}
			}
			if (StdDraw.isKeyPressed(87)) { // Letra W
				user1Image = "images/player1/user1-up.png";
				lastPosition1 = 'u';
				
				if (player1Y < (alto - 20)){
					player1Y += 10;
				}
			}
			if (StdDraw.isKeyPressed(83)) { // Letra S
		
				user1Image = "images/player1/user1-down.png";
				lastPosition1 = 'd';
				
				if (player1Y > 20){
					player1Y -= 10;
				}
			}
			if (StdDraw.isKeyPressed(39)) { // Flecha Derecha
				user2Image = "images/player2/user2-right.png";
				lastPosition2 = 'r';
				
				if (player2X < (ancho - 20)){
					player2X += 10;
				}
			}
			if (StdDraw.isKeyPressed(37)) { // Flecha Izquierda
				user2Image = "images/player2/user2-left.png";
				lastPosition2 = 'l';
				
				if (player2X > 20){
					player2X -= 10;
				}
			}
			if (StdDraw.isKeyPressed(38)) { // Flecha Arriba
				user2Image = "images/player2/user2-up.png";
				lastPosition2 = 'u';
				
				if (player2Y < (alto - 20)){
					player2Y += 10;
				}
			}
			if (StdDraw.isKeyPressed(40)) { // Flecha Abajo
				user2Image = "images/player2/user2-down.png";
				lastPosition2 = 'd';
				
				if (player2Y > 20){
					player2Y -= 10;
				}
			}

			// Dibujamos el contenido
			StdDraw.clear(StdDraw.BOOK_BLUE);
			StdDraw.picture(player2X, player2Y, user2Image);
			StdDraw.picture(player1X, player1Y, user1Image);
			StdDraw.picture(rocket1X, rocket1Y, rocketImage1);
			StdDraw.picture(rocket2X, rocket2Y, rocketImage2);
			StdDraw.text(60, 10, "TIME: " + time);
			StdDraw.text(660, 10, "SCORE: " + score1);

			StdDraw.show();
			StdDraw.pause(20);
			// El tiempo aumenta
			// time--;
			// Revisar si el tiempo se acaba
			if (time < 0) {
				break;
			}
		}
	}

	/*
	 * Método de los movimientos del cohete del jugador 1
	 */
	public static void disparoJ1() {
		// Primero apuntamos el cohete
		if (getLastPosition1() == 'r') {
			// Cargamos la imagen del cohete
			rocketImage1 = "images/player1/rocket1-right.png";

			// Genera el cohete delante del jugador
			if (cont1 == 0) {
				rocket1X = player1X + 40;
				rocket1Y = player1Y;
				cont1 = 1;
			}
			// Lo disparamos
			rocket1X += 10;
		} else if (getLastPosition1() == 'l') {
			rocketImage1 = "images/player1/rocket1-left.png";
			if (cont1 == 0) {
				rocket1X = player1X - 40;
				rocket1Y = player1Y;
				cont1 = 1;
			}
			rocket1X -= 10;
		} else if (getLastPosition1() == 'u') {
			rocketImage1 = "images/player1/rocket1-up.png";
			if (cont1 == 0) {
				rocket1X = player1X;
				rocket1Y = player1Y + 40;
				cont1 = 1;
			}
			rocket1Y += 10;
		} else if (getLastPosition1() == 'd') {
			rocketImage1 = "images/player1/rocket1-down.png";
			if (cont1 == 0) {
				rocket1X = player1X;
				rocket1Y = player1Y - 40;
				cont1 = 1;
			}
			rocket1Y -= 10;
		}
	}

	/*
	 * Método de los movimientos del cohete del jugador 2
	 */
	public static void disparoJ2() {
		// Primero apuntamos el cohete
		if (getLastPosition2() == 'r') {
			// Cargamos la imagen del cohete
			rocketImage2 = "images/player2/rocket2-right.png";

			// Genera el cohete delante del jugador
			if (cont2 == 0) {
				rocket2X = player2X + 40;
				rocket2Y = player2Y;
				cont2 = 1;
			}
			// Lo disparamos
			rocket2X += 10;
		} else if (getLastPosition2() == 'l') {
			rocketImage2 = "images/player2/rocket2-left.png";
			if (cont2 == 0) {
				rocket2X = player2X - 40;
				rocket2Y = player2Y;
				cont2 = 1;
			}
			rocket2X -= 10;
		} else if (getLastPosition2() == 'u') {
			rocketImage2 = "images/player2/rocket2-up.png";
			if (cont2 == 0) {
				rocket2X = player2X;
				rocket2Y = player2Y + 40;
				cont2 = 1;
			}
			rocket2Y += 10;
		} else if (getLastPosition2() == 'd') {
			rocketImage2 = "images/player2/rocket2-down.png";
			if (cont2 == 0) {
				rocket2X = player2X;
				rocket2Y = player2Y - 40;
				cont2 = 1;
			}
			rocket2Y -= 10;
		}
	}

	public static void restart() {

	}

	public static char getLastPosition1() {
		return lastPosition1;
	}

	public static char getLastPosition2() {
		return lastPosition2;
	}

}
