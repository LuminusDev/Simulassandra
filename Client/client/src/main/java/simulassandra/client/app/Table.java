package simulassandra.client.app;

public class Table {
	
	private String name;
	private Integer nb_rows;
	
	private KeySpace keyspace;
	
	public Table(String name, KeySpace ks){
		this.name = name;
		this.keyspace = ks;
		updateNbRows();
	}
	
	private void updateNbRows(){
		this.nb_rows = this.keyspace.countRowsInTable(this.name);
	}
	
	public Integer getNbRows(){
		return this.nb_rows;
	}
	
	public String getName(){
		return this.name;
	}

}
