package simulassandra.client.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import simulassandra.client.exceptions.KeyspaceException;
import utils.Validator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * 
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 *
 */
public class KeySpace {
	
	private KeyspaceMetadata keyspace;
	private File data_file;
	
	private Connection connection;
	private Collection<Table> tables;
	
	
	/**
	 * 
	 * @param n
	 * @param c
	 * @throws KeyspaceException
	 */
	public KeySpace(KeyspaceMetadata k, Connection c) throws KeyspaceException{
		
		if(k == null){
			throw new KeyspaceException("This keyspace doesn't exist.");
		}
		
		this.keyspace = k;
		this.connection = c;
		this.tables = new ArrayList<Table>();
		updateTablesList();
	}
	
	/**
	 * 
	 * @param n
	 * @param rc
	 * @param rf
	 * @throws KeyspaceException 
	 */
	public KeySpace(Cluster cluster, String name, String replication_class, Integer replication_factor, Connection c) throws KeyspaceException{
		 this.connection = c;
		 
		 String s = "CREATE KEYSPACE ".concat(name)
				 .concat(" WITH REPLICATION = { 'class' : '")
				 .concat(replication_class)
				 .concat("', 'replication_factor' :")
				 .concat(replication_factor.toString())
				 .concat("};");
		 
		 this.connection.execute(s);
		 KeyspaceMetadata ksm = cluster.getMetadata().getKeyspace(name);
		 if(ksm == null){
			 throw new KeyspaceException("Impossible to create the keyspace ".concat(name));
		 }
		 this.keyspace = ksm;
		 this.tables = new ArrayList<Table>();
	}
	
	
	public String getName(){
		return this.keyspace.getName();
	}
	
	public Map<String, String> getReplication(){
		return this.keyspace.getReplication();
	}
	
	/**
	 * 
	 */
	private void updateTablesList(){
		Collection<TableMetadata> tables = this.keyspace.getTables();
		for(TableMetadata table : tables){
			this.tables.add(new Table(table.getName(), this));
		}
	}
	
	
	/**
	 * 
	 * @param name
	 * @param query_args
	 */
	public void createTable(String name, String query_args){
		String s = "CREATE TABLE ".concat(getName())
								  .concat(".")
								  .concat(name)
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
		Statement q = QueryBuilder.select().countAll().from(getName(),table_name);
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
	public void copyDataFromFileQuery(String table){
	
		this.connection.execute("TRUNCATE ".concat(table));
		this.connection.execute("COPY ".concat(this.getName()).concat(".").concat(table)
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
