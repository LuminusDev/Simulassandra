package simulassandra.client.queriesfactory;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;

public class PearsonQueriesFactory extends QueriesFactory{
	
	private double a;
	private double l;
	private double m;
	private double v;
	private double k;

	public PearsonQueriesFactory(Connection c, double a, double l, double m, double v, double k) {
		super(c);
		if( m <= 0.5){
			m = 0.51;
		}
		this.a = a;
		this.l = l;
		this.m = m;
		this.v = v;
		this.k = k;
	}
	
	public PearsonQueriesFactory(Connection c, Long seed, double a, double l, double m, double v, double k) {
		super(c,seed);
		if( m <= 0.5){
			m = 0.51;
		}
		this.a = a;
		this.l = l;
		this.m = m;
		this.v = v;
		this.k = k;
	}

	@Override
	public Boolean queriesfactory() {
		Integer nb_simul = 10000; //A modifier
		Integer nb_request = 50;
		String id_column_name = "id";
		
		for(Integer i=0; i<nb_simul; i++){
			Table target = this.connection.getTable(generator.nextInt());
			Long target_nb_rows = target.getNbRows();
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			for(Integer j=0; j<nb_request; j++){
				Long id = pearsonFunction(this.generator.nextDouble())%target_nb_rows;
				Statement query = QueryBuilder.select()
											  .from(target_keyspace, target_name)
											  .where(QueryBuilder.eq(id_column_name, id ));
				this.connection.execute(query);
			}
		}
		
		return Boolean.TRUE;
		
	}
	
	private Integer pearsonFunction(double x){
		return (int) Math.round(k*Math.pow((1+Math.pow((x-l)/a,2)), -m)*Math.exp(-v*(1/Math.tan((x-l)/a))));
	}

	
}
