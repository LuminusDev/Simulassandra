package simulassandra.client.queriesfactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import simulassandra.client.Config;
import simulassandra.client.app.Connection;
import simulassandra.client.utils.Chrono;
import simulassandra.client.utils.Interactor;


public abstract class QueriesFactory {
	
	private File log;
	private Collection<String> log_msg;
	private Long seed;
	
	protected Random generator;
	protected Connection connection;
	
	public QueriesFactory(Connection c, Long seed){
		this.connection = c;
		this.seed = seed;
		this.generator = new Random();
		this.generator.setSeed(seed);
	}
	
	public QueriesFactory(Connection c){
		this(c,new Random().nextLong());
	}
	
	protected void initData(){
		//Création nom fichier log  : format logsimulassandra-date
		String date = new SimpleDateFormat("dd-MM-yyyy-hh-mm", Locale.FRANCE).format(new Date());
		this.log = new File(Config.LOG_DIRECTORY+Config.LOG_NAME+"_"+date);
	}
	
	protected Boolean write(){
		return Boolean.TRUE;
	}
	
	public void run(){
		Interactor.displayMessage("Starting quering.");
		Chrono c = new Chrono();
		this.queriesfactory();
		Long time = c.time();
		Interactor.displayMessage("End ("+time.toString()+"ms).");
	}
	
	public abstract Boolean queriesfactory();
}
