package PRouter;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicApp {

	@RequestMapping("/")
	public String index() {

		String Message = "";
		Message = ServiceMessage();
		return Message;
	}

	/**
	 * Connect for router By IP
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Connect/{IP}")
	public String ConnectIP(@PathVariable String IP) throws SocketException, IOException {

		RouterAPIs.getInstance().connect(IP);

		return "The connection is successfully";
	}

	/**
	 * Show Version of Router
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Version")
	public String Version() throws SocketException, IOException {

		// RouterAPIs.getInstance().connect();
		RouterAPIs.ResponseCommand = "";
		RouterAPIs.getInstance().sendCommand("sh Version");
		// RouterAPIs.getInstance().disconnect();

		return RouterAPIs.ResponseCommand;
	}

	/**
	 * Show Interfaces of router
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	@RequestMapping("/Interfaces")
	public List<String> Interfaces() throws SocketException, IOException {

		// Send command for router
		// RouterAPIs.getInstance().connect();
		RouterAPIs.ResponseCommand = "";
		RouterAPIs.getInstance().sendCommand("show ip interface brief");
		// RouterAPIs.getInstance().disconnect();

		// Get the response and get from it Interfaces and IP
		RouterAPIs.ResponseCommand = RouterAPIs.ResponseCommand.replaceAll("( )+", " ");
		String[] RCommand1 = RouterAPIs.ResponseCommand.split("\n");

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

	/**
	 * Show Version of Interfaces and their IP
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/IntIP")
	public Map<String, String> Interfaces_IP() throws SocketException, IOException {

		// Send command for router
		// RouterAPIs.getInstance().connect();
		RouterAPIs.ResponseCommand = "";
		RouterAPIs.getInstance().sendCommand("show ip interface brief");
		// RouterAPIs.getInstance().disconnect();

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

	/**
	 * Add IP address for interface of router
	 * 
	 * @return
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/addInt")
	public String addIP_Int(@RequestBody String Interface) throws SocketException, IOException {

		RouterAPIs.ResponseCommand = "";

		Interfaces_IP();
		String addResponse = " ";
		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";
		RouterAPIs.ResponseCommand = "";

		String[] Inter = Interface.split(" ");

		RouterAPIs.getInstance().sendCommand("config t");
		RouterAPIs.getInstance().sendCommand("int " + Inter[0]);
		RouterAPIs.getInstance().sendCommand("ip address " + Inter[1] + " " + Inter[2]);
		RouterAPIs.getInstance().sendCommand("no shutdown");
		RouterAPIs.getInstance().sendCommand("exit");
		RouterAPIs.getInstance().sendCommand("exit");

		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";
		Interfaces_IP();
		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";

		return addResponse;
	}

	public String ServiceMessage() {

		return "Greetings from Spring Boot!!!";

	}

}
