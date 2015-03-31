package simulassandra.data.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

import simulassandra.client.app.KeySpace;

public class SimpleDataGenerator extends DataGenerator {
	
	private final Integer ecart = 500;
	private final String column_key_name = "key";
	private final String column_key_type = "bigint";
	private final String column_data_name = "data";
	private final String column_data_type = "text";
	private final String table_name = "simutable";

	public SimpleDataGenerator(KeySpace k, String path, Integer nb_tables,
			Integer average_nb_rows, Integer string_length) throws IOException {
		super(k, path, nb_tables, average_nb_rows, string_length);
	}

	@Override
	public void data_generation(PrintWriter writer) throws IOException {
		//Création des tables
		for(Integer i=0; i<get_nb_tables(); i++){
			writer.print("CREATE TABLE "+get_keyspace_name()+".simutable_"+i.toString());
			writer.print("( "+this.column_key_name+" "+this.column_key_type+" PRIMARY KEY,");
			writer.println(this.column_data_name+" "+this.column_data_type+");");
			
			Long nb_rows = get_nb_rows(this.ecart);
			//Création des données
			for(Integer r=0; r<nb_rows; r++){
				writer.print("INSERT INTO "+get_keyspace_name()+"."+this.table_name+"_"+i.toString());
				writer.print(" ("+this.column_key_name+","+this.column_data_name+") VALUES ");
				writer.println("("+r.toString()+", '"+new BigInteger(130, generator).toString(get_data_size())+"');");
			}
		}
	}
	
	private Long get_nb_rows(Integer ecart){
		Random r = new Random();
        Float epsilon = (float) ((r.nextInt(ecart) - ecart/2)/100);
        return (long) Math.round(epsilon); 
	}
	
	
}
