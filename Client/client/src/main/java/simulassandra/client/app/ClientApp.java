package simulassandra.client.app;

import java.io.IOException;
import java.net.InetAddress;

import simulassandra.client.Config;
import simulassandra.client.exceptions.UnreachableHostException;

import com.datastax.driver.core.Cluster;

public class ClientApp {
	
	private String address;
	private Cluster cluster;
    private Connection connection;
	
	public ClientApp(String a) throws UnreachableHostException, IOException{
		this.setAddress(a);
		this.connection = new Connection(cluster);
	}
	
	public String getAdress(){
		return this.address;
	}
	
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
	
	
	public Boolean run(){
		this.connectToCluster();
		
		
		return Boolean.TRUE;
	}
	
	private void connectToCluster(){
		
		Interactor.creatingContactPoint();
		
		this.cluster = Cluster.builder().addContactPoint(this.address).build();
		
	}
	
	public void finalize(){
		this.cluster.close();
	}

}
