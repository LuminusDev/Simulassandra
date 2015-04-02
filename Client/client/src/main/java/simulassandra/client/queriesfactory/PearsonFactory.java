package simulassandra.client.queriesfactory;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import simulassandra.client.app.Connection;
import simulassandra.client.app.Table;
import simulassandra.client.utils.Interactor;

/**
 * Générateur de requêtes suivant la loi de Pearson
 * @author Guillaume Marques <guillaume.marques33@gmail.com>
 */
public class PearsonFactory extends QueriesFactory{
	
	private String target_column;
	private float a;
	private float l;
	private float m;
	private float v;
	private float k;

	public PearsonFactory(Connection c, Integer nb_simulations, Integer nb_queries){
		super(c, nb_simulations, nb_queries);
	}
	
	public PearsonFactory(Connection c, Long seed, Integer nb_simulations, Integer nb_queries){
		super(c,seed, nb_simulations, nb_queries);
	}

	@Override
	protected void askForConfiguration() {
		this.target_column = Interactor.basicInput("Target column : ");
		
		Interactor.displayMessage("Configuration of the Pearson Queries Factory.");
		this.m = Interactor.basicInputToFloat("m = ");
		if( this.m <= 0.5){
			this.m = (float) 0.51;
		}
		this.a = Interactor.basicInputToFloat("a = ");
		this.l = Interactor.basicInputToFloat("l = ");
		this.v = Interactor.basicInputToFloat("v = ");
		this.k = Interactor.basicInputToFloat("k = ");
	}
	
	@Override
	public Boolean queriesfactory() {
		for(Integer i=0; i<this.nb_simulations; i++){
			Table target = this.connection.getTable(Math.abs(generator.nextInt()));
			Long target_nb_rows = target.getNbRows();
			String target_name = target.getName();
			String target_keyspace = target.getKeyspace();
			for(Integer j=0; j<this.nb_queries; j++){
				Long id = Math.abs(pearsonFunction(this.generator.nextDouble())%target_nb_rows);
				addLog("select table "+target_name+" row "+id);
				Statement query = QueryBuilder.select()
											  .from(target_keyspace, target_name)
											  .where(QueryBuilder.eq(this.target_column, id ));
				this.connection.execute(query);
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * Retourne la valeur de la loi de Pearson pour x
	 * @param x antécédent
	 * @return long image
	 */
	private Integer pearsonFunction(double x){
		return (int) Math.round(k*Math.pow((1+Math.pow((x-l)/a,2)), -m)*Math.exp(-v*(1/Math.tan((x-l)/a))));
	}
}
