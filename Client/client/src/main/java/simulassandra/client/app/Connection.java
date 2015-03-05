package simulassandra.client.app;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Connection {
	
	private Session session;
	
	public Connection(Cluster c){
		this.session = c.connect(); //voir si définition keyspace immédiate
	}

}
