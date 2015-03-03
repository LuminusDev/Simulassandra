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

}
