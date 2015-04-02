package simulassandra.client.queriesfactory;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;
import simulassandra.client.utils.Interactor;

/**
 * Générateur de grosses requêtes (SELECT COUNT(*) FROM <TABLE>)
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class BigFactory extends QueriesFactory{

	public BigFactory(Connection c, Integer nb_simulations, Integer nb_queries) {
		super(c, nb_simulations, nb_queries);
	}
	
	public BigFactory(Connection c, Long seed, Integer nb_simulations, Integer nb_queries){
		super(c, seed, nb_simulations, nb_queries);
	}

	@Override
	protected void askForConfiguration() {
		Interactor.displayMessage("Ce générateur effectue une requête COUNT sur des tables choisies");
		Interactor.displayMessage("de manière alétoire.");
		Interactor.displayMessage("Le nombre de requêtes effectuées est égal au nombre de simulations demandées.");
	}

	@Override
	public Boolean queriesfactory() {
		for(Integer i=0; i<this.nb_simulations; i++){
			Table target = this.connection.getTable(Math.abs(generator.nextInt()));
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			addLog("count table "+target_name);
			Statement q = QueryBuilder.select().countAll().from(target_keyspace, target_name);
			this.connection.execute(q);
		}
		return Boolean.TRUE;
	}

}
