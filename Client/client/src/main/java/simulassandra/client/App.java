package simulassandra.client;

import java.io.IOException;
import java.util.Scanner;

import simulassandra.client.app.ClientApp;
import simulassandra.client.app.Interactor;
import simulassandra.client.exceptions.UnreachableHostException;


public class App 
{
	private static ClientApp clientApp;
	
    public static void main( String[] args )
    {
    	Interactor.welcome();
    	
    	setAddress();
    	
    }
    
    public static void setAddress(){
    	
    	try {
			clientApp = new ClientApp(Interactor.getHostAddress());
		} catch (UnreachableHostException | IOException e) {
			e.printStackTrace();
			
			setAddress();
		}
    }
}
