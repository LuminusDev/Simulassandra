package simulassandra.client.app;

import simulassandra.client.exceptions.UnavailableKeyspaceException;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.TableMetadata;

/**
 * Objet représentant une table
 * Ne contient qu'un résumé des informations sur la table
 * @author Guillaume Marques <guillaume.marques[a]gmail.com>
 */
public class Table {
	
	private String name;
	private Long nb_rows;
	private KeySpace keyspace;
	private TableMetadata table_metadata;
	
	/**
	 * Constructeur
	 * @param name nom de la table
	 * @param ks keyspace dans lequel est situé la table
	 * @param td TableMetadata, metadata de la table
	 * @throws UnavailableKeyspaceException Impossibilité d'effectuer des requêtes dans
	 * le keyspace
	 */
	public Table(String name, KeySpace ks, TableMetadata td) throws UnavailableKeyspaceException{
		this.name = name;
		this.keyspace = ks;
		this.table_metadata = td;
		updateNbRows();
	}
	
	/**
	 * Dénombrement des lignes (enregistrements) de la table et mise à jour
	 * de l'attribut correspondant
	 * @throws UnavailableKeyspaceException Impossibilité d'effectuer des requêtes dans
	 * le keyspace
	 */
	private void updateNbRows() throws UnavailableKeyspaceException{
		this.nb_rows = this.keyspace.countRowsInTable(this.name);
	}
	
	/**
	 * Retourne le nombre d'enregistrements
	 * @return long
	 */
	public Long getNbRows(){
		return this.nb_rows;
	}
	
	/**
	 * Retourne le nom du keyspace dans lequel est situé la table
	 * @return String
	 */
	public String getKeyspace(){
		return this.keyspace.getName();
	}
	
	/**
	 * Retourne le nom de la table
	 * @return String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Metadata de la table formatées
	 * @return meta données formatées
	 */
	public String getMetadata(){
		String s = new String("Table named "+getName()+" \n");
		s += getNbRows()+" row(s)\n\n";
		
		s += String.format("%-30s", "Column name");
		s += "Type";
		s += "\n";
		for(ColumnMetadata c : this.table_metadata.getColumns()){
			s += String.format("%-30s", c.getName());
			s += c.getType();
			s += "\n";
		}
		return s;
	}
}
