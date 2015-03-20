package simulassandra.client.app;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.exceptions.UnreachableHostException;
import utils.Interactor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class ClientApp {
	
	private String address;
	private Cluster cluster;
    private Connection connection;
	
	public ClientApp(String a) throws UnreachableHostException, IOException{
		this.setAddress(a);
		this.connectToCluster();
		String keyspace_name = Interactor.getKeySpaceName();
		String replication_type = Interactor.getReplicationType();
		File data_file = Interactor.getDataFile();
		this.connection = new Connection(cluster, new KeySpace(keyspace_name, replication_type, data_file));
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
		Boolean end = Boolean.FALSE;
		while(!end){
			try {
				Command command = Interactor.commandInput();
				end = this.execute(command);
			} catch (ArgumentException e) {
				Interactor.displayException(e);
			}
		}
		return Boolean.TRUE;
	}
	
	private Boolean execute(Command cmd){
		switch(cmd.getAction()){
			case Config.ACT_QUIT:
				return Boolean.TRUE;
			case Config.ACT_HELP:
				Interactor.showCommands();
				return Boolean.FALSE;
			case Config.ACT_IMPORT:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_SWITCH_KEYSPACE:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_RESET_KEYSPACE:
				//TODO
				return Boolean.FALSE;
			case Config.ACT_QUERIESFACTORY:
				//TODO
				return Boolean.FALSE;
			default:
				Interactor.displayMessage("Unknown command");
				Interactor.showCommands();
				return Boolean.FALSE;
				
		}
	}
	
	private void connectToCluster(){
		this.cluster = Cluster.builder().addContactPoint(this.address).build();
		Interactor.displayMetadata(cluster.getMetadata());
	}
	
	public void finalize(){
		this.cluster.close();
	}

}
