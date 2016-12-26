import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * @author Aitor Ugarte
 * Juego para prog1 de la UD
 */
public class Juego {

	// Declaramos las variables
	private Jugador player1 = null;
	private Jugador player2 = null;
	private String nombre1 = "", nombre2 = "";
	private int player1X = 200, player1Y = 300; // Coordenadas iniciales jugador1
	private int player2X = 200, player2Y = 200; // Coordenadas iniciales jugador2	
	private int rocket1X = 0, rocket1Y = 0; // Coordenadas iniciales cohete 1
	private int rocket2X = 0, rocket2Y = 0; // Coordenadas iniciales cohete 2
	private int score1 = 0, score2 = 0, time = 2000;
	private String user1Image = "images/player1/user1-right.png";
	private String user2Image = "images/player2/user2-right.png";
	private String rocketImage1 = "images/player1/rocket1-right.png";
	private String rocketImage2 = "images/player2/rocket2-right.png";
	private char lastPosition1 = 'r'; // Orientación jugador1
	private char lastPosition2 = 'r'; // Orientación jugador2
	private int cont1 = 0; // Con este valor el cohete 1 se dispara
	private int cont2 = 0; // Con este valor el cohete 2 se dispara
	private boolean lanzado1 = false; //Estado del cohete 1
	private boolean lanzado2 = false; //Estado del cohete 2
	private int movimiento;

