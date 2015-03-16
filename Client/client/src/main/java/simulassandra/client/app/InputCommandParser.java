package simulassandra.client.app;

import java.util.ArrayList;
import java.util.Collection;

public class InputCommandParser {
	
	public static String inputCleaner(String cmd){
		return cmd;
	}
	
	public static Integer getAction(String cmd){
		return 1;
		
	}
	
	public static Object[] getArguments(String cmd){
		Collection<String> arguments = new ArrayList<String>();
		
		return arguments.toArray();
	}

}
