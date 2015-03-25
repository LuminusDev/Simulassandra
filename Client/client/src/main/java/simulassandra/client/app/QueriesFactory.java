package simulassandra.client.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Locale;


public abstract class QueriesFactory {
	
	protected Connection connection;
	private File log;
	
	public QueriesFactory(Connection c){
		this.connection = c;
	}
	
	protected void initData(){
		//Création nom fichier log  : format logsimulassandra-date
		String date = new SimpleDateFormat("dd-MM-yyyy-hh-mm", Locale.FRANCE).format(new Date());
		this.log = new File("logsimulassandra-".concat(date));
	}
	
	protected Boolean write(){
		return Boolean.TRUE;
	}
	
	public abstract Boolean run();
	
	
	
}
