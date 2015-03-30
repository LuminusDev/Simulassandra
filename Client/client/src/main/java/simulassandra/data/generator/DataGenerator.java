package simulassandra.data.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

import simulassandra.client.app.KeySpace;
import simulassandra.client.utils.Interactor;

public class DataGenerator {
	private KeySpace keyspace;
	private File file;
	private Random generator;
	
	private Integer nb_tables;
	private Integer average_nb_rows;
	private Integer string_length;
	
	public DataGenerator(KeySpace k, String path, Integer nb_tables, Integer average_nb_rows, Integer string_length) throws IOException{
		this.keyspace = k;
		this.nb_tables = nb_tables;
		this.average_nb_rows = average_nb_rows;
		this.string_length = string_length;
		this.generator = new SecureRandom();
		
		this.file = new File(path);
		
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	
	public Boolean write(){
		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file, false)));
			
			//Création des tables
			for(Integer i=0; i<nb_tables; i++){
				writer.print("CREATE TABLE "+this.keyspace.getName()+".simutable_"+i.toString());
				writer.println("( key bigint PRIMARY KEY, data text );");
				writer.println("----");
				//Création des données
				for(Integer r=0; r<average_nb_rows; r++){
					writer.print("INSERT INTO "+this.keyspace.getName()+".simutable_"+i.toString()+" (key, data) VALUES ");
					writer.println("("+r.toString()+", '"+new BigInteger(130, generator).toString(string_length)+"');");
					writer.println("----");
				}
			}
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
	
	
	

}
