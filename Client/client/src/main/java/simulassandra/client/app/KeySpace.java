package simulassandra.client.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import simulassandra.client.exceptions.KeyspaceException;
import utils.Validator;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 */
public class KeySpace {
	
	private String name;
	private String replication_class;
	private Integer replication_factor;
	private File data_file;
	
	private Connection connection;
	private Collection<Table> tables;
	
	
	/**
	 * 
	 * @param n
	 * @param c
	 * @throws KeyspaceException
	 */
	public KeySpace(String n, Connection c) throws KeyspaceException{
		this.name = n;
		this.connection = c;
		Statement q = QueryBuilder.select()
								  .from("system", "schema_keyspaces")
								  .where(QueryBuilder.eq("keyspace_name", this.name));
		ResultSet r = this.connection.execute(q);
		if(r.all().size() <= 0){
			throw new KeyspaceException("This keyspace doesn't exist.");
		} else {
			this.replication_class = r.all().get(0).getString("strategy_class");
			String[] replication_factor = r.all().get(0).getString("strategy_options").split(":");
			String rf = replication_factor[1].replaceAll("\"", "").replaceAll("}", "");
			this.replication_factor = Integer.parseInt(rf);
			this.tables = new ArrayList<Table>();
			updateTablesList();
		}
	}
	
	/**
	 * 
	 * @param n
	 * @param rc
	 * @param rf
	 */
	public KeySpace(String n, String rc, Integer rf){
		this.name = n;
		this.replication_class = rc;
		this.replication_factor = rf;
		this.createKeyspace();
	}
	
	/**
	 * 
	 */
	private void updateTablesList(){
		ResultSet tables_results = this.connection.execute("DESCRIBE TABLES;");
		for(Row r : tables_results){
			String table_name =  r.getString(0);
			this.tables.add(new Table(table_name, this));
		}
	}
	
	/**
	 * 
	 */
	private void createKeyspace(){
		 String s = "CREATE KEYSPACE ".concat(name)
								 .concat(" WITH REPLICATION = { 'class' : '")
								 .concat(replication_class)
								 .concat("', 'replication_factor' :")
								 .concat(replication_factor.toString())
								 .concat("};");
		 this.connection.execute(s);
	}
	
	/**
	 * 
	 * @param table_name
	 * @return
	 */
	private Boolean existingTable(String table_name){
		for(Integer i=0; i<this.tables.size(); i++){
			if(table_name.equals(((ArrayList<Table>) this.tables).get(i).getName())){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 
	 * @param name
	 * @param query_args
	 */
	public void createTable(String name, String query_args){
		String s = "CREATE TABLE ".concat(name)
								  .concat(" ")
								  .concat(query_args);
		this.connection.execute(s);
		this.tables.add(new Table(name, this));
	}
	
	/**
	 * 
	 * @param table_name
	 * @return
	 */
	public Integer countRowsInTable(String table_name){
		Statement q = QueryBuilder.select().countAll().from(table_name);
		ResultSet count = this.connection.execute(q);
		return count.one().getInt(0);
	}
	
	/**
	 * 
	 * @param path
	 * @throws KeyspaceException
	 */
	public void setFile(String path) throws KeyspaceException {
		File f = new File(path);
		if(Validator.data_file(f)){
			throw new KeyspaceException("File ".concat(path).concat(" doesn't exist"));
		}
		this.data_file = f;
	}
	
	/**
	 * 
	 * @param table
	 * @throws KeyspaceException
	 */
	public void copyDataFromFileQuery(String table) throws KeyspaceException{
		if(!existingTable(table)){
			throw new KeyspaceException("Table ".concat(table).concat(" doesn't exist"));
		}
		
		this.connection.execute("TRUNCATE ".concat(table));
		this.connection.execute("COPY ".concat(table)
									   .concat(" FROM ")
									   .concat(this.data_file.getAbsolutePath()));
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Table getTable(Integer i){
		return ((ArrayList<Table>) this.tables).get(i%this.tables.size());
	}
}
