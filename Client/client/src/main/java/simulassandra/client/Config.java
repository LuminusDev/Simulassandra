package simulassandra.client;

public class Config {
	
	public static final Integer TIMEOUT = 10000; //To know if a host is unreachable
	
	public static final int ACT_UNKNOWN = 0;
	public static final int ACT_HELP = 1;
	public static final int ACT_IMPORT = 2;
	public static final int ACT_SWITCH_KEYSPACE = 3;
	public static final int ACT_RESET_KEYSPACE = 4;
	public static final int ACT_QUERIESFACTORY = 5;
	public static final int ACT_QUIT = 6;
	
	public static final String CMD_HELP = "help";
	public static final String CMD_IMPORT = "import";
	public static final String CMD_SWITCH_KEYSPACE = "keyspace";
	public static final String CMD_RESET_KEYSPACE = "reset";
	public static final String CMD_QUERIESFACTORY = "queries";
	public static final String CMD_QUIT = "quit";
	
	public static final Integer NARGS_HELP = 0;
	public static final Integer NARGS_IMPORT = 1;
	public static final Integer NARGS_SWITCH_KEYSPACE = 1;
	public static final Integer NARGS_RESET_KEYSPACE = 0;
	public static final Integer NARGS_QUERIESFACTORY = 1;
	public static final Integer NARGS_QUIT = 0;
	
	
	public static final Integer[] ARGS = {	99, 
											Config.NARGS_HELP,
											Config.NARGS_IMPORT,
											Config.NARGS_SWITCH_KEYSPACE,
											Config.NARGS_RESET_KEYSPACE,
											Config.NARGS_QUERIESFACTORY,
											Config.NARGS_QUIT};
		
}


