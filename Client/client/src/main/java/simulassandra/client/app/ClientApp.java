package simulassandra.client.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import simulassandra.client.Config;
import simulassandra.client.exceptions.UnreachableHostException;

import com.datastax.driver.core.Cluster;

public class ClientApp {
	
	private String address;
	private Cluster cluster;
	
	public ClientApp(String a) throws UnreachableHostException, IOException{
		this.setAddress(a);
	}
	
	public String getAdress(){
		return this.address;
	}
	
	private void setAddress(String a) throws UnreachableHostException, IOException{
		//First, we're testing the existence of the host
		InetAddress i = InetAddress.getByName(address);
		//Ping
		if(i.isReachable(Config.TIMEOUT)){
			this.address = a;
		} else {
			throw new UnreachableHostException("Timeout");
		}
	}
	
	public Boolean run(){
		//TODO
		return Boolean.TRUE;
	}
	
	private void connectToCluster(){
		this.cluster = Cluster.builder().addContactPoint(this.address).build();
	}
	
	public void finalize(){
		this.cluster.close();
	}

}
