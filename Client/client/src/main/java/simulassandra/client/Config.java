package simulassandra.client;

public class Config {
	
	public static final Integer TIMEOUT = 10000; //To know if a host is unreachable
	
	
	/**
	 *  Config for available command
	 *  HOW-TO : Add a command
	 *  Define constants ACT_, CMD_, NARGS_
	 *  Add the NARGS in the array ARGS
	 *  
	 *  Modify simulassandra.client.utils.InputCommandParser.whichACtion method
	 *  Modify simulassandra.client.app.ClientApp.exec method
	 */
	public static final int ACT_UNKNOWN = 0;
	public static final int ACT_HELP = 1;
	public static final int ACT_IMPORT = 2;
	public static final int ACT_SWITCH_KEYSPACE = 3;
	public static final int ACT_SHOW_TABLE = 4;
	public static final int ACT_QUERIESFACTORY = 5;
	public static final int ACT_QUIT = 6;
	public static final int ACT_SHOW_KEYSPACE = 7;
	public static final int ACT_LIST_TABLE = 8;
	public static final int ACT_CREATE_DATA_FILE = 9;

	
	public static final String CMD_HELP = "help";
	public static final String CMD_IMPORT = "import";
	public static final String CMD_SWITCH_KEYSPACE = "switchks";
	public static final String CMD_QUERIESFACTORY = "queries";
	public static final String CMD_QUIT = "quit";
	public static final String CMD_SHOW_KEYSPACE = "showksdata";
	public static final String CMD_LIST_TABLE = "lstable";
	public static final String CMD_SHOW_TABLE = "showtabledata";
	public static final String CMD_CREATE_DATA_FILE = "createdatafile";
	
	public static final Integer NARGS_HELP = 0;
	public static final Integer NARGS_IMPORT = 1;
	public static final Integer NARGS_SWITCH_KEYSPACE = 1;
	public static final Integer NARGS_QUERIESFACTORY = 1;
	public static final Integer NARGS_QUIT = 0;
	public static final Integer NARGS_SHOW_KEYSPACE = 0;
	public static final Integer NARGS_LIST_TABLE = 0;
	public static final Integer NARGS_SHOW_TABLE = 1;
	public static final Integer NARGS_CREATE_DATA_FILE = 4;
	
	
	public static final Integer[] ARGS = {	0, 
											Config.NARGS_HELP,
											Config.NARGS_IMPORT,
											Config.NARGS_SWITCH_KEYSPACE,
											Config.NARGS_SHOW_TABLE,
											Config.NARGS_QUERIESFACTORY,
											Config.NARGS_QUIT,
											Config.NARGS_SHOW_KEYSPACE,
											Config.NARGS_LIST_TABLE,
											Config.NARGS_CREATE_DATA_FILE};
		
}


