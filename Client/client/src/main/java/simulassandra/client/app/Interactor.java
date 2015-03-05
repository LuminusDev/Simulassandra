package simulassandra.client.app;

import java.util.Scanner;

public class Interactor {
	
	public static void welcome(){
		System.out.println("Simulassandra Client");
	}
	
	public static String getHostAddress(){
		Scanner sc = new Scanner(System.in);
    	System.out.println("Please, tip the host address :");
    	return sc.nextLine();
	}
	
	public static void checkingHost(String h){
		System.out.println("Checking if "+h+" host is reachable.");
	}
	
	public static void creatingContactPoint(){
		System.out.println("Connecting to cluster.");
	}
	
	public static void displayException(Exception e){
		System.out.println("Error > "+e.getMessage());
	}

	public static void end(){
		System.out.println("End");
	}
}
