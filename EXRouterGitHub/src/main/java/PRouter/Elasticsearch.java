package PRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * 
 * @author Adil M Ladadwah
 *
 *         This class represent ElasticSearch database of project and use to
 *         connect to database,create index and create insert document of
 *         information router and use to update change in information router
 * 
 */

public class Elasticsearch {

	// Create Object of ElasticSearch and Client to perform ElasticSearch Operation

	private static Elasticsearch elasticsearch;
	public Client client;

	private Elasticsearch() {

	}

	/**
	 * This method use to make only one instance of ElasticSearch and return this
	 * instance.
	 * 
	 */

	public static Elasticsearch getInstance() {
		if (elasticsearch == null) {
			elasticsearch = new Elasticsearch();
		}
		return elasticsearch;
	}

	/**
	 * This method use to insert information of router to database ElasticSearch by
	 * create index and insert these information in document
	 * 
	 * @throws IOException
	 */
	public void insert() throws IOException {

		// Connect to server database ElasticSearch
		client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		// Create index and insert document information Router
		XContentBuilder builder = XContentFactory.jsonBuilder();
		IndexResponse response = client.prepareIndex("router", "RouterAPIs", "1").setSource(builder.startObject()
				.field("Router name", RouterAPIs.getInstance().getRouterOperation().getHostName())
				.field("IP", RouterAPIs.getInstance().getIP().toString())
				.field("Interface IP", RouterAPIs.getInstance().getInterfacesIP().toString())
				.field("Show version", RouterAPIs.getInstance().getVersion())
				.field("Install IOS version", RouterAPIs.getInstance().getInstallVersion()).field("Date", new Date())
				.field("Running Configuration", RouterAPIs.getInstance().getConfigRunning()).endObject()).get();

		// Use to delete document by id
		// DeleteResponse responsee = client.prepareDelete("router", "RouterAPIs", "1").get();

		// Use to search all document in index
		// QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
		// SearchResponse resp = client.prepareSearch("router").setTypes("RouterAPIs").setQuery(matchAllQuery).get();
		// System.out.println(resp);

	}
	
	/**
	 * This method use to update change on information of router to database
	 * ElasticSearch by update changed field in document
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */

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
}
