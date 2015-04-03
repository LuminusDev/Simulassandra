package simulassandra.client.queriesfactory;

import java.util.ArrayList;
import java.util.Collection;

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
	private String target_column;
	
	public BigFactory(Connection c, Integer nb_simulations, Integer nb_queries) {
		super(c, nb_simulations, nb_queries);
	}
	
	public BigFactory(Connection c, Long seed, Integer nb_simulations, Integer nb_queries){
		super(c, seed, nb_simulations, nb_queries);
	}

	@Override
	protected void askForConfiguration() {
		this.target_column = Interactor.basicInput("Target column : ");
		Interactor.displayMessage("Ce générateur effectue une requête du type WHERE ID IN (int,int...int) sur des tables choisies");
		Interactor.displayMessage("de manière aléatoire.");
		Interactor.displayMessage("Le nombre de requêtes effectuées est égal au nombre de simulations demandées.");
		Interactor.displayMessage("Le nombre d'entiers dans le IN correspond au nombre de requêtes demandées.");
	}

	@Override
	public Boolean queriesfactory() {
		for(Integer i=0; i<this.nb_simulations; i++){
			Table target = this.connection.getTable(Math.abs(generator.nextInt()));
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			Long target_nb_rows = target.getNbRows();
			Collection<Integer> id_targets = new ArrayList<Integer>();
			for(Integer j=0; j<this.nb_queries; j++){
				id_targets.add((int) Math.abs(generator.nextInt()%target_nb_rows));
			}
			addLog("IN table "+target_name+" lf "+id_targets.toString());
			Statement q = QueryBuilder.select().from(target_keyspace, target_name).where(QueryBuilder.in(this.target_column, id_targets.toArray()));
			this.connection.execute(q);
		}
		return Boolean.TRUE;
	}

}
