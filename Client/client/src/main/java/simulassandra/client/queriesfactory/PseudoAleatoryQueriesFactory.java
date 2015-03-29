package simulassandra.client.queriesfactory;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;


public class PseudoAleatoryQueriesFactory extends QueriesFactory {
	
	public PseudoAleatoryQueriesFactory(Connection c){
		super(c);
	}
	
	public PseudoAleatoryQueriesFactory(Connection c, Long seed){
		super(c,seed);
	}

	@Override
	public Boolean run() {
		Integer nb_simul = 10000; //A modifier
		Integer nb_request = 50;
		String id_column_name = "id";
		
		for(Integer i=0; i<nb_simul; i++){
			Table target = this.connection.getTable(generator.nextInt());
			Long target_nb_rows = target.getNbRows();
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			for(Integer j=0; j<nb_request; j++){
				Statement query = QueryBuilder.select()
											  .from(target_keyspace, target_name)
											  .where(QueryBuilder.eq(id_column_name, generator.nextLong()%target_nb_rows ));
				this.connection.execute(query);
			}
		}
		
		return Boolean.TRUE;
	}
}
