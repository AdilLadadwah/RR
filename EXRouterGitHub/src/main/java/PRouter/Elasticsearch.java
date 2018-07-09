package PRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.json.JSONObject;

import com.google.gson.Gson;


public class Elasticsearch {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

		
		// on startup

		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));


		/*Map<String, Object> json = new HashMap<String, Object>();
		json.put("user","kimchy");
		json.put("postDate",new Date());
		json.put("message","trying out Elasticsearch");*/
		
		
		
		/*
		String jso = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";



		IndexResponse response = client.prepareIndex("twitter", "tweet", "1")

		        .setSource(jso)
		        .get();
		/*
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();	
		System.out.println("\\n+++++++++++++++++++\\n");	
		System.out.println(_index+" & "+_type+" & "+_id+" & "+_version+" & "+created );*/
		

		//DeleteResponse responsee = client.prepareDelete("twitter", "tweet", "1").get();
		//System.out.println(responsee.getIndex()+" & "+responsee.getType()+" & "+responsee.getId()+" & "+responsee.getVersion() );
		
		
		
		/*GetResponse responsse = client.prepareGet("twitter", "tweet", "1").get();
		System.out.println(responsse.getIndex()+" & "+responsse.getType()+" & "+responsse.getId()+" & "+responsse.getVersion() );
		System.out.println(responsse.getSourceAsString());*/

		
		
		/*QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
		System.out.println(matchAllQuery);		
		SearchResponse response = client.prepareSearch("twitter")
		        .setTypes("tweet")
		        .setQuery(matchAllQuery)                 // Query    // Filter
		        .setFrom(0).setSize(60).setExplain(true)
		        .get();	
		System.out.println(response);*/
		
		
		
		/*XContentBuilder builder = XContentFactory.jsonBuilder();			
		UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "1")
		        .doc(builder.startObject().field("user", "MK").endObject());
		client.update(updateRequest).get();
		
	
		TimeUnit.SECONDS.sleep(10);
		
		responsse = client.prepareGet("twitter", "tweet", "1").get();
		System.out.println(responsse.getIndex()+" & "+responsse.getType()+" & "+responsse.getId()+" & "+responsse.getVersion() );
		System.out.println(responsse.getSourceAsString());
		
		
		matchAllQuery = QueryBuilders.matchAllQuery();
		System.out.println(matchAllQuery);		
		 response = client.prepareSearch("twitter")
		        .setTypes("tweet")
		        .setQuery(matchAllQuery)                 // Query    // Filter
		        .setFrom(0).setSize(60).setExplain(true)
		        .get();	
		 System.out.println(response);*/
	
	
		        
		// on shutdown

}
}