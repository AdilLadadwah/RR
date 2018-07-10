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
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;


public class Elasticsearch {
	

	private static Elasticsearch elasticsearch;
	
	public  Client client ;
	
	
		private Elasticsearch() {

		}

		/**
		 * This method use to make only one instance of RouterAPIs and return this
		 * instance.
		 * 
		 */
		public static Elasticsearch getInstance() {
			if (elasticsearch == null) {
				elasticsearch = new Elasticsearch();
			}
			return elasticsearch;
		}

		public void insert() throws IOException {
			
			client = TransportClient.builder().build()
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		/*Map<String, Object> json = new HashMap<String, Object>();
		json.put("Router name","Al");
		json.put("IP","B");
		json.put("Interface IP","C");
		json.put("Show version","D");
		json.put("Install IOS version","E");
		json.put("Date",new Date());
		json.put("Running Configuration","trying out Elasticsearch");*/
		
		
		XContentBuilder builder = XContentFactory.jsonBuilder();	
		IndexResponse response = client.prepareIndex("router", "RouterAPIs", "1")
		        .setSource(builder
		                    .startObject()
		                        .field("Router name",RouterAPIs.getInstance().getRouterOperation().getHostName())
		                        .field("IP",RouterAPIs.getInstance().getIP().toString())
		                        .field("Interface IP",RouterAPIs.getInstance().getInterfacesIP().toString())
		                        .field("Show version",RouterAPIs.getInstance().getVersion())
		                        .field("Install IOS version",RouterAPIs.getInstance().getInstallVersion())
		                        .field("Date",new Date())
		                        .field("Running Configuration",RouterAPIs.getInstance().getConfigRunning())
		                    .endObject()
		                  )
		        .get();
		




	/*	IndexResponse response = client.prepareIndex("router", "RouterAPIs", "1")

		        .setSource(json)
		        .get();
		//
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
		
		
		/*
		GetResponse res1 = client.prepareGet("twitter", "tweet", "1").get();
		System.out.println(res1.getIndex()+" & "+res1.getType()+" & "+res1.getId()+" & "+res1.getVersion() );
		System.out.println(res1.getSourceAsString());
		*/
		
		GetResponse res2 = client.prepareGet("router", "RouterAPIs", "1").get();
		System.out.println(res2.getIndex()+" & "+res2.getType()+" & "+res2.getId()+" & "+res2.getVersion() );
		System.out.println(res2.getSourceAsString());

		
		
	   QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();		
		SearchResponse resp = client.prepareSearch("router")
		        .setTypes("RouterAPIs")
		        .setQuery(matchAllQuery)                 // Query    // Filter
		        .get();	
		System.out.println(resp);
		
		
		
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