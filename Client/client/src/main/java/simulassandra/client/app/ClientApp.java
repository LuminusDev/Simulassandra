package simulassandra.client.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnreachableHostException;
import simulassandra.client.queriesfactory.QueriesFactory;
import simulassandra.client.utils.Interactor;

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
    private QueriesFactory factory;
    
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
		initKeyspace(keyspace_name);
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
	
	
	private void initKeyspace(String keyspace_name){
		try {
			this.connection = new Connection(cluster, keyspace_name);
		} catch (KeyspaceException e) { //Le Keyspace n'existe pas
			Interactor.displayException(e);
			
			String replication_type = Interactor.getReplicationType();
			Integer replication_factor = Interactor.getReplicationFactor();
			try {
				this.connection = new Connection(cluster, keyspace_name, replication_type, replication_factor);
			} catch (KeyspaceException e1) { //Impossible de créer le keyspace
				// TODO Auto-generated catch block
				Interactor.displayException(e1);
				e1.printStackTrace();
			}
		}
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
	 * @throws ArgumentException 
	 * @throws KeyspaceException 
	 * @throws FileNotFoundException 
	 */
	private Boolean execute(Command cmd) throws ArgumentException, KeyspaceException, FileNotFoundException{
		switch(cmd.getAction()){
			case Config.ACT_QUIT:
				return Boolean.TRUE;
			case Config.ACT_HELP:
				Interactor.showCommands();
				return Boolean.FALSE;
			case Config.ACT_IMPORT:
				this.connection.executeFromFileQueries(cmd.getArg(0));
				return Boolean.FALSE;
			case Config.ACT_SWITCH_KEYSPACE:
				initKeyspace(cmd.getArg(0));
				Interactor.displayMessage(this.connection.getKeyspace().getMetadata());
				return Boolean.FALSE;
			case Config.ACT_QUERIESFACTORY:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_SHOW_KEYSPACE:
				Interactor.displayMessage(this.connection.getKeyspace().getMetadata());
				return Boolean.FALSE;
			case Config.ACT_SHOW_TABLE:
				Interactor.displayMessage(this.connection.getKeyspace().getTableMetadata(cmd.getArg(0)));
				return Boolean.FALSE;
			case Config.ACT_LIST_TABLE:
				Interactor.displayMessage(this.connection.getKeyspace().getTablesList());
				return Boolean.FALSE;
			default:
				Interactor.displayUnknownCommand();
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