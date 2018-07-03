package PRouter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * @author Adil M Ladadwah
 * 
 *         This class represent APIs for router and include 3 main methods
 *         connect(), sendCommand and disconnect().
 * 
 */
public class RouterAPIs {

	private static RouterAPIs RouterAPI;
	public static String ResponseCommand;
	private String prompt = "#";

	TelnetClient telnetClient = new TelnetClient();
	InputStream inRouter = null;
	PrintStream outRouter = null;
	String PassWord = "lab";
	String HostName = "";

	Router router = new Router(telnetClient, inRouter, outRouter, PassWord, HostName);

	private RouterAPIs() {

	}

	/**
	 * This method use to make only one instance of RouterAPIs and return this
	 * instance.
	 * 
	 */
	public static RouterAPIs getInstance() {
		if (RouterAPI == null) {
			RouterAPI = new RouterAPIs();
		}
		return RouterAPI;
	}

	/**
	 * This function represent method to connect for router by using Telnet and
	 * after call this function you can send command line for router
	 * 
	 * @param iP
	 * 
	 * @param Router
	 *            Object
	 * @throws SocketException
	 * @throws IOException
	 * 
	 */
	public void connect(String IP) throws SocketException, IOException {
		try {
			int remoteport = 23;
			router.setHostName(IP);
			router.getTelnet().connect(router.getHostName(), remoteport);
			router.setIn(router.getTelnet().getInputStream());
			router.setOut(new PrintStream(router.getTelnet().getOutputStream()));

			readUntil("Password: ", router.getIn());
			write(router.getPassWord(), router.getOut());
			readUntil(">", router.getIn());
			write("en", router.getOut());
			readUntilLine("\n", router.getIn());
			readUntil("Password: ", router.getIn());
			write(router.getPassWord(), router.getOut());
			readUntil(prompt, router.getIn());
			ResponseCommand = "";
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function represent method to read input from router until matches with
	 * specific pattern
	 * 
	 * @param pattern
	 * @param in
	 *            from router
	 * @return reading from router and print it
	 * 
	 */

	public String readUntil(String pattern, InputStream in) {

		try {
			char lastChar = pattern.charAt(pattern.length() - 1);

			StringBuffer sb = new StringBuffer();
			char ch;
			ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				ResponseCommand = ResponseCommand + ch;
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function represent method to read input from router until matches with
	 * pattern end by new line "\n"
	 * 
	 * @param pattern
	 * @param in
	 *            from router
	 * @return reading from router
	 * 
	 */

	public String readUntilLine(String pattern, InputStream in) {

		try {
			char lastChar = pattern.charAt(pattern.length() - 1);

			StringBuffer sb = new StringBuffer();
			char ch;
			ch = (char) in.read();
			while (true) {
				sb.append(ch);
				// ResponseCommand = ResponseCommand + ch;
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function represent method to write value out to router
	 * 
	 * @param value
	 * @param out
	 *            to router
	 * 
	 */
	public void write(String value, PrintStream out) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
			ResponseCommand = ResponseCommand + value + "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function represent method to write command out to router and print
	 * response of router
	 * 
	 * @param command
	 * @param router
	 */
	public void sendCommand(String command) {
		try {
			write(command, router.getOut());
			readUntilLine("\n", router.getIn());
			readUntil(prompt, router.getIn());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * This function represent method to disconnect for router
	 * 
	 * @param router
	 */
	public void disconnect() {
		try {
			router.getTelnet().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
