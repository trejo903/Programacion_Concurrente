
/**
 * Clase que consume elementos eliminándolos de la cola
 */
public class Consumidor extends Thread {

	private Cola cola;
	
	public Consumidor(Cola cola) {
		
		this.cola = cola;
	}
	
	public void run() {
		
		Integer elemento;
		
		do {
			elemento = cola.eliminar();
			System.out.println("Consumidor " + getName() + " - " + elemento);
		} while (elemento != -1);
	}

}
