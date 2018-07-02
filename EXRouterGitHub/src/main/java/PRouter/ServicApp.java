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
	public String[] Interfaces() throws SocketException, IOException {

		RouterAPIs.getInstance().connect();
		RouterAPIs.getInstance().sendCommand("show ip interface brief");
		RouterAPIs.getInstance().disconnect();

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
		
		System.out.print("\nInterface \t\t IP-Address");
		
		for (int i = 0; i < SInt_IP.length; i++)
			System.out.print("\n" + SInt_IP[i]);
		
		String[] Interface =new String[SInt_IP.length];	
		String[] IP = new String[SInt_IP.length];
		String[] INTER_IP= new String[2];
		
		for (int i = 0; i < SInt_IP.length; i++)
			
		{
			INTER_IP = SInt_IP[i].split(" ", 2);
			Interface[i]=INTER_IP[0];
			IP[i]=INTER_IP[1];
		}

		return SInt_IP;
	}

	public String ServiceMessage() {

		return "Greetings from Spring Boot!!!";
	}

}
