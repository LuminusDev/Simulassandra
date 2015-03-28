package simulassandra.client.app;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnreachableHostException;
import utils.Interactor;

import com.datastax.driver.core.Cluster;

/**
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 */
public class ClientApp {
	
	private String address;
	private Cluster cluster;
    private Connection connection;
    
	/**
	 * Constructeur initialisant la connexion à Cassandra.
	 * 
	 * @param a, adresse de connexion
	 * @throws UnreachableHostException, si l'adresse a n'est pas accessible
	 * @throws IOException, lorsuq'il est impossible de se connecter à l'adresse a.
	 */
	public ClientApp(String a) throws UnreachableHostException, IOException{
		this.setAddress(a);
		this.connectToCluster();
		
		String keyspace_name = Interactor.getKeySpaceName();
		try {
			this.connection = new Connection(cluster, keyspace_name);
		} catch (KeyspaceException e) {
			Interactor.displayException(e);
			String replication_type = Interactor.getReplicationType();
			Integer replication_factor = Interactor.getReplicationFactor();
			File data_file = Interactor.getDataFile();
			
			try {
				this.connection = new Connection(cluster, keyspace_name, replication_type, replication_factor);
			} catch (KeyspaceException e1) {
				// TODO Auto-generated catch block
				Interactor.displayException(e1);
				e1.printStackTrace();
			}
		}
		
		String replication_type = Interactor.getReplicationType();
		File data_file = Interactor.getDataFile();
	}
	
	/**
	 * Méthode setAddress
	 * Setteur, permet de définir l'adresse du cluster auquel on souhaite se connecter.
	 * Cette méthode n'est à utiliser que lors de l'initialisation de la connexion.
	 * Une execution de celle-ci une fois la connexion au cluster initialisé peut provoquer un arrêt du programme.
	 * 
	 * @param a addresse
	 * @throws UnreachableHostException
	 * @throws IOException
	 */
	private void setAddress(String a) throws UnreachableHostException, IOException{
		
		//First, we're testing the existence of the host
		Interactor.checkingHost(a);
		InetAddress i = InetAddress.getByName(a);
		
		//Ping
		if(i.isReachable(Config.TIMEOUT)){
			this.address = a;
		} else {
			throw new UnreachableHostException("Timeout, host "+a+" is unreachable.");
		}
	}
	
	/**
	 * Méthode connectToCluster
	 * Initialise la connexion au cluster
	 */
	private void connectToCluster(){
		this.cluster = Cluster.builder().addContactPoint(this.address).build();
		Interactor.displayMetadata(cluster.getMetadata());
	}

	/**
	 * Getteur getAddress
	 * 
	 * @return l'adresse du cluster
	 */
	public String getAddress(){
		return this.address;
	}
	
	/**
	 * Méthode run
	 * Console, l'utilisateur communique avec le programme en ligne de commande
	 * 
	 * @return
	 */
	public Boolean run(){
		Boolean end = Boolean.FALSE;
		while(!end){
			try {
				Command command = Interactor.commandInput();
				end = this.execute(command);
			} catch (ArgumentException e) {
				Interactor.displayException(e);
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Méthode execute
	 * Effectue une action selon la commande choisie.
	 * 
	 * @param cmd, commande à executer
	 * @return
	 */
	private Boolean execute(Command cmd){
		switch(cmd.getAction()){
			case Config.ACT_QUIT:
				return Boolean.TRUE;
			case Config.ACT_HELP:
				Interactor.showCommands();
				return Boolean.FALSE;
			case Config.ACT_IMPORT:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_SWITCH_KEYSPACE:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_RESET_KEYSPACE:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_QUERIESFACTORY:
				//TODO
				return Boolean.FALSE;
			default:
				Interactor.displayMessage("Unknown command");
				Interactor.showCommands();
				return Boolean.FALSE;
				
		}
	}
	
	/**
	 * Destructeur
	 * Fermer la connexion au cluster lors de la destruction de l'objet.
	 */
	public void finalize(){
		this.cluster.close();
	}

}