package PRouter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	String HostName = "10.63.10.206";

	RouterOperation router = new RouterOperation(telnetClient, inRouter, outRouter, PassWord, HostName);

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
	
	public RouterOperation getRouterOperation() {
		return router;
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

	public String getVersion() {

		ResponseCommand = "";
		sendCommand("sh Version");

		return ResponseCommand;
	}

	public String getInstallVersion() {

		ResponseCommand = "";
		sendCommand("sh Version");
		return ResponseCommand;
	}

	public String getConfigRunning() {
		RouterAPIs.ResponseCommand = "";
		sendCommand("sh Run");

		return ResponseCommand;
	}

	public List<String> getInterfaces() {

		ResponseCommand = "";
		sendCommand("show ip interface brief");
		ResponseCommand = ResponseCommand.replaceAll("( )+", " ");
		String[] RCommand1 = ResponseCommand.split("\n");

		String Int_IP = "";
		for (int i = 2; i < RCommand1.length - 1; i++) {
			for (int j = 0; i < RCommand1[i].length(); j++) {

				if (RCommand1[i].charAt(j) == ' ')
					break;

				Int_IP = Int_IP + RCommand1[i].charAt(j);
			}
			Int_IP = Int_IP + "\n";
		}

		String[] SInt = Int_IP.split("\n");
		List<String> Interfaces = new ArrayList<>();

		for (int i = 0; i < SInt.length; i++)
			Interfaces.add(SInt[i]);

		return Interfaces;
	}

	public Map<String, String> getInterfacesIP() {

		ResponseCommand = "";
		sendCommand("show ip interface brief");
		// Get the response and get from it Interfaces and IP
		RouterAPIs.ResponseCommand = RouterAPIs.ResponseCommand.replaceAll("( )+", " ");
		String[] RCommand1 = RouterAPIs.ResponseCommand.split("\n");

		String Int_IP = "";
		for (int i = 2; i < RCommand1.length - 1; i++) {
			int n = 0;
			for (int j = 0; i < RCommand1[i].length(); j++) {

				if (RCommand1[i].charAt(j) == ' ' && n == 1)
					break;
				else if (RCommand1[i].charAt(j) == ' ' && n == 0)
					n = 1;

				Int_IP = Int_IP + RCommand1[i].charAt(j);
			}
			Int_IP = Int_IP + "\n";
		}

		String[] SInt_IP = Int_IP.split("\n");

		// Split between interface and IP and Create Hash Map
		String[] Interface = new String[SInt_IP.length];
		String[] IP = new String[SInt_IP.length];
		String[] INTER_IP = new String[2];

		HashMap<String, String> hmap = new HashMap<String, String>();

		for (int i = 0; i < SInt_IP.length; i++)

		{
			INTER_IP = SInt_IP[i].split(" ", 2);
			Interface[i] = INTER_IP[0];
			IP[i] = INTER_IP[1];

			hmap.put(Interface[i], IP[i]);
		}

		// Convert Hash map for gsonObject and convert it to String
		Map<String, String> map = new TreeMap<String, String>(hmap);

		return map;

	}

	public List<String> getIP() {
		ResponseCommand = "";
		sendCommand("show ip interface brief");
		// Get the response and get from it Interfaces and IP
		RouterAPIs.ResponseCommand = RouterAPIs.ResponseCommand.replaceAll("( )+", " ");
		String[] RCommand1 = RouterAPIs.ResponseCommand.split("\n");

		String Int_IP = "";
		for (int i = 2; i < RCommand1.length - 1; i++) {
			int n = 0;
			for (int j = 0; i < RCommand1[i].length(); j++) {

				if (RCommand1[i].charAt(j) == ' ' && n == 1)
					break;
				else if (RCommand1[i].charAt(j) == ' ' && n == 0)
					n = 1;

				Int_IP = Int_IP + RCommand1[i].charAt(j);
			}
			Int_IP = Int_IP + "\n";
		}

		String[] SInt_IP = Int_IP.split("\n");

		// Split between interface and IP and Create Hash Map

		String[] INTER_IP = new String[2];

		List<String> IP = new ArrayList<>();

		for (int i = 0; i < SInt_IP.length; i++)

		{
			INTER_IP = SInt_IP[i].split(" ", 2);
			IP.add(INTER_IP[1]);

		}

		// Convert Hash map for gsonObject and convert it to String

		return IP;

	}

}
