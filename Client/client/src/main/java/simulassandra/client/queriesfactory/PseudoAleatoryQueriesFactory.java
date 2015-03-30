package simulassandra.client.queriesfactory;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;
import simulassandra.client.utils.Interactor;

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
	public Boolean queriesfactory() {
		Integer nb_simul = 10; //A modifier
		Integer nb_request = 50;
		String id_column_name = "key";
		
		for(Integer i=0; i<nb_simul; i++){
			Table target = this.connection.getTable(Math.abs(generator.nextInt()));
			Long target_nb_rows = target.getNbRows();
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			if( target_nb_rows > 0){
				for(Integer j=0; j<nb_request; j++){
					Long id_target = Math.abs(generator.nextLong()%target_nb_rows);
					Statement query = QueryBuilder.select()
												  .from(target_keyspace, target_name)
												  .where(QueryBuilder.eq(id_column_name, id_target));
					this.connection.execute(query);
				}
			}
		}
		
		return Boolean.TRUE;
	}
}
