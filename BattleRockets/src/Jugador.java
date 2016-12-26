/**
 * @author aitor
 * Clase objeto de los jugadores
 */
public class Jugador {

	private int jugador;
	private String imagen;
	private int x;
	private int y;
	private int score;
	
	public Jugador(int jugador, String imagen, int x, int y, int score) {
		
		this.jugador = jugador;
		this.imagen = imagen;
		this.x = x;
		this.y = y;
		this.score = score;
	}
	
	/*
	 * Método que dibuja al jugador
	 */
	public void mostrar(int x, int y){
			this.imagen = getImagen();
			StdDraw.picture(x, y, imagen);
	}
	
	public void moveUp(int jugador, int x, int y){
		if(jugador == 1){
			this.imagen = "images/player1/user1-up.png";
		}else{
			this.imagen = "images/player2/user2-up.png";
		}
		setImagen(imagen);
		y += 10;
	}
	
	public void moveDown(int jugador, int x, int y){
		if(jugador == 1){
			this.imagen = "images/player1/user1-down.png";
		}else{
			this.imagen = "images/player2/user2-down.png";
		}
		setImagen(imagen);
		y -= 10;
	}

	public void moveLeft(int jugador, int x, int y){
		if(jugador == 1){
			this.imagen = "images/player1/user1-left.png";
		}else{
			this.imagen = "images/player2/user2-left.png";
		}
		setImagen(imagen);
		x -= 10;
	}

	public void moveRight(int jugador, int x, int y){
	
		if(jugador == 1){
			this.imagen =  "images/player1/user1-right.png";
		}else{
			this.imagen =  "images/player2/user2-right.png";
		}
		setImagen(imagen);
		x += 10;
	}
	/*
	 * Método que muestra al jugador cuando le alcanza
	 * un cohete
	 */
	public void destruido(){
		imagen = " ";
		setImagen(imagen);
	}

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