	/**
	 * Método que carga el juego
	 * @param tipo 1 si jugador vs jugador, 2 si jugador vs IA
	 */
	public void cargarJuego(int tipo) {

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

		//Cargamos los jugadores
		player1 = new Jugador("images/player1/user1-right.png", 200, 300, 0);
		player2 = new Jugador("images/player2/user2-right.png", 200, 200, 0);
		
		// Movimiento de los usuarios
		while (true) {
			
			movimientoCohete1();
			movimientoCohete2();
		
			limitesVuelo(ancho, alto);
			if(tipo == 2){
				movimiento = movimientoIA();
			}
			
			// Eventos de teclado
			if (StdDraw.isKeyPressed(32) && lanzado1 == false) { // Barra espaciadora
																// Jugador 1 dispara
				disparoJ1();
			}
			if ((StdDraw.isKeyPressed(96) || movimiento == 5) && lanzado2 == false ) { // Numpad 0
																// Jugador 2 dispara
				disparoJ2();
			}
			if (StdDraw.isKeyPressed(68)) { // Letra D
				
				player1.moveRight(1, player1X, player1Y);
				
				if(lanzado1 == false){
					lastPosition1 = 'r';
				}
				if (player1X < (ancho - 20)){
					player1X += 10;
				}
			}
			if (StdDraw.isKeyPressed(65)) { // Letra A
				
				player1.moveLeft(1, player1X, player1Y);
				
				if(lanzado1 == false){
					lastPosition1 = 'l';
				}
				if (player1X > 20){
					player1X -= 10;
				}
			}
			if (StdDraw.isKeyPressed(87)) { // Letra W

				player1.moveUp(1, player1X, player1Y);
				
				if(lanzado1 == false){
					lastPosition1 = 'u';
				}
				if (player1Y < (alto - 20)){
					player1Y += 10;
				}
			}
			if (StdDraw.isKeyPressed(83)) { // Letra S
				
				player1.moveDown(1, player1X, player1Y);
				
				if(lanzado1 == false){
					lastPosition1 = 'd';
				}
				if (player1Y > 20){
					player1Y -= 10;
				}
			}
			if (StdDraw.isKeyPressed(39) || movimiento == 1) { // Flecha Derecha
				
				player2.moveRight(2, player2X, player2Y);
				
				if(lanzado2 == false){
					lastPosition2 = 'r';
				}
				if (player2X < (ancho - 20)){
					player2X += 10;
				}
			}
			if (StdDraw.isKeyPressed(37) || movimiento == 2) { // Flecha Izquierda

				player2.moveLeft(2, player2X, player2Y);

				if(lanzado2 == false){
					lastPosition2 = 'l';
				}
				if (player2X > 20){
					player2X -= 10;
				}
			}
			if (StdDraw.isKeyPressed(38) || movimiento == 3) { // Flecha Arriba

				player2.moveUp(2, player2X, player2Y);
				
				if(lanzado2 == false){
					lastPosition2 = 'u';
				}
				if (player2Y < (alto - 20)){
					player2Y += 10;
				}
			}
			if (StdDraw.isKeyPressed(40) || movimiento == 4) { // Flecha Abajo

				player2.moveDown(2, player2X, player2Y);

				if(lanzado2 == false){
					lastPosition2 = 'd';
				}
				if (player2Y > 20){
					player2Y -= 10;
				}
			}
			//Colisiones player 1 con rocket2
			colisiones1();
			//Colisiones player2 con rocket1
			colisiones2();
			
			// Dibujamos el contenido
			StdDraw.clear(StdDraw.BOOK_BLUE); //Seteamos el color del fondo
			player1.mostrar(player1X, player1Y); //Mostramos el jugador 1
			player2.mostrar(player2X, player2Y); //Mostramos el jugador 2
			StdDraw.picture(rocket1X, rocket1Y, rocketImage1);
			StdDraw.picture(rocket2X, rocket2Y, rocketImage2);
			StdDraw.text(ancho/2, alto - 20, nombre1 + "   " + player1.getScore()  +"    "+ player2.getScore() + "   " + nombre2);
			StdDraw.text(ancho/2 - 4, alto - 40, "TIME: " + time);
			StdDraw.show();
			StdDraw.pause(20);
			// El tiempo disminuye
			time--;
			// Revisar si el tiempo se acaba
			if (time < 0) {
				if(score1 > score2){
					JOptionPane.showMessageDialog(null, nombre1 + " won so ez.  " + player1.getScore() + " : " + player2.getScore(),
							 "Time is up!",
						    JOptionPane.INFORMATION_MESSAGE,
						    new ImageIcon("src/images/icon.jpg"));
				}else if(score1 < score2){
					JOptionPane.showMessageDialog(null, nombre2 + " won so ez.  " + player1.getScore() + " : " + player2.getScore(),
							 "Time is up!",
						    JOptionPane.INFORMATION_MESSAGE,
						    new ImageIcon("src/images/icon.jpg"));
				}else{
					JOptionPane.showMessageDialog(null, "Empate!! " + player1.getScore() + " : " + player2.getScore(),
						    "Time is up!",
						    JOptionPane.INFORMATION_MESSAGE,
						    new ImageIcon("src/images/icon.jpg"));
				}
				break;
			}
		}
	}

	/*
	 * Método que genera valores enteros aleatorios entre 1 y 5
	 */
	public int movimientoIA(){
		Random aleatorio = new Random();
		return aleatorio.nextInt(6);
	}

