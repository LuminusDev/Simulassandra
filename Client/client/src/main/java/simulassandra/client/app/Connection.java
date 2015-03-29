package simulassandra.client.app;

import java.io.FileNotFoundException;

import simulassandra.client.exceptions.KeyspaceException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

/**
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 *
 */
public class Connection {
	
	private Session session;
	private KeySpace keyspace;
	
	/**
	 * Constructeur à utiliser si le keyspace existe
	 * 
	 * @param cluster, cluster sur lequel nous travaillons
	 * @param keyspace_name, keyspace existant dans le cluster
	 * @throws KeyspaceException, lorsque le keyspace n'existe pas
	 */
	public Connection(Cluster cluster, String keyspace_name) throws KeyspaceException{
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
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet execute(String query){
		return this.session.execute(query);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet execute(Statement query){
		return this.session.execute(query);
	}
	
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Table getTable(Integer i){
		return this.keyspace.getTable(i);
	}
	
	public KeySpace getKeyspace(){
		return this.keyspace;
	}
	
	public void executeFromFileQueries(String path) throws FileNotFoundException{
		this.keyspace.executeFromFileQueries(path);
	}
	
	public void copyDataFromFile(String table_name, String path) throws FileNotFoundException{
		this.keyspace.copyDataFromFile(table_name, path);
	}
}
