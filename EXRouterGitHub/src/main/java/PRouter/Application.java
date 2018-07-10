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
 *         using TelNet protocol, send command and change IP address for router
 *         and store data of router using ElasicSearch.
 *
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SocketException, IOException {

		SpringApplication.run(Application.class, args);
	}
}
