package simulassandra.client.app;

import java.io.File;

import simulassandra.client.exceptions.KeyspaceException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

public class Connection {
	
	private Session session;
	private KeySpace keyspace;
	
	public Connection(Cluster c, String keyspace_name) throws KeyspaceException{
		this.session = c.connect(); //voir si définition keyspace immédiate
		this.keyspace = new KeySpace(keyspace_name, this);
		initConnection();
	}
	
	public Connection(Cluster cluster, String keyspace_name,
			String replication_type, Integer replication_factor, File data_file) {
		// TODO Auto-generated constructor stub
	}

	private void initConnection(){
	
	}

	public ResultSet execute(String query){
		return this.session.execute(query);
	}
	
	public ResultSet execute(Statement query){
		return this.session.execute(query);
	}
	
	public Table getTable(Integer i){
		return this.keyspace.getTable(i);
	}
}
