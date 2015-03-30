package simulassandra.client.utils;

public class Chrono {
	
	private Long begin;
	
	public Chrono(){
		this.begin = System.currentTimeMillis();
	}
	
	public Long time(){
		return System.currentTimeMillis() - this.begin;
	}
}
