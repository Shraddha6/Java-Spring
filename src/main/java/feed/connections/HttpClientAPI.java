package feed.connections;

import java.io.Closeable;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * This is {@link HttpClientAPI} interface 
 */

public interface HttpClientAPI extends Closeable {
	   
	 CloseableHttpClient getHttpClient();
	 
	 
}
