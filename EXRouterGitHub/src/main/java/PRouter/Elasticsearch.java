package PRouter;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;


public class Elasticsearch {
	
	public static void main(String[] args) throws UnknownHostException {

		
		// on startup

		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));


		
		System.out.println("ffdfffz");
		        
		// on shutdown

}
}