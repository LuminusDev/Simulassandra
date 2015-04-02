package simulassandra.client.queriesfactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import simulassandra.client.Config;
import simulassandra.client.app.Connection;
import simulassandra.client.utils.Chrono;
import simulassandra.client.utils.Interactor;

/**
 * Classe mère des générateur de requêtes
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public abstract class QueriesFactory {
	
	private File log;
	private Collection<String> log_msg;
	private Long seed;
	
	protected Random generator;
	protected Connection connection;
	protected Integer nb_simulations;
	protected Integer nb_queries; //Nombre de requêtes par simulation
	
	/**
	 * Constructeur pour seed non fixée
	 * Les constructeurs des classes filles doivent rester vides
	 * l'initialisation se faisant par la méthode askForConfiguration (voir exemple PearsonFactory)
	 * @param c connexion courante
	 * @param nb_simulations nombre de simulations
	 * @param nb_queries nombre de requêtes par simulation
	 */
	public QueriesFactory(Connection c, Long seed, Integer nb_simulations, Integer nb_queries){
		this.connection = c;
		this.seed = seed;
		this.generator = new Random();
		this.generator.setSeed(this.seed);
		this.nb_simulations = nb_simulations;
		this.nb_queries = nb_queries;
		askForConfiguration();
		initLog();
	}
	
	/**
	 * Constructeur pour seed fixée
	 * Les constructeurs des classes filles doivent rester vides
	 * l'initialisation se faisant par la méthode askForConfiguration (voir exemple PearsonFactory)
	 * @param c connexion courante
	 * @param seed
	 * @param nb_simulations nombre de simulations
	 * @param nb_queries nombre de reqauêtes par simulation
	 */
	public QueriesFactory(Connection c, Integer nb_simulations, Integer nb_queries){
		this(c,new Random().nextLong(), nb_simulations, nb_queries);
	}
	
	/**
	 * Initialisation du système de log
	 */
	protected void initLog(){
		this.log_msg = new ArrayList<String>();
		//Création nom fichier log  : format logsimulassandra-date
		String date = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.FRANCE).format(new Date());
		this.log = new File(Config.LOG_DIRECTORY+"/"+Config.LOG_NAME+"_"+date);
		if(!this.log.exists()){
			try {
				this.log.createNewFile();
			} catch (IOException e) {
				Interactor.displayException(e);
			}
		}
		
	}
	
	/**
	 * Retourne la seed utilisée
	 * @return Long 
	 */
	public Long getSeed(){
		return this.seed;
	}
	
	/**
	 * Ajout d'un message de log dans la liste des messages
	 * @param msg message à ajouter
	 */
	protected void addLog(String msg){
		String date = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss", Locale.FRANCE).format(new Date());
		this.log_msg.add(date+" - "+msg);
	}
	
	/**
	 * Écriture de tous les messages de log dans le fichier log
	 * Après écriture la liste des messages enregistrés est effacée
	 * 
	 * L'enregistrement de log pouvant avoir lieu durant la génération de requête, on les écrit
	 * une fois la génération terminée.
	 */
	private void writeLog(){
		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.log, false)));
			for(String msg : this.log_msg){
				writer.println(msg);
			}
			writer.close();
		} catch (IOException e) {
			Interactor.displayException(e);
		} catch(Exception e) {
			Interactor.displayException(e);
		}
		this.log_msg.clear();
	}
	
	/**
	 * Exécution de la génération de requêtes
	 */
	public void run(){
		Interactor.displayMessage("Starting quering.");
		Chrono c = new Chrono();
		this.queriesfactory();
		Long time = c.time();
		String end = "End ("+time.toString()+"ms).";
		addLog(end);
		Interactor.displayMessage(end);
		writeLog();
	}
	
	/**
	 * Initialisation des attributs des classes filles
	 */
	protected abstract void askForConfiguration();
	
	/**
	 * Définition de la génération de requêtes
	 * @return Boolean FAUX si une erreur est survenue
	 */
	public abstract Boolean queriesfactory();
}
