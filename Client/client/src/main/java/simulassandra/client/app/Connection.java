package simulassandra.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnavailableKeyspaceException;
import simulassandra.client.utils.Interactor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

/**
 * Objet ayant pour but d'exécuter les requêtes sur Cassandra
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class Connection {
	
	private Session session;
	private KeySpace keyspace;
	
	/**
	 * Constructeur à utiliser si le keyspace existe
	 * 
	 * @param cluster, cluster sur lequel nous travaillons
	 * @param keyspace_name, keyspace existant dans le cluster
	 * @throws KeyspaceException le keyspace n'existe pas
	 * @throws UnavailableKeyspaceException 
	 */
	public Connection(Cluster cluster, String keyspace_name) throws KeyspaceException, UnavailableKeyspaceException{
		this.session = cluster.connect();
		this.keyspace = new KeySpace(cluster.getMetadata().getKeyspace(keyspace_name), this);
	}
	
	/**
	 * Constructeur à utiliser si le keyspace n'existe pas
	 * 
	 * @param cluster, cluster sur lequel nous travaillons
	 * @param keyspace_name, nom du keyspace
	 * @param replication_type, classe de réplication utilisée dans le keyspace
	 * @param replication_factor, facteur de réplication
	 * @throws KeyspaceException 
	 */
	public Connection(Cluster cluster, String keyspace_name,
			String replication_type, Integer replication_factor) throws KeyspaceException {
		// TODO Auto-generated constructor stub
		this.session = cluster.connect();
		this.keyspace = new KeySpace(cluster, keyspace_name, replication_type, replication_factor, this);
	}

	/**
	 * Exécution d'une requête sur Cassandra
	 * @param query chaîne de caractère contenant la requête
	 * @return ResultSet, résultat de la requête si bonne exécution, null sinon
	 */
	public ResultSet execute(String query){
		try{
			return this.session.execute(query);
		} catch (Exception e) {
			Interactor.displayException(e);
			Interactor.displayMessage("Query will be ignored");
			return null;
		}
	}
	
	/**
	 * Exécution d'une requête sur Cassandra
	 * @param query Statement contenant la requête
	 * @return ResultSet, résultat de la requête si bonne exécution, null sinon
	 */
	public ResultSet execute(Statement query){
		try{
			return this.session.execute(query);
		} catch (Exception e) {
			Interactor.displayException(e);
			Interactor.displayMessage("Query will be ignored");
			return null;
		}
	}
	
	/**
	 * Retourne l'objet Table situé en position i dans la liste des tables du keyspace
	 * @param i position
	 * @return Table
	 */
	public Table getTable(Integer i){
		return this.keyspace.getTable(i);
	}
	
	/**
	 * Retourne le keyspace courant
	 * @return KeySpace
	 */
	public KeySpace getKeyspace(){
		return this.keyspace;
	}
	

	/**
	 * Exécute les requêtes contenues dans le fichier situé à l'adresse path
	 * @param path adresse du fichier
	 * @throws FileNotFoundException Le fichier n'existe pas
	 * @throws UnavailableKeyspaceException Impossiblité d'effectuer des requetes dans
	 * le keyspace
	 */
	public void executeFromFileQueries(String path) throws FileNotFoundException, UnavailableKeyspaceException{
		
		File f = new File(path);
		Scanner sc = new Scanner(f);
		String query = new String();

		while(sc.hasNext()){
			String line = sc.nextLine();
			
			if( line.matches(".*; {0,}") ){
				query += line;
				try {
					this.execute(query);
				} catch (Exception e) {
					Interactor.displayException(e);
					Interactor.displayMessage("Query will be ignored");
				}
				query = "";
			} else {
				query += line;
			}
		}
		this.keyspace.updateTablesList();
		sc.close();
	}
}
