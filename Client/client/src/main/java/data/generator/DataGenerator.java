package data.generator;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Random;

public class DataGenerator {
	private File file;
	private long seed;
	private Random generator;
	
	private final int MAX_R=500;
	private final int LIST_LENGTH=120;
	private final int B_INF = -180;
	private final int B_SUP = 180;
	
	
	private final int R_SIZE = Integer.SIZE + LIST_LENGTH*(2*Double.SIZE + Long.SIZE);
	
	
	public DataGenerator(File f, long s){
		this.file = f;
		this.seed = s;
		this.generator = new Random();
		this.generator.setSeed(seed);
	}
	
	private Double getRandomDouble(){
		return this.generator.nextDouble()*(B_SUP-B_INF)+B_INF;
	}
	
	private long getRandomLong(){
		return (long) Math.round(this.generator.nextDouble()*B_SUP);
	}
	
	public Boolean write(){
		
		
		for(Integer id=0; id<MAX_R; id++){
			ByteBuffer bb = ByteBuffer.allocate(R_SIZE);
			
			bb.putInt(id);
			for(Integer k=0; k<LIST_LENGTH; k++){
				bb.putDouble(getRandomDouble());
				bb.putDouble(getRandomDouble());
				bb.putLong(getRandomLong());
			}
			
		}
		
		return Boolean.TRUE;
	}
	
	
	

}
