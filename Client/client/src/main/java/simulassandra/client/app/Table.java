package simulassandra.client.app;

import simulassandra.client.exceptions.UnavailableKeyspaceException;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.TableMetadata;

/**
 * 
 * @author Guillaume Marques <guillaume.marques[a]gmail.com>
 */
public class Table {
	
	private String name;
	private Long nb_rows;
	private KeySpace keyspace;
	private TableMetadata table_metadata;
	
	public Table(String name, KeySpace ks, TableMetadata td) throws UnavailableKeyspaceException{
		this.name = name;
		this.keyspace = ks;
		this.table_metadata = td;
		updateNbRows();
	}
	
	private void updateNbRows() throws UnavailableKeyspaceException{
		this.nb_rows = this.keyspace.countRowsInTable(this.name);
	}
	
	public Long getNbRows(){
		return this.nb_rows;
	}
	
	public String getKeyspace(){
		return this.keyspace.getName();
	}
	
	public String getName(){
		return this.name;
	}
	
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
