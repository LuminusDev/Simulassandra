package simulassandra.client;

public class Config {
	
	public static final Integer TIMEOUT = 10000; //To know if a host is unreachable
	
	public static final Integer ACT_HELP = 1;
	public static final Integer ACT_IMPORT = 2;
	public static final Integer ACT_SWITCH_KEYSPACE = 3;
	public static final Integer ACT_RESET_KEYSPACE = 4;
	public static final Integer ACT_QUERIESFACTORY = 5;
}
