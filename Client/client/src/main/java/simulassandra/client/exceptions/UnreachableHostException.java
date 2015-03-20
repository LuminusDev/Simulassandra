package simulassandra.client.exceptions;

public class UnreachableHostException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -596425886853878418L;

	public UnreachableHostException(){
		super();
	}
	
	public UnreachableHostException(String s){
		super(s);
	}

}
