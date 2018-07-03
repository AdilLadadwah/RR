package PRouter;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ServicApp {

	@RequestMapping("/")
	public String index() {

		String Message = "";
		Message = ServiceMessage();
		return Message;
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

		RouterAPIs.getInstance().connect();
		RouterAPIs.getInstance().sendCommand("sh Version");
		RouterAPIs.getInstance().disconnect();

		return RouterAPIs.ResponseCommand;
	}
	/**
	 * Show Version of Interfaces and their IP
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	@RequestMapping("/Interfaces")
	public String Interfaces() throws SocketException, IOException {

		
		// Send command  for router
		RouterAPIs.getInstance().connect();
		RouterAPIs.getInstance().sendCommand("show ip interface brief");
		RouterAPIs.getInstance().disconnect();

		// Get the response and get from it Interfaces and IP
		RouterAPIs.ResponseCommand = RouterAPIs.ResponseCommand.replaceAll("( )+", " ");
		String[] RCommand1 = RouterAPIs.ResponseCommand.split("\n");

		String Int_IP = "";
		for (int i = 1; i < RCommand1.length - 1; i++) {
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

		for (int i = 0; i < SInt_IP.length; i++)
			System.out.print("\n" + SInt_IP[i]);

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
		Gson gsonObject = new GsonBuilder().setPrettyPrinting().create();
		String JSON = gsonObject.toJson(map);
		
		// JSONObject JSON = new JSONObject(map);

		return JSON;
	}

	public String ServiceMessage() {

		return "Greetings from Spring Boot!!!";
	}

}
