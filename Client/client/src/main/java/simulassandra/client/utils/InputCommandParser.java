package simulassandra.client.utils;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;

/**
 * Class InputCommandParser
 * 
 * Objet permettant de lire des chaînes de caractère pour les transformer
 * en commande.
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class InputCommandParser {
	
	/**
	 * Méthode inputCleaner
	 * Nettoyage de la chaîne de caractères soumise en entrée.
	 * Suppression des caractères inutiles.
	 * @param cmd
	 * @return String la chaîne nettoyée
	 */
	public static String inputCleaner(String cmd){
		cmd = cmd.replaceAll(" {2,}", " ");
		cmd = cmd.replaceAll("^ {1,}", "");
		return cmd;
	}
	
	/**
	 * Méthode whichAction
	 * Retourne l'identifiant de la commande défini dans Config
	 * @param cmd commande
	 * @return Integer identifiant de commande
	 */
	public static Integer whichAction(String cmd){
		String[] split_cmd = cmd.split(" ");
		switch(split_cmd[0]){
			case Config.CMD_HELP:
				return Config.ACT_HELP;
			case Config.CMD_IMPORT:
				return Config.ACT_IMPORT;
			case Config.CMD_SWITCH_KEYSPACE:
				return Config.ACT_SWITCH_KEYSPACE;
			case Config.CMD_QUERIESFACTORY:
				return Config.ACT_QUERIESFACTORY;
			case Config.CMD_QUIT:
				return Config.ACT_QUIT;
			case Config.CMD_SHOW_KEYSPACE:
				return Config.ACT_SHOW_KEYSPACE;
			case Config.CMD_LIST_TABLE:
				return Config.ACT_LIST_TABLE;
			case Config.CMD_SHOW_TABLE:
				return Config.ACT_SHOW_TABLE;
			case Config.CMD_CREATE_DATA_FILE:
				return Config.ACT_CREATE_DATA_FILE;
			default:
				return Config.ACT_UNKNOWN;
		}
	}
	
	/**
	 * Méthode getArguments
	 * Retourne les arguments de la commande
	 * @param cmd commande
	 * @return String[] tableau d'arguments
	 * @throws ArgumentException nombre d'arguments entrée inférieur au nombre
	 * d'arguments attendus.
	 */
	public static String[] getArguments(String cmd) throws ArgumentException{
		Integer idAction = InputCommandParser.whichAction(cmd);
		String[] split = cmd.split(" ");
		Integer length = Math.min(split.length-1, Config.ARGS[idAction]);
		
		if(length < Config.ARGS[idAction]){
			throw new ArgumentException("Too few arguments. Use help command");
		}
		
		String[] args = new String[length];
		if(length > 0){
			for(int i=0; i<length; i++){
				args[i] = split[i+1];
			}
		}
		return args;	
	}
}
