package PRouter;

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
	

	public String ServiceMessage() {
	
		return "Greetings from Spring Boot!!!";
	}

}

		