	/*
	 * Mëtodo que revisa si el jugador 1 ha sido alcanzado por el misil
	 * teniendo en cuenta el ancho y alto de las imágenes, además de su
	 * posición en el mapa
	 */
	public void colisiones1(){
		
		if (player1X  == rocket2X - 20 && (rocket2Y <= player1Y + 12 && rocket2Y >= player1Y - 12)){
			user1Image = "images/explosion.png";
			StdDraw.picture(player1X, player1Y, user1Image);
			StdDraw.show();
			lanzado2 = false;
			score2 += 1;
			player2.setScore(score2);
			reset();
		}
		else if (player1X  == rocket2X + 20 && (rocket2Y <= player1Y + 12 && rocket2Y >= player1Y - 12)){
			user1Image = "images/explosion.png";
			StdDraw.picture(player1X, player1Y, user1Image);
			StdDraw.show();
			lanzado2 = false;
			score2 += 1;
			player2.setScore(score2);
			reset();
		}else if (player1Y == rocket2Y + 20 && (rocket2X <= player1X + 12 && rocket2X >= player1X - 12)){
			user1Image = "images/explosion.png";
			StdDraw.picture(player1X, player1Y, user1Image);
			StdDraw.show();
			lanzado2 = false;
			score2 += 1;
			player2.setScore(score2);
			reset();
		}else if (player1Y == rocket2Y - 20 && (rocket2X <= player1X + 12 && rocket2X >= player1X - 12)){
			user1Image = "images/explosion.png";
			StdDraw.picture(player1X, player1Y, user1Image);
			StdDraw.show();
			lanzado2 = false;
			score2 += 1;
			player2.setScore(score2);
			reset();
		}
		
	}
	/*
	 * Mëtodo que revisa si el jugador 2 ha sido alcanzado por el misil
	 * teniendo en cuenta el ancho y alto de las imágenes, además de su
	 * posición en el mapa
	 */
	public void colisiones2(){
		
		if (player2X  == rocket1X - 20 && (rocket1Y <= player2Y + 12 && rocket1Y >= player2Y - 12)){
			user2Image = "images/explosion.png";
			StdDraw.picture(player2X, player2Y, user2Image);
			StdDraw.show();
			lanzado1 = false;
			score1 += 1;
			player1.setScore(score1);
			reset();
		}
		else if (player2X  == rocket1X + 20 && (rocket1Y <= player2Y + 12 && rocket1Y >= player2Y - 12)){
			user2Image = "images/explosion.png";
			StdDraw.picture(player2X, player2Y, user2Image);
			StdDraw.show();
			lanzado1 = false;
			score1 += 1;
			player1.setScore(score1);
			reset();
		}else if (player2Y == rocket1Y + 20 && (rocket1X <= player2X + 12 && rocket1X >= player2X - 12)){
			user2Image = "images/explosion.png";
			StdDraw.picture(player2X, player2Y, user2Image);
			StdDraw.show();
			lanzado1 = false;
			score1 += 1;
			player1.setScore(score1);
			reset();
		}else if (player1Y == rocket1Y - 20 && (rocket1X <= player2X + 12 && rocket1X >= player2X - 12)){
			user2Image = "images/explosion.png";
			StdDraw.picture(player2X, player2Y, user2Image);
			StdDraw.show();
			lanzado1 = false;
			score1 += 1;
			player1.setScore(score1);
			reset();
		}
		
	}
	
