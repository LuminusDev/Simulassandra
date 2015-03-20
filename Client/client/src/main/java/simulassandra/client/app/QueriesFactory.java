package simulassandra.client.app;

import java.math.BigInteger;
import java.util.Random;

import com.datastax.driver.core.Session;

public class QueriesFactory {
	
	private String seed;
	private Session session;
	
	public QueriesFactory(Session session){
		Random random = new Random();
		this.seed = new BigInteger(130, random).toString(32); //Creating seed
		this.session = session;
	}
	
	public QueriesFactory(String seed, Session session){
		this.seed = seed;
		this.session = session;
	}
	
	public String getSeed(){
		return this.seed;
	}
	
	
}
