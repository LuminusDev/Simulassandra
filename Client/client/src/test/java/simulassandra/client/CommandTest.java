package simulassandra.client;

import simulassandra.client.app.Command;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.utils.Interactor;
import junit.framework.TestCase;

public class CommandTest extends TestCase {

	public void testCommand(){
		try {
			Command c= new Command("queries a b c d");
			assertEquals("a", c.getArg(0));
			assertEquals("b", c.getArg(1));
			assertEquals("c", c.getArg(2));
			assertEquals("d", c.getArg(3));
			int i = c.getAction();
			assertEquals(Config.ACT_QUERIESFACTORY, i);
		} catch (ArgumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Interactor.displayException(e);
		}
	}
	
	public void testCommand2(){
		try {
			Command c= new Command("r");
			assertEquals("", c.getArg(0));
			assertEquals("", c.getArg(1));
			assertEquals("", c.getArg(2));
			assertEquals("", c.getArg(3));
			int i = c.getAction();
			assertEquals(Config.ACT_UNKNOWN, i);
		} catch (ArgumentException e) {
			Interactor.displayException(e);
		}
	}
}
