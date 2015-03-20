package simulassandra.client.app;

import java.io.File;

public class KeySpace {
	
	private String name;
	private String replication_type;
	private File data_file;
	
	public KeySpace(String n, String rt, File df){
		this.name = n;
		this.replication_type = rt;
		this.data_file = df;
	}
	
	
	

}
