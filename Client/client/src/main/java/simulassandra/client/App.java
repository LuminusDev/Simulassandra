package simulassandra.client;

import java.io.IOException;

import simulassandra.client.app.ClientApp;
import simulassandra.client.exceptions.UnreachableHostException;
import utils.Interactor;


public class App 
{
	private static ClientApp clientApp;
	
    public static void main( String[] args ) throws IOException
    {
    	Interactor.welcome(); 	
    	setAddress();
    	
    	clientApp.run();
    	
    	Interactor.end();
    	
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
