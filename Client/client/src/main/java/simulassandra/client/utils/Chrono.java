package simulassandra.client.utils;

/**
 * Classe Chrono
 * Objet permettant de chronometrer le temps en millisecondes.
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 */
public class Chrono {
	
	private Long begin;
	
	/**
	 * Constructeur
	 * Initialisation et lancement du chronomètre.
	 */
	public Chrono(){
		this.begin = System.currentTimeMillis();
	}
	
	/**
	 * Méthode time
	 * Mesure le temps écoulé depuis la création du chronomètre.
	 * @return Long temps écoulé
	 */
	public Long time(){
		return System.currentTimeMillis() - this.begin;
	}
}
