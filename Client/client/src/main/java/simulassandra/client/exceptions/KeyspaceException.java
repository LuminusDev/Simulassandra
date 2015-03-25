package simulassandra.client.exceptions;

public class KeyspaceException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -311940110094692864L;


	public KeyspaceException(){
		super();
	}
	
	public KeyspaceException(String s){
		super(s);
	}

}
