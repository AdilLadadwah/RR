package PRouter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;

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

		
		QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
		SearchResponse resp = client.prepareSearch("router").setTypes("RouterAPIs").setQuery(matchAllQuery).get();
		System.out.println(resp);

	}

}


