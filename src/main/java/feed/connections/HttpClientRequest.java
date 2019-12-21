package feed.connections;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import com.google.api.client.util.Base64;

import feed.common.FeedErrorHandler;
import feed.config.common.DynamicProperty;
import feed.config.common.HttpConnectionConstants;
import feed.constants.Constants;
import feed.constants.Constants.FEED_ERROR_CODE;
import feed.encryption.CryptoException;
import feed.encryption.CryptoUtils;



/**
 * This class implements {@link HttpClientRequestAPI} and helps to send the 
 * request to the server in order to create feeds with default properties
 * <p>
 * Instantiates {@link HttpPost} object
 * <p>
 */

public class HttpClientRequest implements HttpClientRequestAPI {
	private static final Logger LOGGER = Logger.getLogger(HttpClientRequest.class.getName());
	private String configFilePath;

	FeedErrorHandler feedErrorHandler = new FeedErrorHandler();
	HttpConnectionConstants httpConnectionConstants = DynamicProperty.create(HttpConnectionConstants.class,
			configFilePath);

	@SuppressWarnings("resource")
	public <REQ> CloseableHttpResponse sendHttpPost(String url, String requestJson) {
		CloseableHttpResponse response = null;

		try {
			LOGGER.log(Level.FINER, "Send POST request:" + requestJson + " to url-" + url);
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Authorization",
					"Basic " + new String(Base64.encodeBase64(("username" + ":" + "password").getBytes())));

			StringEntity entity = new StringEntity(requestJson, "UTF-8");
			entity.setContentType("application/json");

			httpPost.setEntity(entity);

			response = new HttpClientFactory().getHttpClient().execute(httpPost);

			System.out.println("Status Code is " + response.getStatusLine().getStatusCode());
			if (null == response)
				feedErrorHandler.handleError(Constants.FEED_ERROR_CODE.FEED_ERR_404, new String[] { "No Response" });
			HttpEntity entity1 = response.getEntity();
			if (entity1 != null) {
				InputStream instream = entity.getContent();
				try {
					System.out.println("Done ");
				} finally {
					instream.close();
					entity1.consumeContent();
				}
			}
		} catch (IOException e) {
			feedErrorHandler.handleError(FEED_ERROR_CODE.FEED_ERR_100, new String[] { e.getMessage() }, e);

		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return response;

	}

	public <REQ> CloseableHttpResponse sendHttpPut(String url, REQ request) {
		// TODO Auto-generated method stub
		return null;
	}

	public CloseableHttpResponse sendHttpGet(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public CloseableHttpResponse sendHttpDelete(String url, List<Header> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String confogFilePath) {
		this.configFilePath = confogFilePath;
	}

	public HttpConnectionConstants getHttpConnectionConstants() {
		return httpConnectionConstants;
	}

	public void setHttpConnectionConstants(HttpConnectionConstants httpConnectionConstants) {
		this.httpConnectionConstants = httpConnectionConstants;
	}

}
