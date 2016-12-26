import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Principal {

	/**
	 * @author aitor
	 *  Clase principal del programa
	 */
	public static void main(String[] args) {
	
		int seleccion = JOptionPane.showOptionDialog( null, "Elija un modo de juego",  "Battle Rockets",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   new ImageIcon("src/images/icon.jpg"),
				   new Object[] { "Jugador vs Jugador", "Jugador vs IA"},
				   "Jugador vs Jugador");
	
				if (seleccion == 0){
					dosJugadores();
				}else if(seleccion == 1){
					IA();
				}
	}
	
	/*
	 * Modo de juego Jugador vs Jugador
	 */
	public static void dosJugadores(){

		String nombre1 = JOptionPane.showInputDialog(null, "Nombre del Jugador 1"); 
		String nombre2 = JOptionPane.showInputDialog(null, "Nombre del Jugador 2");
		
		Juego jugar = new Juego(); 
		jugar.setNombre(1, nombre1);
		jugar.setNombre(2, nombre2);
		jugar.cargarJuego(1);
	}
	
	/*
	 * Modo de juego Jugador vs IA
	 */
	public static void IA(){
		String nombre1 = JOptionPane.showInputDialog(null, "Nombre del Jugador"); 
		
		Juego jugar = new Juego(); 
		jugar.setNombre(1, nombre1);
		jugar.setNombre(2, "IA");
		jugar.cargarJuego(2);
	}
}
