package feed.connections;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import feed.handlers.FeedExecutorHandler;



/**
 * This class extends {@link HttpClientAPI} and provides implementation of
 * HttpClientFactory
 * <p>
 * Instantiates {@link HttpClients} object
 * <p>
 */

public class HttpClientFactory implements HttpClientAPI,Closeable {
	private static final Logger LOGGER = Logger.getLogger(HttpClientFactory.class.getName());

	protected CloseableHttpClient httpClient;

    public HttpClientFactory() {
    	this.httpClient = HttpClients.createDefault();
    }

    public void close() throws IOException {
        try {
            LOGGER.log(Level.INFO, "Closing HttpClient instance");
            this.httpClient.close();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception when trying to free http client resource", e);
        }
    }
	public CloseableHttpClient getHttpClient() {
		// TODO Auto-generated method stub
		return this.httpClient;
	}

	
}
