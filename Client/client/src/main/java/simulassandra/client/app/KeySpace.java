package simulassandra.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnavailableKeyspaceException;
import simulassandra.client.utils.Interactor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * Objet représentant un keyspace
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class KeySpace {
	
	private KeyspaceMetadata keyspace;
	private Connection connection;
	private Collection<Table> tables;
	
	/**
	 * Constructeur pour un keyspace existant
	 * @param k, metadata du keyspace
	 * @param c, Connection connexion courante
	 * @throws KeyspaceException, keyspace inexistant
	 * @throws UnavailableKeyspaceException , impossibilité d'effectuer des requêtes sur le keyspace
	 */
	public KeySpace(KeyspaceMetadata k, Connection c) throws KeyspaceException, UnavailableKeyspaceException{
		
		if(k == null){
			throw new KeyspaceException("This keyspace doesn't exist.");
		}
		
		this.keyspace = k;
		this.connection = c;
		this.tables = new ArrayList<Table>();
		updateTablesList();
	}
	
	/**
	 * Constructeur pour créer un keyspace
	 * @param cluster, cluster dans lequel on souhaite créer le keyspace
	 * @param name, nom du keyspace
	 * @param replication_class, classe utilisée pour la stratégie de réplication
	 * @param replication_factor, facteur de réplication
	 * @param c, Connection, connexion courante
	 * @throws KeyspaceException Impossible de créer le keypspace
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
	
	/**
	 * Nom du keyspace
	 * @return String
	 */
	public String getName(){
		return this.keyspace.getName();
	}
	
	/**
	 * Facteurs de réplications pour chaque datacenter
	 * Format [datacenter] => replication_factor
	 * @return Map<String,String>
	 */
	public Map<String, String> getReplication(){
		return this.keyspace.getReplication();
	}
	
	/**
	 * Mise à jour de la liste des tables du keyspace
	 * et de leurs informations.
	 * @throws UnavailableKeyspaceException Impossibilité d'effectuer des requêtes 
	 * dans le keyspace
	 */
	private void updateTablesList() throws UnavailableKeyspaceException{
		this.tables.clear();
		Collection<TableMetadata> tables = this.keyspace.getTables();
		for(TableMetadata table : tables){
			this.tables.add(new Table(table.getName(), this, table));
		}
	}
	
	/**
	 * Dénombrement des lignes (enregistrements) dans chaque table
	 * @param table_name, nom de la table
	 * @return long, nombre de ligne
	 * @throws UnavailableKeyspaceException Impossibilité d'effectuer des requêtes
	 * dans le keyspace 
	 */
	public long countRowsInTable(String table_name) throws UnavailableKeyspaceException{
		Statement q = QueryBuilder.select().countAll().from(getName(),table_name);
		ResultSet count = this.connection.execute(q);
		if(count == null){
			throw new UnavailableKeyspaceException("An error occurred, keypsace is not available for querying.");
		}
		return count.one().getLong("count");
	}
	
	
	/**
	 * Exécute les requêtes contenues dans le fichier situé à l'adresse path
	 * @param path adresse du fichier
	 * @throws FileNotFoundException Le fichier n'existe pas
	 * @throws UnavailableKeyspaceException Impossiblité d'effectuer des requetes dans
	 * le keyspace
	 */
	public void executeFromFileQueries(String path) throws FileNotFoundException, UnavailableKeyspaceException{
		
		File f = new File(path);
		Scanner sc = new Scanner(f);
		String query = new String();

		while(sc.hasNext()){
			String line = sc.nextLine();
			
			if( line.matches(".*; {0,}") ){
				query += line;
				try {
					this.connection.execute(query);
				} catch (Exception e) {
					Interactor.displayException(e);
					Interactor.displayMessage("Query will be ignored");
				}
				query = "";
			} else {
				query += line;
			}
		}
		updateTablesList();
		sc.close();
	}
	
	/**
	 * Retourne la table en position i (modulo nombre de table) dans la liste
	 * des tables du keyspace
	 * @param i position
	 * @return Table
	 */
	public Table getTable(Integer i){
		return ((ArrayList<Table>) this.tables).get(Math.abs(i%this.tables.size()));
	}
	
	/**
	 * Retourne un résumé des métadata du keyspace prêt à l'affichage
	 * @return String metadata formatée
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
	 * Retourne la liste des tables prête à l'affichage
	 * @return String liste des tables à afficher
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
	
	/**
	 * Retourne un résumé des métadata de la table table_name prêt à l'affichage
	 * @param table_name, nom de la table
	 * @return String metadata formatée
	 */
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
