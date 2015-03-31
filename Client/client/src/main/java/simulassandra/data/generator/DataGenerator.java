package simulassandra.data.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

import simulassandra.client.app.KeySpace;
import simulassandra.client.utils.Interactor;

public abstract class DataGenerator {
	
	protected Random generator;
	
	private KeySpace keyspace;
	private File file;
	private Integer nb_tables;
	private Integer average_nb_rows;
	private Integer data_size;
	
	public DataGenerator(KeySpace k, String path, Integer nb_tables, Integer average_nb_rows, Integer data_size) throws IOException{
		this.keyspace = k;
		this.nb_tables = nb_tables;
		this.average_nb_rows = average_nb_rows;
		this.data_size = data_size;
		this.generator = new SecureRandom();
		
		this.file = new File(path);
		
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	public Integer get_nb_tables(){
		return this.nb_tables;
	}
	
	public Integer get_average_nb_rows(){
		return this.average_nb_rows;
	}
	
	public Integer get_data_size(){
		return this.data_size;
	}
	
	public String get_keyspace_name(){
		return this.keyspace.getName();
	}
	
	public Boolean write(){
		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file, false)));
			
			data_generation(writer);
			
			writer.close();
		} catch (IOException e) {
			Interactor.displayException(e);
			return Boolean.FALSE;
		} catch(Exception e) {
			Interactor.displayException(e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	protected abstract void data_generation(PrintWriter writer) throws IOException;
}
