package utils;

import simulassandra.client.Config;
import simulassandra.client.exceptions.ArgumentException;

public class InputCommandParser {
	
	public static String inputCleaner(String cmd){
		cmd = cmd.replaceAll(" {2,}", " ");
		cmd = cmd.replaceAll("^ {1,}", "");
		return cmd;
	}
	
	public static Integer whichAction(String cmd){
		String[] split_cmd = cmd.split(" ");
		switch(split_cmd[0]){
			case Config.CMD_HELP:
				return Config.ACT_HELP;
			case Config.CMD_IMPORT:
				return Config.ACT_IMPORT;
			case Config.CMD_SWITCH_KEYSPACE:
				return Config.ACT_SWITCH_KEYSPACE;
			case Config.CMD_RESET_KEYSPACE:
				return Config.ACT_SWITCH_KEYSPACE;
			case Config.CMD_QUERIESFACTORY:
				return Config.ACT_QUERIESFACTORY;
			case Config.CMD_QUIT:
				return Config.ACT_QUIT;
			case Config.CMD_SHOW_KEYSPACE:
				return Config.ACT_SHOW_KEYSPACE;
			default:
				return Config.ACT_UNKNOWN;
		}
	}
	
	public static String[] getArguments(String cmd) throws ArgumentException{
		Integer idAction = InputCommandParser.whichAction(cmd);
		String[] split = cmd.split(" ");
		Integer length = Math.min(split.length-1, Config.ARGS[idAction]);
		
		if(length < Config.ARGS[idAction]){
			throw new ArgumentException("Too few arguments. Use help command");
		}
		
		String[] args = new String[length];
		if(length > 0){
			for(int i=0; i<length; i++){
				args[i] = split[i+1];
			}
		}
		return args;	
	}

}
