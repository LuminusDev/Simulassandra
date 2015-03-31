package simulassandra.client.app;

import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.utils.InputCommandParser;

/**
 * Objet contenant la structure d'une commande, c'est-à-dire
 * l'identifiant de l'action à executer et ses arguments.
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 */
public class Command {
	
	private Integer action;
	private String[] args;
	
	/**
	 * Constructeur
	 * @param cmd Commande saisie par l'utilisateur
	 * @throws ArgumentException nombre d'arguments insuffisant
	 */
	public Command(String cmd) throws ArgumentException{
		cmd = InputCommandParser.inputCleaner(cmd);
		this.action = InputCommandParser.whichAction(cmd);
		this.args = InputCommandParser.getArguments(cmd);
	}
	
	/**
	 * Getteur
	 * @return Integer identification de l'action
	 */
	public Integer getAction(){
		return this.action;
	}
	
	/**
	 * Getteur
	 * @param i numéro de l'argument voulu (placement dans la commande)
	 * @return String l'argument saisi
	 * @throws ArgumentException argument inexistant
	 */
	public String getArg(Integer i) throws ArgumentException{
		if(i >= args.length){
			throw new ArgumentException("Non-existent argument");
		}
		return args[i];
	}
}
