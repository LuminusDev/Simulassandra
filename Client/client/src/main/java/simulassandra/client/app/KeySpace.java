package simulassandra.client.app;

import java.io.File;

import simulassandra.client.exceptions.KeyspaceException;
import utils.Validator;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class KeySpace {
	
	private String name;
	private String replication_class;
	private Integer replication_factor;
	private File data_file;
	
	private Connection connection;
	
	
	public KeySpace(String n, Connection c) throws KeyspaceException{
		this.name = n;
		this.connection = c;
		Statement q = QueryBuilder.select()
								  .from("schema_keyspaces", "system")
								  .where(QueryBuilder.eq("keyspace_name", this.name));
		ResultSet r = this.connection.execute(q);
		if(r.all().size() <= 0){
			throw new KeyspaceException("This keyspace doesn't exist.");
		} else {
			this.replication_class = r.all().get(0).getString("strategy_class");
			String[] replication_factor = r.all().get(0).getString("strategy_options").split(":");
			String rf = replication_factor[1].replaceAll("\"", "").replaceAll("}", "");
			this.replication_factor = Integer.parseInt(rf);
		}
	}
	
	public KeySpace(String n, String rc, Integer rf){
		this.name = n;
		this.replication_class = rc;
		this.replication_factor = rf;
		this.createKeyspace();
	}
	
	public void setFile(String path) throws KeyspaceException{
		File f = new File(path);
		if(Validator.data_file(f)){
			throw new KeyspaceException("File ".concat(path).concat(" doesn't exist"));
		}
		this.data_file = f;
	}
	
	private void createKeyspace(){
		 String s = "CREATE KEYSPACE ".concat(name)
								 .concat(" WITH REPLICATION = { 'class' : '")
								 .concat(replication_class)
								 .concat("', 'replication_factor' :")
								 .concat(replication_factor.toString())
								 .concat("};");
		 this.connection.execute(s);
	}
	
	public void copyDataFromFileQuery(){
		String s = "";
		this.connection.execute(s);
	}
}
