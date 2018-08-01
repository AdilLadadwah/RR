package PRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class Test {

	public static void main(String[] args) throws UnknownHostException {

		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		GetResponse res1 = client.prepareGet("router", "RouterAPIs", "1").get();
		System.out.println(res1.getIndex() + " & " + res1.getType() + " & " + res1.getId() + " & " + res1.getVersion()+"\n\n\n");
		//System.out.println(res1.getSourceAsString());

		Map<String, Object> map=res1.getSourceAsMap();
		System.out.println(map.get("Router name"));
		System.out.println(map.get("IP"));
		System.out.println(map.get("Interface IP"));
		System.out.println(map.get("Show version"));
		System.out.println(map.get("Install IOS version"));
		System.out.println(map.get("Running Configuration"));

		/*
		 * 
		 * 		json2.put("HostName", "A"+Inter[0]);
		json2.put("Date", "B"+Inter[0]);
		json2.put("Version", "C"+Inter[0]);
		json2.put("ConfigRunning", "D"+Inter[0]);
		json2.put("InterfaceIP", "E"+Inter[0]);
				/*String addResponse = " ";
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
	

		// Return Response List Interfaces mapping with IPs before and after change
		return addResponse;*/
		 
		QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
		SearchResponse resp = client.prepareSearch("router").setTypes("RouterAPIs").setQuery(matchAllQuery).get();
		System.out.println(resp);
		
		

	}

}


/**
 * This method use to update change on information of router to database
 * ElasticSearch by update changed field in document
 * 
 * @throws IOException
 * @throws InterruptedException
 * @throws ExecutionException

public void update() throws IOException, InterruptedException, ExecutionException {

	// Connect to server database ElasticSearch
	client = TransportClient.builder().build()
			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

	// GetResponse before update
	GetResponse res1 = client.prepareGet("router", "RouterAPIs", "1").get();
	System.out.println(res1.getIndex() + " & " + res1.getType() + " & " + res1.getId() + " & " + res1.getVersion());
	System.out.println(res1.getSourceAsString());

	// Update Field IP by New Value 
	XContentBuilder builder1 = XContentFactory.jsonBuilder();
	UpdateRequest updateRequest1 = new UpdateRequest("router", "RouterAPIs", "1")
			.doc(builder1.startObject().field("IP", RouterAPIs.getInstance().getIP().toString()).endObject());
	client.update(updateRequest1).get();

	// Update Field InterfacesIP by New Value 
	XContentBuilder builder2 = XContentFactory.jsonBuilder();
	UpdateRequest updateRequest2 = new UpdateRequest("router", "RouterAPIs", "1").doc(builder2.startObject()
			.field("Interface IP", RouterAPIs.getInstance().getInterfacesIP().toString()).endObject());
	client.update(updateRequest2).get();

	// Update Date by last change in router 
	XContentBuilder builder3 = XContentFactory.jsonBuilder();
	UpdateRequest updateRequest3 = new UpdateRequest("router", "RouterAPIs", "1")
			.doc(builder3.startObject().field("Date", new Date()).endObject());
	client.update(updateRequest3).get();

	// GetResponse after update
	GetResponse res2 = client.prepareGet("router", "RouterAPIs", "1").get();
	System.out.println(res2.getIndex() + " & " + res2.getType() + " & " + res2.getId() + " & " + res2.getVersion());
	System.out.println(res2.getSourceAsString());
}
*/
