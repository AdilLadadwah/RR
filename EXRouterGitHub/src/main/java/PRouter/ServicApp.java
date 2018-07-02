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

		String RCommand = RouterAPIs.ResponseCommand;

		RCommand = RCommand.replaceAll("( )+", " ");
		String[] Command = RCommand.split("\n");
		
		String Int_IP = "";
		for (int i = 2; i < Command.length - 1; i++) {
			int n = 0;
			for (int j = 0; i < Command[i].length(); j++) {

				if (Command[i].charAt(j) == ' ' && n == 1)
					break;
				else if (Command[i].charAt(j) == ' ' && n == 0)
					n = 1;

				Int_IP = Int_IP + Command[i].charAt(j);
			}
			Int_IP = Int_IP +"\n";
		}

		
		String[] SInt_IP = Int_IP.split("\n");
		for (int i = 0; i < SInt_IP.length ; i++) 
		System.out.println(SInt_IP[i]);
		
		return Int_IP;
	}

	public String ServiceMessage() {

		return "Greetings from Spring Boot!!!";
	}

}
