package PRouter;

import java.io.IOException;
import java.net.SocketException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServicApp {

	@RequestMapping("/")
	public String index() {
		
		String Message="";
		Message= ServiceMessage();
		return Message;
	}
	

	@RequestMapping("/Version")
	public String Version() throws SocketException, IOException {
	
	RouterAPIs.getInstance().connect();
	RouterAPIs.getInstance().sendCommand("sh Version");
	RouterAPIs.getInstance().disconnect();
		
	return RouterAPIs.ResponseCommand;
	}
	
	
	@RequestMapping("/Interfaces")
	public String Interfaces() throws SocketException, IOException {
	
	RouterAPIs.getInstance().connect();
	RouterAPIs.getInstance().sendCommand("sh Version");
	RouterAPIs.getInstance().disconnect();
		
	return RouterAPIs.ResponseCommand;
	}
	public String ServiceMessage() {
	
		return "Greetings from Spring Boot!!!";
	}

}

		
