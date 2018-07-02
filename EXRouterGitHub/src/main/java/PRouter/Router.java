package PRouter;

import java.io.InputStream;
import java.io.PrintStream;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * 
 * @author Adil M Ladadwah
 * 
 *         This class represent object Router and include TelnetClient , input
 *         from router, output to router, passWord to router and host name for
 *         router
 *
 */
public class Router {

	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String PassWord;
	private String HostName;

	/**
	 * This function represent method to create object of Router without attributes
	 * 
	 * @param telnet
	 * @param in
	 * @param out
	 * @param passWord
	 * @param hostName
	 * 
	 */
	public Router() {

	}

	/**
	 * This function represent method to create object of Router with these
	 * attributes
	 * 
	 * @param telnet
	 * @param in
	 * @param out
	 * @param passWord
	 * @param hostName
	 * 
	 */
	public Router(TelnetClient telnet, InputStream in, PrintStream out, String passWord, String hostName) {

		this.telnet = telnet;
		this.in = in;
		this.out = out;
		PassWord = passWord;
		HostName = hostName;
	}

	/**
	 * These functions represent methods to get and set attributes of object of
	 * Router
	 * 
	 */

	public TelnetClient getTelnet() {
		return telnet;
	}

	public void setTelnet(TelnetClient telnet) {
		this.telnet = telnet;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

}
