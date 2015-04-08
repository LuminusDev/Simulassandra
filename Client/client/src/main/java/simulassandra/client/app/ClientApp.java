package simulassandra.client.app;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnavailableKeyspaceException;
import simulassandra.client.exceptions.UnreachableHostException;
import simulassandra.client.utils.Interactor;
import simulassandra.data.generator.DataGenerator;
import simulassandra.data.generator.SimpleDataGenerator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

/**
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class ClientApp {
	
	private String address;
	private Cluster cluster;
    private Connection connection;
    
    /**
     * Constructeur pour tests
     */
    public ClientApp(){
    	
    }
    
	/**
	 * Constructeur initialisant la connexion à Cassandra.
	 * 
	 * @param a, adresse de connexion
	 * @throws UnreachableHostException, adresse a n'est pas accessible
	 * @throws IOException, impossible de se connecter à l'adresse a.
	 * @throws UnavailableKeyspaceException, impossibilité de créer le keyspace,
	 * impossibilité d'effectuer des requêtes sur le keyspace demandé (dans ce cas
	 * il faut modifier la configuration du keyspace et bien vérifier que le
	 * replication_factor demandé correspond à une base de données existante. 
	 */
	public ClientApp(String a) throws UnreachableHostException, IOException, NoHostAvailableException{
		
		this.setAddress(a);
		this.connectToCluster();
		
		initKeyspace(Interactor.getKeySpaceName());
		Interactor.displayKeyspace(this.connection.getKeyspace().getName());
	}
	
	/**
	 * Setteur, permet de définir l'adresse du cluster auquel on souhaite se connecter.
	 * Cette méthode n'est à utiliser que lors de l'initialisation de la connexion.
	 * Une execution de celle-ci une fois la connexion au cluster initialisé peut provoquer un arrêt du programme.
	 * 
	 * @param a adresse
	 * @throws UnreachableHostException  adresse injoignable après Config.TIMEOUT secondes
	 * @throws IOException problème lors de la tentative de lancer un ping à l'adresse
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
	 * Initialise la connexion au cluster
	 */
	private void connectToCluster() {
			this.cluster = Cluster.builder().addContactPoint(this.address).build();
			Interactor.displayMetadata(cluster.getMetadata());
	}
	
	/**
	 * Connecte le client sur le keyspace dont le nom est passé en paramètre.
	 * Si le keyspace n'existe pas, la méthode propose sa création.
	 * 
	 * @param keyspace_name, nom du keyspace sur lequel on souhaite se connecter.
	 */
	private void initKeyspace(String keyspace_name){
		try {
			this.connection = new Connection(cluster, keyspace_name);
		} catch (KeyspaceException e) { //Le Keyspace n'existe pas
			Interactor.displayException(e);
			if(Interactor.question("Do you want to create a new keyspace named "+keyspace_name+" ?")){
			
				String replication_type = Interactor.getReplicationType();
				Integer replication_factor = Interactor.getReplicationFactor();
				try {
					this.connection = new Connection(cluster, keyspace_name, replication_type, replication_factor);
				} catch (KeyspaceException f) { //Impossible de créer le keyspace
					Interactor.displayException(f);
				}
			} else {
				initKeyspace(Interactor.getKeySpaceName());
			}
		} catch (UnavailableKeyspaceException e) { //Le Keyspace n'est pas disponible au requetage
			Interactor.displayException(e);
			initKeyspace(Interactor.getKeySpaceName());
		}
	}
	
	/**
	 * Execute le generateur de requête nommé queries_factory
	 * La classe représentant l'objet doit être dans le package simulassandra.client.queriesfactory
	 * et hérité de l'objet QueriesFactory
	 * @param queries_factory nom du générateur de requête
	 * @param seed graîne pour initialiser le générateur de nombre aléatoire
	 * @param nb_simulations nombre de simulations, une simulation correspond à effectuer nb_queries requêtes sur une table.
	 * @param nb_queries nombre de requête à effectuer
	 */
	private void execQueriesFactory(String queries_factory, Long seed, Integer nb_simulations, Integer nb_queries){
		try {
			Class<?> factory_class = Class.forName("simulassandra.client.queriesfactory."+queries_factory);
			Constructor<?> constructeur = factory_class.getConstructor(Connection.class, Long.class, Integer.class, Integer.class);
			Object factory_object = constructeur.newInstance(this.connection, seed, nb_simulations, nb_queries);
			Method run = factory_class.getMethod("run");
			run.invoke(factory_object);
		} catch (ClassNotFoundException e) {
			Interactor.displayException(e);
		} catch (NoSuchMethodException | SecurityException e) {
			Interactor.displayException(e);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			Interactor.displayException(e);
		}
	}

	/**
	 * Effectue une action selon la commande choisie.
	 * 
	 * @param cmd, commande à executer
	 * @return Boolean.TRUE si l'utilisateur souhaite mettre fin au programme.
	 * Boolean.FALSE sinon.
	 * @throws ArgumentException Argument manquant, argument incorrect 
	 * @throws KeyspaceException Keyspace inexistant
	 * @throws UnavailableKeyspaceException 
	 * @throws IOException 
	 */
	public Boolean execute(Command command) throws ArgumentException, 
												KeyspaceException, 
												UnavailableKeyspaceException, 
												IOException{
		switch(command.getAction()){
			case Config.ACT_QUIT:
				return Boolean.TRUE;
			case Config.ACT_HELP:
				Interactor.showCommands();
				return Boolean.FALSE;
			case Config.ACT_IMPORT:
				this.connection.executeFromFileQueries(command.getArg(0));
				return Boolean.FALSE;
			case Config.ACT_SWITCH_KEYSPACE:
				initKeyspace(command.getArg(0));
				Interactor.displayKeyspace(this.connection.getKeyspace().getName());
				return Boolean.FALSE;
			case Config.ACT_QUERIESFACTORY:
				execQueriesFactory(command.getArg(0), Long.parseLong(command.getArg(1)), Integer.parseInt(command.getArg(2)), Integer.parseInt(command.getArg(3)));
				return Boolean.FALSE;
			case Config.ACT_SHOW_KEYSPACE:
				Interactor.displayMessage(this.connection.getKeyspace().getMetadata());
				return Boolean.FALSE;
			case Config.ACT_SHOW_TABLE:
				Interactor.displayMessage(this.connection.getKeyspace().getTableMetadata(command.getArg(0)));
				return Boolean.FALSE;
			case Config.ACT_LIST_TABLE:
				Interactor.displayMessage(this.connection.getKeyspace().getTablesList());
				return Boolean.FALSE;
			case Config.ACT_CREATE_DATA_FILE:
				Integer nb_tables = Integer.parseInt(command.getArg(1));
				Integer nb_rows = Integer.parseInt(command.getArg(2));
				Integer data_size = Integer.parseInt(command.getArg(3));
				DataGenerator generator = new SimpleDataGenerator(this.connection.getKeyspace(), command.getArg(0), nb_tables, nb_rows, data_size);
				if(generator.write()){
					Interactor.displayMessage("Data writed.");
				}
				return Boolean.FALSE;
			default:
				Interactor.displayUnknownCommand();
				return Boolean.FALSE;
		}
	}
	
	/**
	 * Getteur
	 * @return l'adresse du cluster
	 */
	public String getAddress(){
		return this.address;
	}
	
	/**
	 * Console, l'utilisateur communique avec le programme en ligne de commande
	 * 
	 * @return Boolean.FALSE si une erreur est survenue. Boolean.TRUE sinon
	 */
	public void run(){
		Boolean end = Boolean.FALSE;
		while(!end){
			try {
				Command command = Interactor.commandInput();
				end = this.execute(command);
			} catch (Exception e) {
				Interactor.displayException(e);
			}
		}
	}
	
	/**
	 * Destructeur
	 * Ferme la connexion au cluster lors de la destruction de l'objet.
	 */
	public void finalize(){
		this.cluster.close();
	}
}