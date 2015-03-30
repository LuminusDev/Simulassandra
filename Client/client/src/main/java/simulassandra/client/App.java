package simulassandra.client;

import java.io.IOException;

import com.datastax.driver.core.exceptions.NoHostAvailableException;

import simulassandra.client.app.ClientApp;
import simulassandra.client.exceptions.UnavailableKeyspaceException;
import simulassandra.client.exceptions.UnreachableHostException;
import simulassandra.client.utils.Interactor;


public class App 
{
	private static ClientApp clientApp;
	
    public static void main( String[] args )
    {
    	Interactor.welcome();
    	
    	try {
    		setAddress(Interactor.getHostAddress());
    	} catch (Error e) {
    		Interactor.displayError(e);
    		Interactor.end();
    		System.exit(0);
    	}
    	
    	clientApp.run();
    	
    	Interactor.end();
    	System.exit(0);
    }
    
    public static void setAddress(String address) throws Error {
    	try {
			clientApp = new ClientApp(address);
		} catch (UnreachableHostException | IOException e) {
			Interactor.displayException(e);
			setAddress(Interactor.getHostAddress());
		} catch (NoHostAvailableException e){
			Interactor.displayException(e);
			throw new Error("Cassandra unreachable.");
		}
    }
}
