package simulassandra.client;

import java.io.IOException;

import simulassandra.client.app.ClientApp;
import simulassandra.client.app.Command;
import simulassandra.client.exceptions.ArgumentException;
import simulassandra.client.exceptions.KeyspaceException;
import simulassandra.client.exceptions.UnavailableKeyspaceException;
import simulassandra.client.utils.InputCommandParser;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
    public void testAppEnd()
    {
        ClientApp app = new ClientApp();
        try {
			boolean r = app.execute(new Command("quit"));
			assertTrue(r);
		} catch (ArgumentException | KeyspaceException
				| UnavailableKeyspaceException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
