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
		String Respons = RouterAPIs.getInstance().getVersion();
		// RouterAPIs.getInstance().disconnect();

		return Respons;
	}

	
	@RequestMapping("/InstallVersion")
	public String InstallVersion() throws SocketException, IOException {

		// RouterAPIs.getInstance().connect();
		String Respons = RouterAPIs.getInstance().getInstallVersion();
		// RouterAPIs.getInstance().disconnect();
		


		return Respons;
	}
	
	@RequestMapping("/ConfigRunning")
	public String ConfigRunning() throws SocketException, IOException {

		// RouterAPIs.getInstance().connect();
		String Respons=  RouterAPIs.getInstance().getConfigRunning();
		// RouterAPIs.getInstance().disconnect();
		
		return Respons;
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

		List<String> Interfaces=RouterAPIs.getInstance().getInterfaces();
		// RouterAPIs.getInstance().disconnect();

		// Get the response and get from it Interfaces and IP
		
		return Interfaces;
	}

	@RequestMapping("/IP")
	public List<String> IP() throws SocketException, IOException {

		// Send command for router
		// RouterAPIs.getInstance().connect();

		List<String> IP=RouterAPIs.getInstance().getIP();
		// RouterAPIs.getInstance().disconnect();

		// Get the response and get from it Interfaces and IP
		
		return IP;
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
		Map<String, String> map = RouterAPIs.getInstance().getInterfacesIP();
		// RouterAPIs.getInstance().disconnect();

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

	@RequestMapping("/insert")
	public String insert() throws SocketException, IOException {

		Elasticsearch.getInstance().insert();
		
		return "Good";
	}
	
	
	
}