	/*
	 * Método que coloca a los jugadores en la posición inicial
	 */
	public void reset(){
	
		try {
			Thread.sleep (2000);
			} catch (Exception e) {
			}
		player1X = 200;	player1Y = 300;
		player2X = 200; player2Y = 200; 
		rocket1X = 0; rocket1Y = 0;
		rocket2X = 0; rocket2Y = 0; 
		user1Image = "images/player1/user1-right.png";
		user2Image = "images/player2/user2-right.png";
		lastPosition1 = 'r'; 
		lastPosition2 = 'r'; 
		cont1 = 0;
		cont2 = 0; 
		lanzado1 = false; //Estado del cohete 1
		lanzado2 = false; //Estado del cohete 2
	}
	/*
	 * Método de los movimientos del cohete del jugador 1
	 */
	public void disparoJ1() {
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
			lanzado1 = true;
		} else if (getLastPosition1() == 'l') {
			rocketImage1 = "images/player1/rocket1-left.png";
			if (cont1 == 0) {
				rocket1X = player1X - 40;
				rocket1Y = player1Y;
				cont1 = 1;
			}
			rocket1X -= 10;
			lanzado1 = true;
		} else if (getLastPosition1() == 'u') {
			rocketImage1 = "images/player1/rocket1-up.png";
			if (cont1 == 0) {
				rocket1X = player1X;
				rocket1Y = player1Y + 40;
				cont1 = 1;
			}
			rocket1Y += 10;
			lanzado1 = true;
		} else if (getLastPosition1() == 'd') {
			rocketImage1 = "images/player1/rocket1-down.png";
			if (cont1 == 0) {
				rocket1X = player1X;
				rocket1Y = player1Y - 40;
				cont1 = 1;
			}
			rocket1Y -= 10;
			lanzado1 = true;
		}
	}

	/*
	 * Método de los movimientos del cohete del jugador 2
	 */
	public void disparoJ2() {
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
			lanzado2 = true;
			
		} else if (getLastPosition2() == 'l') {
			rocketImage2 = "images/player2/rocket2-left.png";
			if (cont2 == 0) {
				rocket2X = player2X - 40;
				rocket2Y = player2Y;
				cont2 = 1;
			}
			rocket2X -= 10;
			lanzado2 = true;
			
		} else if (getLastPosition2() == 'u') {
			rocketImage2 = "images/player2/rocket2-up.png";
			if (cont2 == 0) {
				rocket2X = player2X;
				rocket2Y = player2Y + 40;
				cont2 = 1;
			}
			rocket2Y += 10;
			lanzado2 = true;
			
		} else if (getLastPosition2() == 'd') {
			rocketImage2 = "images/player2/rocket2-down.png";
			if (cont2 == 0) {
				rocket2X = player2X;
				rocket2Y = player2Y - 40;
				cont2 = 1;
			}
			rocket2Y -= 10;
			lanzado2 = true;
		}
	}
	
	/*
	 * Método encargado de realizar el movimiento
	 * del cohete 1
	 */
	public void movimientoCohete1() {

		if (lanzado1 == true) {

			if (getLastPosition1() == 'r') {
				rocket1X += 10;

			} else if (getLastPosition1() == 'l') {
				rocket1X -= 10;

			} else if (getLastPosition1() == 'u') {
				rocket1Y += 10;

			} else if (getLastPosition1() == 'd') {
				rocket1Y -= 10;
			}
		}
	}
	
	/*
	 * Método encargado de realizar el movimiento
	 * del cohete 2
	 */
	public void movimientoCohete2() {

		if (lanzado2 == true) {

			if (getLastPosition2() == 'r') {
				rocket2X += 10;

			} else if (getLastPosition2() == 'l') {
				rocket2X -= 10;

			} else if (getLastPosition2() == 'u') {
				rocket2Y += 10;

			} else if (getLastPosition2() == 'd') {
				rocket2Y -= 10;
			}
		}
	}
	
	/*
	 * Método que marca los límites de vuelo del cohete
	 */
	public void limitesVuelo(int ancho, int alto) {

		if (rocket1X > ancho || rocket1X < 0 || rocket1Y > alto || rocket1Y < 0) {
			rocket1X = -1000;
			rocket1Y = -1100;
			cont1 = 0;
			lanzado1 = false;
		}

		if (rocket2X > ancho || rocket2X < 0 || rocket2Y > alto || rocket2Y < 0) {
			rocket2X = -1000;
			rocket2Y = -1100;
			cont2 = 0;
			lanzado2 = false;
		}
	}
	/*
	 * Método que recupera la última posición
	 * del cohete 1
	 */
	public char getLastPosition1() {
		return lastPosition1;
	}
	/*
	 * Método que recupera la última posición
	 * del cohete 2
	 */
	public char getLastPosition2() {
		return lastPosition2;
	}

	/**
	 * Método que setea los nombres de los jugadores
	 * @param jugador 1 para nombre del jugador1, 2 para el jugador 2
	 * @param nombre del jugador
	 */
	public void setNombre(int jugador, String nombre){
		if(jugador == 1){
			this.nombre1 = nombre;
		}else if(jugador == 2){
			this.nombre2 = nombre;
		}
	}
}
