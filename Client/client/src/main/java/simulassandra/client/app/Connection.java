package simulassandra.client.app;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.SyntaxError;

public class Connection {
	
	private Session session;
	
	public Connection(Cluster c){
		this.session = c.connect(); //voir si définition keyspace immédiate
	}

	public void executeQuery(String query){
		
		this.session.execute(query);
		
	}
}
