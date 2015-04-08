package simulassandra.client;

import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.utils.InputCommandParser;
import junit.framework.TestCase;

public class InputCommandParserTest extends TestCase {
	public void testCleaner1(){
		assertEquals( "r", InputCommandParser.inputCleaner("  r") );
	}
	public void testCleaner2(){
		assertEquals( "r", InputCommandParser.inputCleaner(" r") );
	}
	public void testCleaner3(){
		assertEquals( "r", InputCommandParser.inputCleaner("r") );
	}
	public void testCleaner4(){
		assertEquals( "r a b", InputCommandParser.inputCleaner("r a b") );
	}
	public void testCleaner5(){
		assertEquals( "r a b", InputCommandParser.inputCleaner("r  a b") );
	}
	public void testCleaner6(){
		assertEquals( "r a b", InputCommandParser.inputCleaner("r   a    b  ") );
	}
	public void testwhichAction1(){
		int i = InputCommandParser.whichAction(Config.CMD_CREATE_DATA_FILE);
		assertEquals( Config.ACT_CREATE_DATA_FILE, i);
	}
	public void testwhichAction2(){
		int i = InputCommandParser.whichAction(Config.CMD_QUIT);
		assertEquals( Config.ACT_QUIT, i);
	}
	public void testwhichAction3(){
		int i = InputCommandParser.whichAction(Config.CMD_IMPORT);
		assertEquals( Config.ACT_IMPORT, i);
	}
	public void testwhichAction4(){
		int i = InputCommandParser.whichAction(Config.CMD_SHOW_KEYSPACE);
		assertEquals( Config.ACT_SHOW_KEYSPACE, i);
	}
	public void testwhichAction5(){
		int i = InputCommandParser.whichAction(Config.CMD_SHOW_TABLE);
		assertEquals( Config.ACT_SHOW_TABLE, i);
	}
	public void testwhichAction6(){
		int i = InputCommandParser.whichAction(Config.CMD_QUERIESFACTORY);
		assertEquals( Config.ACT_QUERIESFACTORY, i);
	}
	public void testArguments(){
		try {
			int s = InputCommandParser.getArguments("queries a b c d").length;
			assertEquals(4,s);
		} catch (ArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testArguments2(){
		try {
			int s = InputCommandParser.getArguments("r").length;
			assertEquals(0,s);
		} catch (ArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
