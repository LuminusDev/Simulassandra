package simulassandra.client.queriesfactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import simulassandra.client.app.Connection;
import simulassandra.client.utils.Chrono;
import simulassandra.client.utils.Interactor;


public abstract class QueriesFactory {
	
	protected Connection connection;
	private File log;
	private Long seed;
	protected Random generator;
	
	public QueriesFactory(Connection c){
		this.connection = c;
		this.generator = new Random();
		this.seed = this.generator.nextLong();
		this.generator.setSeed(seed);
	}
	
	public QueriesFactory(Connection c, Long seed){
		this.connection = c;
		this.seed = seed;
		this.generator = new Random();
		this.generator.setSeed(seed);
	}
	
	protected void initData(){
		//Création nom fichier log  : format logsimulassandra-date
		String date = new SimpleDateFormat("dd-MM-yyyy-hh-mm", Locale.FRANCE).format(new Date());
		this.log = new File("logsimulassandra-".concat(date));
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
