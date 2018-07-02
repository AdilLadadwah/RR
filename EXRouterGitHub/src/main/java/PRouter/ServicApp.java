package PRouter;

import java.io.IOException;
import java.net.SocketException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicApp {

	@RequestMapping("/")
	public String index() {

		String Message = "";
		Message = ServiceMessage();
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
		RouterAPIs.getInstance().sendCommand("show ip interface brief");
		RouterAPIs.getInstance().disconnect();
		
		
		String RCommand=RouterAPIs.ResponseCommand;

		RCommand=RCommand.replaceAll("( )+"," ");
		String []Command=RCommand.split(" ");
		
		for(int i=1;i<Command.length;i++)
		System.out.print(Command[i]+"kk");
		
		return RCommand;
	}

	public String ServiceMessage() {

		return "Greetings from Spring Boot!!!";
	}

}
