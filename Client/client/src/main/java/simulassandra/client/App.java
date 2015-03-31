package simulassandra.client;

import java.io.IOException;

import com.datastax.driver.core.exceptions.NoHostAvailableException;

import simulassandra.client.app.ClientApp;
import simulassandra.client.exceptions.UnreachableHostException;
import simulassandra.client.utils.Interactor;

/**
 * Classe App
 * Fait tourner l'application, elle contient le main.
 * 
 * @author Guillaume Marques <guillaume.marques33[a]gmail.com>
 *
 */
public class App 
{
	private static ClientApp clientApp;
	
	/**
	 * Main
	 */
    public static void main(String[] args)
    {
    	Interactor.welcome();
    	
    	try {
    		setAddress(Interactor.getHostAddress());
    	} catch (Error e) {
    		Interactor.displayError(e);
    		Interactor.end();
    		System.exit(0);
    	}
    	
    	clientApp.run();
    	
    	Interactor.end();
    	System.exit(0);
    }
    
    /**
     * setAddress
     * Connexion à la base de données Cassandra située à l'adresse passée en paramètre.
     * La méthode redemande une adresse automatiquement jusqu'à atteindre le nombre de
     * tentative Config.NB_TENTATIVES
     * @param address, une adresse internet
     * @throws Error, lorsque la base de données Cassandra hébergée sur la machine située 
     * à l'adresse indiquée est injoignable (ou inexistante) ou lorsque le nombre de
     * tentatives est dépassé.
     */
    private static void setAddress( String address ) throws Error {
    	setAddress(address,Config.NB_TENTATIVES);
    }
    
    /**
     * setAddress
     * Connexion à la base de données Cassandra située à l'adresse passée en paramètre.
     * La méthode redemande une adresse automatiquement jusqu'à atteindre le nombre de
     * tentative passé en paramètre
     * @param address, une adresse internet
     * @param nb_tentatives_restantes, nombre de tentatives pour se connecter
     * @throws Error, lorsque la base de données Cassandra hébergée sur la machine située 
     * à l'adresse indiquée est injoignable (ou inexistante) ou lorsque le nombre de
     * tentatives est dépassé.
     */
    private static void setAddress( String address, Integer nb_tentatives_restantes ) throws Error {
    	
    	if(nb_tentatives_restantes <= 0){
    		throw new Error("Too many attempts.");
    	}
    	
    	try {
			clientApp = new ClientApp(address);
		} catch (UnreachableHostException | IOException e) {
			Interactor.displayException(e);
			setAddress(Interactor.getHostAddress(), nb_tentatives_restantes--);
		} catch (NoHostAvailableException e){
			Interactor.displayException(e);
			throw new Error("Cassandra unreachable.");
		}
    }
}
