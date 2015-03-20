package simulassandra.client.app;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.SyntaxError;

public class Connection {
	
	private Session session;
	private KeySpace keyspace;
	
	public Connection(Cluster c, KeySpace ks){
		this.session = c.connect(); //voir si définition keyspace immédiate
		this.keyspace = ks;
	}
	
	public Session getSession(){
		return this.session;
	}

	public void executeQuery(String query){
		this.session.execute(query);
	}
	
	
}
