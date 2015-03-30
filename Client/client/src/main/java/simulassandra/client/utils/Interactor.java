package simulassandra.client.utils;

import java.util.Scanner;

import simulassandra.client.Config;
import simulassandra.client.app.Command;
import simulassandra.client.exceptions.ArgumentException;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class Interactor {
	
	public static Scanner text_input = new Scanner(System.in); 
	
	public static void welcome(){
		System.out.println("Simulassandra Client");
	}
	
	private static String basicInput(String msg){
		System.out.print(msg);
		return text_input.nextLine();
	}
	
	public static String getHostAddress(){
		return basicInput("Please, tip the host address :");
	}
	
	public static Command commandInput() throws ArgumentException{
		System.out.print("\n> ");
		return new Command(text_input.nextLine());
	}
	
	public static String getKeySpaceName(){
		return basicInput("Please, tip the keyspace name :");
	}
	
	public static String getDataFile(){
		return basicInput("Data file name :");
	}
	
	public static String getReplicationType(){
		return basicInput("Replication type :");
	}
	
	public static Integer getReplicationFactor() {
		return Integer.parseInt(basicInput("Replication factor :"));
	}
	
	public static void checkingHost(String h){
		displayMessage("Checking if "+h+" host is reachable.");
	}
	
	public static void displayMetadata(Metadata m){
		System.out.printf("Connected to cluster: %s\n", m.getClusterName());
		for ( Host host : m.getAllHosts() ) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		    host.getDatacenter(), host.getAddress(), host.getRack());
		}
	}
	
	public static Boolean question(String q){
		String answer = basicInput(q+" (y/n)");
		answer.replaceAll(" {1,}", "").toLowerCase();
		return "y".equals(answer);
	}
	
	public static void displayKeyspace(String ks_name){
		displayMessage("You are now using keyspace "+ks_name);
	}
	
	public static void displayMessage(String msg){
		System.out.print(msg+"\n");
	}
	
	public static void displayException(Exception e){
		displayMessage("Error > "+e.getMessage());
		e.printStackTrace();
	}
	
	public static void displayError(Error e){
		displayMessage("Fatal Error > "+e.getMessage());
	}

	public static void end(){
		System.out.println("End");
		text_input.close();
	}
	
	public static void showCommands(){
		System.out.println("Lists of commands available : ");
		System.out.println("- "+Config.CMD_HELP+" : show this list");
		System.out.println("- "+Config.CMD_IMPORT+" <file> : execute cql queries written in the file");
		System.out.println("- "+Config.CMD_SWITCH_KEYSPACE+" <ks> : switch to keyspace ks");
		System.out.println("- "+Config.CMD_QUERIESFACTORY+" <s> : execute queries generated by the seed s on the current keyspace");
		System.out.println("- "+Config.CMD_SHOW_KEYSPACE+" : show current keyspace metadata");
		System.out.println("- "+Config.CMD_LIST_TABLE+" : list tables available in the current keyspace");
		System.out.println("- "+Config.CMD_SHOW_TABLE+" <t> : show table t metadata");
		System.out.println("- "+Config.CMD_QUIT+" : quitter le programme");
	}

	public static void displayUnknownCommand() {
		System.out.println("This command does not exist. Use help.");
	}
}
