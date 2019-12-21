package feed.handlers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Uninterruptibles;

import feed.common.FeedMetadatProcessingException;
import feed.config.common.Constants;
import feed.connections.HttpClientRequest;
import feed.constants.Constants.FeedMetadataConfigResource;

/**
 * This class extends {@link FeedExecutorHandler} and provides implementation of
 * executeHandler
 * <p>
 * Method helps to populates request URL 
 * <p>
 *
 */

public class FeedInvocationHandler extends FeedExecutorHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedInvocationHandler.class);

	private static final int FEED_COMPLETION_WAIT_DELAY = 10;
	private HttpClientRequest httpClient;
	
	@Override
	protected boolean executeHandler() {
		@SuppressWarnings("unchecked")
		Map<String, String> feedMetaDataJSON = (Map<String, String>) feedMetadataConfigMap.get(FeedMetadataConfigResource.FEED_METADATA_JSON.name());
		String requestURL=(String) feedMetadataConfigMap.get(FeedMetadataConfigResource.REQUEST_URL.name());
		System.out.println("URL is"+requestURL);
		
		
		// Create Feed Using Post Request for each table
		try {
			long startTime =(long) 0.0;
			long endTime =(long) 0.0;
			long  duration=(long) 0.0;
			 
			System.out.println(" Feed Creation Process START time is :::::::::::: "+ System.currentTimeMillis());
			
		for (Map.Entry<String, String> feedEntry : feedMetaDataJSON.entrySet()) {
			LOGGER.debug("Key is : " + feedEntry.getKey() + "JSON is " + feedEntry.getValue());
			
			// create feed using rest API call
			startTime = System.currentTimeMillis();
			System.out.println(feedEntry.getKey()+" Feed Start time is -------- "+startTime);
			CloseableHttpResponse response=httpClient.sendHttpPost(requestURL, feedEntry.getValue());
			
			endTime= System.currentTimeMillis();
			
			System.out.println(feedEntry.getKey()+" Feed End time is -------- "+endTime);
			
			duration = duration + (endTime - startTime);
			
			
			System.out.println(feedEntry.getKey()+ " Total Execution time is "+duration);
			String feedJson = feedEntry.getValue();
			LOGGER.info(" Json is " + feedJson);
			
			}
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return false;
	}

	protected void waitForFeedToComplete() {
		// wait for feed completion by waiting for certain amount of time and then
		waitFor(FEED_COMPLETION_WAIT_DELAY, TimeUnit.SECONDS, "for feed to complete");
	}

	protected void waitFor(int delay, TimeUnit timeUnit, String msg) {
		System.out.println("Waiting {} {} {}..." + delay + timeUnit + msg);
		Uninterruptibles.sleepUninterruptibly(delay, timeUnit);
		LOGGER.info("Finished waiting {} {} {}" + delay + timeUnit + msg);
	}

	public void setHttpClient(HttpClientRequest httpClient) {
		this.httpClient = httpClient;
	}

}
