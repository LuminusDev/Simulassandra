package simulassandra.client.queriesfactory;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;
import simulassandra.client.utils.Interactor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * Générateur de requêtes suivant une loi de distribution uniforme
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class PseudoAleatoryFactory extends QueriesFactory {
	private String target_column;
	
	public PseudoAleatoryFactory(Connection c, Integer nb_simulations, Integer nb_queries){
		super(c, nb_simulations, nb_queries);
	}
	
	public PseudoAleatoryFactory(Connection c, Long seed, Integer nb_simulations, Integer nb_queries){
		super(c,seed, nb_simulations, nb_queries);
	}
	
	@Override
	protected void askForConfiguration() {
		this.target_column = Interactor.basicInput("Target column : ");
	}
	
	@Override
	public Boolean queriesfactory() {
		for(Integer i=0; i<this.nb_simulations; i++){
			Table target = this.connection.getTable(Math.abs(generator.nextInt()));
			Long target_nb_rows = target.getNbRows();
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			if( target_nb_rows > 0){
				for(Integer j=0; j<this.nb_queries; j++){
					Long id_target = Math.abs(generator.nextLong()%target_nb_rows);
					addLog("select table "+target_name+" row "+id_target);
					Statement query = QueryBuilder.select()
												  .from(target_keyspace, target_name)
												  .where(QueryBuilder.eq(this.target_column, id_target));
					this.connection.execute(query);
				}
			}
		}
		
		return Boolean.TRUE;
	}
}
