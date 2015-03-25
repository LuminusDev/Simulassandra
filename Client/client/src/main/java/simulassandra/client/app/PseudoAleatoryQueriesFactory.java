package simulassandra.client.app;

import java.util.HashMap;
import java.util.Random;


public class PseudoAleatoryQueriesFactory extends QueriesFactory {

	private Long seed;
	private Random generator;
	
	public PseudoAleatoryQueriesFactory(Connection c){
		super(c);
		this.generator = new Random();
		this.seed = this.generator.nextLong();
		this.generator.setSeed(seed);
	}
	
	public PseudoAleatoryQueriesFactory(Connection c, Long seed){
		super(c);
		this.seed = seed;
		this.generator = new Random();
		this.generator.setSeed(seed);
	}
	
	public Long getSeed(){
		return this.seed;
	}
	
}
