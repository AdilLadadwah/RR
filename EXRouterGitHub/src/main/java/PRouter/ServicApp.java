package PRouter;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.json.Json;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Adil M Ladadwah
 *
 *         This class represent Controller of project and use services to
 *         connect and send Command for router and get response these commands
 *         and store information router by ElasticSearch
 * 
 */

@RestController
public class ServicApp {

	@RequestMapping("/")
	public String index() {

		return "Greetings from Spring Boot!!!";

	}

	/**
	 * This service use to connect for router By IP (HostName)
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Connect/{IP}")
	public String ConnectIP(@PathVariable String IP) throws SocketException, IOException {

		// Connect to Router by IP with RouterAPIs
		RouterAPIs.getInstance().connect(IP);

		return "The connection is successfully";
	}

	/**
	 * This service use to disconnect for router
	 * 
	 */

	@RequestMapping("/Disconnect")
	public void Disconnect() {

		// Disconnect from Router by RouterAPIs
		RouterAPIs.getInstance().disconnect();

	}

	/**
	 * This service use to show Version of Router
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Version")
	public String Version() throws SocketException, IOException {

		// Get Version of Router from RouterAPIs
		String Response = RouterAPIs.getInstance().getVersion();

		// Return Response Version of Router
		return Response;
	}

	/**
	 * This service use to show software version of Router
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/InstallVersion")
	public String InstallVersion() throws SocketException, IOException {

		// Get Install Version of Router from RouterAPIs
		String Response = RouterAPIs.getInstance().getInstallVersion();

		// Return Response Type Install Version of Router
		return Response;
	}

	/**
	 * This service use to show configuration Running for router
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/ConfigRunning")
	public String ConfigRunning() throws SocketException, IOException {

		// Get Configuration Running of Router from RouterAPIs
		String Response = RouterAPIs.getInstance().getConfigRunning();

		// Return Response Configuration Running of Router
		return Response;
	}

	/**
	 * This service use to show Interfaces of router
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Interfaces")
	public List<String> Interfaces() throws SocketException, IOException {

		// Get List Interfaces of Router from RouterAPIs
		List<String> Interfaces = RouterAPIs.getInstance().getInterfaces();

		// Return Response List Interfaces of Router
		return Interfaces;
	}

	/**
	 * This service use to show IPs of router
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/IP")
	public List<String> IP() throws SocketException, IOException {

		// Get List IPs of Router from RouterAPIs
		List<String> IP = RouterAPIs.getInstance().getIP();

		// Return Response List IPs of Router
		return IP;
	}

	/**
	 * This service use to show Version of Interfaces and their IP
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/IntIP")
	public Map<String, String> Interfaces_IP() throws SocketException, IOException {

		// Get List Interfaces mapping with theirs IPs of Router from RouterAPIs
		Map<String, String> map = RouterAPIs.getInstance().getInterfacesIP();

		// Return Response Interfaces mapping with IPs
		return map;
	}

	/**
	 * This service use to change or add IP address for interface of router and
	 * update changes on database by ElasticSearch
	 * 
	 * @throws SocketException
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/addInt")
	public String addIP_Int(@RequestBody String Interface)
			throws SocketException, IOException, InterruptedException, ExecutionException {

		String addResponse = " ";
		RouterAPIs.ResponseCommand = "";

		// Get List Interfaces mapping with theirs IPs of Router from RouterAPI
		Interfaces_IP();

		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";
		RouterAPIs.ResponseCommand = "";

		// Get RequestBody and Split to Name Interface, Address & SubMask
		String[] Inter = Interface.split(" ");

		// Send Command for router to change Or add this Interface with IP
		RouterAPIs.getInstance().sendCommand("config t");
		RouterAPIs.getInstance().sendCommand("int " + Inter[0]);
		RouterAPIs.getInstance().sendCommand("ip address " + Inter[1] + " " + Inter[2]);
		RouterAPIs.getInstance().sendCommand("no shutdown");
		RouterAPIs.getInstance().sendCommand("exit");
		RouterAPIs.getInstance().sendCommand("exit");

		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";

		// Get List Interfaces mapping with theirs IPs of Router from RouterAPI
		Interfaces_IP();
		addResponse = addResponse + RouterAPIs.ResponseCommand + "\n";

		// Update these changes on database by ElasticSearch
		Elasticsearch.getInstance().update();

		// Return Response List Interfaces mapping with IPs before and after change
		return addResponse;
	}

	/**
	 * This service use to insert object of Router and its informations from router
	 * by TelNet on database by ElasticSearch
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */

	@RequestMapping("/Insert")
	public String insert() throws SocketException, IOException {

		// Insert object of Router and its informations on database
		// Get Directly Informations from Router
		Elasticsearch.getInstance().insert();

		return "The Insertion  Is Successfully";
	}

	/**
	 * This service use to create object of Router from URL path print its
	 * informations
	 * 
	 * 
	 * @param router
	 */

	@RequestMapping("/InsertRouter")
	public void InsertRouter(Router router) {

		// Get Object of Router from URL Path and print its information

		System.out.println(router.getRouterName());
		System.out.println(router.getIP());
		System.out.println(router.getInterfaceIP());
		System.out.println(router.getVersion());
		System.out.println(router.getInstallVersion());
		System.out.println(router.getDate());
		System.out.println(router.getConfigRunning());

	}

}
