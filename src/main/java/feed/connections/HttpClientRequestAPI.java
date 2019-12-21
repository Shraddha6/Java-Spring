package feed.connections;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;


/**
 * This is {@link HttpClientRequestAPI} interface 
 */

public interface HttpClientRequestAPI {
	<REQ> CloseableHttpResponse sendHttpPost(String url, String requestJson);

	<REQ> CloseableHttpResponse sendHttpPut(String url, REQ request);

	CloseableHttpResponse sendHttpGet(String url);

	CloseableHttpResponse sendHttpDelete(String url, List<Header> headers);
}
