package simulassandra.client.app;

import simulassandra.client.Config;

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
			default:
				return Config.ACT_UNKNOWN;
		}
	}
	
	public static String[] getArguments(String cmd){
		Integer idAction = InputCommandParser.whichAction(cmd);
		String[] split = cmd.split(" ");
		Integer length = Math.min(split.length-1, Config.ARGS[idAction]);
		
		String[] args = new String[length];
		if(length > 0){
			for(int i=0; i<length; i++){
				args[i] = split[i+1];
			}
		}
		return args;	
	}

}
