package simulassandra.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.utils.Validator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.ResultSet;
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
		this.tables.clear();
		Collection<TableMetadata> tables = this.keyspace.getTables();
		for(TableMetadata table : tables){
			this.tables.add(new Table(table.getName(), this, table));
		}
	}
	
	/**
	 * 
	 * @param table_name
	 * @return
	 */
	public long countRowsInTable(String table_name){
		Statement q = QueryBuilder.select().countAll().from(getName(),table_name);
		ResultSet count = this.connection.execute(q);
		return count.one().getLong("count");
	}
	
	/**
	 * 
	 * @param path
	 * @throws KeyspaceException
	 * @throws FileNotFoundException 
	 */
	public void executeFromFileQueries(String path) throws FileNotFoundException{
		
		File f = new File(path);
		Scanner sc = new Scanner(f);
		String query = new String();

		while(sc.hasNext()){
			String line = sc.nextLine();
			
			if( line.matches("^ {0,}-{4} {0,}$") ){
				this.connection.execute(query);
				query = "";
			} else {
				query += line;
			}
		}
		updateTablesList();
		sc.close();
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Table getTable(Integer i){
		return ((ArrayList<Table>) this.tables).get(i%this.tables.size());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMetadata(){
		String s = "Keyspace named "+getName()+"\n";
		s += "Replication Strategy used : \n";
		
		String key = new String();
		String value = new String();
		Iterator<String> i = getReplication().keySet().iterator();
		
		s += "\t Replication factor ([Datacenter] => (int)[replication_factor])\n";
		while (i.hasNext())
		{
		    key = (String)i.next();
		    value = (String)getReplication().get(key);
		    
		    if(key == "class"){
		    	s += "\t -------------------------------------\n";
		    	s += "\t Replication class used ";
		    	s += value;
		    	s += "\n\n";
		    } else {
		    	s += "\t\t [";
		    	s += String.format("%-20s", key);
		    	s += "] => ";
		    	s += value;
		    	s += "\n";
		    }
		}
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTablesList(){
		String s = new String("Keyspace named "+getName()+"\n");
		s += this.tables.size()+" table(s)\n\n";
		s += String.format("%-30s", "Table name");
		s += "Rows";
		s += "\n";
		
		for(Table t : this.tables){
			s += String.format("%-30s", t.getName());
			s += t.getNbRows().toString();
			s += "\n";
		}
		return s;
	}
	

	
	public String getTableMetadata(String table_name){
		table_name = table_name.replaceAll(" {1,}", "");
		for(Table t : this.tables){
			if(t.getName().equals(table_name)){
				return t.getMetadata();
			}
		}
		return "This table does not exist.";
	}
}
