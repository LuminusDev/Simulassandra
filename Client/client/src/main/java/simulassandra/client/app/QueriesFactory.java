package simulassandra.client.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public abstract class QueriesFactory {
	
	private Connection connection;
	private File log;
	
	private HashMap<String,Integer> nbItems;
	
	public QueriesFactory(Connection c){
		this.connection = c;
	}
	
	protected void initData(){
		//Création nom fichier log  : format logsimulassandra-date
		String date = new SimpleDateFormat("dd-MM-yyyy-hh-mm", Locale.FRANCE).format(new Date());
		this.log = new File("logsimulassandra-".concat(date));
		
		//Récupération du nom des tables dans le keyspace et du nombre d'enregistrements qu'elles contiennent
		ResultSet tables = this.connection.execute("DESCRIBE TABLES;");
		for(Row r : tables){
			String table_name =  r.getString(0);
			Statement q = QueryBuilder.select().countAll().from(table_name);
			ResultSet count = this.connection.execute(q);
			Integer nbRows = count.one().getInt(0);
			nbItems.put(table_name, nbRows);
		}
		
	}
	
	protected Boolean write(){
		return Boolean.TRUE;
	}
	
	
	
}
