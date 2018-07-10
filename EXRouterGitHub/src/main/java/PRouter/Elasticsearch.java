package PRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class Elasticsearch {

	private static Elasticsearch elasticsearch;

	public Client client;

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

		XContentBuilder builder = XContentFactory.jsonBuilder();
		IndexResponse response = client.prepareIndex("router", "RouterAPIs", "1").setSource(builder.startObject()
				.field("Router name", RouterAPIs.getInstance().getRouterOperation().getHostName())
				.field("IP", RouterAPIs.getInstance().getIP().toString())
				.field("Interface IP", RouterAPIs.getInstance().getInterfacesIP().toString())
				.field("Show version", RouterAPIs.getInstance().getVersion())
				.field("Install IOS version", RouterAPIs.getInstance().getInstallVersion()).field("Date", new Date())
				.field("Running Configuration", RouterAPIs.getInstance().getConfigRunning()).endObject()).get();

		String _index = response.getIndex();
		String _type = response.getType(); // Document ID (generated or not) String
		String _id = response.getId(); // Version (if it's the first time you index this

		long _version = response.getVersion(); //
		boolean created = response.isCreated();
		System.out.println("\\n+++++++++++++++++++\\n");
		System.out.println(_index + " & " + _type + " & " + _id + " & " + _version + " & " + created);

		// DeleteResponse responsee = client.prepareDelete("twitter", "tweet",
		// "1").get();
		// System.out.println(responsee.getIndex()+" & "+responsee.getType()+" &
		// "+responsee.getId()+" & "+responsee.getVersion() );

		/*
		 * QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery(); SearchResponse
		 * resp =
		 * client.prepareSearch("router").setTypes("RouterAPIs").setQuery(matchAllQuery)
		 * .get(); System.out.println(resp);
		 */

	}

	public void update() throws IOException, InterruptedException, ExecutionException {

		client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		GetResponse res1 = client.prepareGet("router", "RouterAPIs", "1").get();
		System.out.println(res1.getIndex() + " & " + res1.getType() + " & " + res1.getId() + " & " + res1.getVersion());
		System.out.println(res1.getSourceAsString());

		XContentBuilder builder1 = XContentFactory.jsonBuilder();
		UpdateRequest updateRequest1 = new UpdateRequest("router", "RouterAPIs", "1")
				.doc(builder1.startObject().field("IP", RouterAPIs.getInstance().getIP().toString()).endObject());
		client.update(updateRequest1).get();

		XContentBuilder builder2 = XContentFactory.jsonBuilder();
		UpdateRequest updateRequest2 = new UpdateRequest("router", "RouterAPIs", "1").doc(builder2.startObject()
				.field("Interface IP", RouterAPIs.getInstance().getInterfacesIP().toString()).endObject());
		client.update(updateRequest2).get();

		XContentBuilder builder3 = XContentFactory.jsonBuilder();
		UpdateRequest updateRequest3 = new UpdateRequest("router", "RouterAPIs", "1")
				.doc(builder3.startObject().field("Date", new Date()).endObject());
		client.update(updateRequest3).get();

		GetResponse res2 = client.prepareGet("router", "RouterAPIs", "1").get();
		System.out.println(res2.getIndex() + " & " + res2.getType() + " & " + res2.getId() + " & " + res2.getVersion());
		System.out.println(res2.getSourceAsString());
	}
}