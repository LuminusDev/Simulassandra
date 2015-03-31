package simulassandra.client.utils;

import java.util.Scanner;

import simulassandra.client.Config;
import simulassandra.client.app.Command;
import simulassandra.client.exceptions.ArgumentException;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

/**
 * Classe Interactor
 * Objet permettant d'afficher des messages sur la console
 * et de lire des saisies utilisateur sur la console.
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class Interactor {
	
	public static Scanner text_input = new Scanner(System.in); 
	
	//Affichage des piles d'execution pour le débogage.
	private static final Boolean DEBUG = Boolean.FALSE;
	
	/**
	 * Affiche le message msg puis demande à l'utilisateur de saisir une chaîne
	 * de caractère sur la console. La méthode s'execute jusqu'à que l'utilisateur
	 * saisisse un message.
	 * @param msg message à afficher
	 * @return String saisie de l'utilisateur
	 */
	private static String basicInput(String msg){
		displayMessage(msg);
		String entry = text_input.nextLine();
		if(entry.isEmpty()){
			displayMessage("Empty entry. Retry.");
			basicInput(msg);
		}
		return entry;
	}
	
	/**
	 * Affiche un message sur la console suivi d'un retour à la ligne
	 * @param msg message à afficher
	 */
	public static void displayMessage(String msg){
		System.out.println(msg);
	}
	
	/**
	 * Affichage d'un chevron puis invite l'utilisateur à saisir
	 * sa commande
	 * @return Command objet contenant les informations à propos
	 * de la commande saisie
	 * @throws ArgumentException nombre d'argument de la commande
	 * trop faible.
	 */
	public static Command commandInput() throws ArgumentException{
		System.out.print("\n> ");
		return new Command(text_input.nextLine());
	}
	
	public static void welcome(){
		displayMessage("Simulassandra Client");
	}
	
	public static String getHostAddress(){
		return basicInput("Please, tip the host address :");
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
		try{
			return Integer.parseInt(basicInput("Replication factor :"));
		} catch(Exception e){
			displayException(e);
			return getReplicationFactor();
		}
	}
	
	public static void checkingHost(String h){
		displayMessage("Checking if "+h+" host is reachable.");
	}
	
	public static void displayMetadata(Metadata m){
		displayMessage("Connected to cluster"+m.getClusterName());
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
	
	public static void displayException(Exception e){
		displayMessage("Error > "+e.getMessage());
		if(DEBUG){
			e.printStackTrace();
		}
	}
	
	public static void displayError(Error e){
		displayMessage("Fatal Error > "+e.getMessage());
		if(DEBUG){
			e.printStackTrace();
		}
	}

	public static void end(){
		System.out.println("End");
		text_input.close();
	}
	
	public static void showCommands(){
		displayMessage("Lists of commands available : ");
		displayMessage("- "+Config.CMD_HELP+" : show this list");
		displayMessage("- "+Config.CMD_IMPORT+" <file> : execute cql queries written in the file");
		displayMessage("- "+Config.CMD_SWITCH_KEYSPACE+" <ks> : switch to keyspace ks");
		displayMessage("- "+Config.CMD_QUERIESFACTORY+" <s> : execute queries generated by the seed s on the current keyspace");
		displayMessage("- "+Config.CMD_SHOW_KEYSPACE+" : show current keyspace metadata");
		displayMessage("- "+Config.CMD_LIST_TABLE+" : list tables available in the current keyspace");
		displayMessage("- "+Config.CMD_SHOW_TABLE+" <t> : show table t metadata");
		displayMessage("- "+Config.CMD_CREATE_DATA_FILE+" <file> <nb_tables> <nb_rows> <data_length>");
		displayMessage("Create or write in file <file> CQL queries to create <nb_tables> with <nb_rows>.");
		displayMessage("- "+Config.CMD_QUIT+" : quitter le programme");
	}

	public static void displayUnknownCommand() {
		displayMessage("This command does not exist. Use help.");
	}
}