package simulassandra.client;

import java.io.IOException;

import simulassandra.client.app.ClientApp;
import simulassandra.client.exceptions.UnreachableHostException;
import simulassandra.client.utils.Interactor;


public class App 
{
	private static ClientApp clientApp;
	
    public static void main( String[] args )
    {
    	Interactor.welcome(); 	
    	setAddress();
    	
    	clientApp.run();
    	
    	Interactor.end();
    	System.exit(0);
    }
    
    public static void setAddress(){
    	try {
			clientApp = new ClientApp(Interactor.getHostAddress());
		} catch (UnreachableHostException | IOException e) {
			Interactor.displayException(e);
			setAddress();
		}
    }
}
