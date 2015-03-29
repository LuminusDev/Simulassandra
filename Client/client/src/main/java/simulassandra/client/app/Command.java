package simulassandra.client.app;

import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.utils.InputCommandParser;

public class Command {
	private Integer action;
	private String[] args;
	
	public Command(String cmd) throws ArgumentException{
		cmd = InputCommandParser.inputCleaner(cmd);
		this.action = InputCommandParser.whichAction(cmd);
		this.args = InputCommandParser.getArguments(cmd);
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
