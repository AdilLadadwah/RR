package PRouter;


import java.io.IOException;
import java.net.SocketException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Adil M Ladadwah
 * 
 *         This class represent main class for project, this project is
 *         springBoot Application use to display message and connect router
 *         using telnet protocol, send command and change IP address for
 *         interface gig0/1/7 for router
 *
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SocketException, IOException {

		SpringApplication.run(Application.class, args);
		
		/*
		RouterAPIs.getInstance().connect();
		RouterAPIs.getInstance().sendCommand("sh run");
		RouterAPIs.getInstance().sendCommand("config t");
		RouterAPIs.getInstance().sendCommand("int gig0/1/7");
		RouterAPIs.getInstance().sendCommand("ip address 30.0.2.8 255.255.255.0");
		RouterAPIs.getInstance().sendCommand("no shutdown");
		RouterAPIs.getInstance().sendCommand("exit");
		RouterAPIs.getInstance().sendCommand("exit");
		RouterAPIs.getInstance().sendCommand("sh run");
		RouterAPIs.getInstance().disconnect();*/

	}
}
