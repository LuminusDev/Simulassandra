package simulassandra.client.app;

import java.util.Collection;

import simulassandra.client.exceptions.ArgumentException;

public class Command {
	private Integer action;
	private String[] args;
	
	public Command(String cmd){
		
	}
	
	public Integer getAction(){
		return this.action;
	}
	
	public String getArg(Integer i) throws ArgumentException{
		if(i >= args.length){
			throw new ArgumentException("Non-existent argument");
		}
		return args[i];
	}
}
