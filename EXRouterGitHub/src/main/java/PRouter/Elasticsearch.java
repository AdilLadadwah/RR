package PRouter;
//import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import java.util.concurrent.ExecutionException;

//import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;


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
		String StrIP = RouterAPIs.getInstance().getInterfacesIP().toString();
		IndexResponse response = client.prepareIndex("router", "RouterAPIs", "3").setSource(builder.startObject()
				.field("HostName", RouterAPIs.getInstance().getRouterOperation().getHostName())
				.field("Date", new Date().toString()).field("Version", RouterAPIs.getInstance().getInstallVersion())
				.field("ConfigRunning", RouterAPIs.getInstance().getConfigRunning())
				.field("InterfaceIP", StrIP.substring(1, StrIP.length() - 1)).endObject()).get();

		System.out.println(response);

	}

	public SearchHit[] getData() throws IOException, InterruptedException, ExecutionException {

		client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
		SearchResponse resp = client.prepareSearch("router").setTypes("RouterAPIs").setQuery(matchAllQuery).get();
		SearchHit[] hits = resp.getHits().getHits();

		return hits;

	}
}
