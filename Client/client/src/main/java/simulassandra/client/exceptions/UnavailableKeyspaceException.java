package simulassandra.client.exceptions;

public class UnavailableKeyspaceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnavailableKeyspaceException(){
		super();
	}
	
	public UnavailableKeyspaceException(String s){
		super(s);
	}

}
