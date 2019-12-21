package feed.handlers;

import java.util.Map;

import org.slf4j.LoggerFactory;

import com.sun.istack.internal.logging.Logger;
import com.sun.org.apache.bcel.internal.Constants;

import feed.constants.Constants.FeedMetadataConfigResource;
import feed.processors.FeedProcessor;
import feed.processors.FeedProcessorFactory;

/**
 * This class extends {@link FeedExecutorHandler} and provides implementation of
 * executeHandler
 * <p>
 * Instantiates {@link FeedFactory} object and retrieves the configured feed to
 * be used for parsing the messages.
 * <p>
*/
public class FeedMetadataJSONHandler extends FeedExecutorHandler {
	private String feedType;
	private String inputJsonTemplatePath;
	// logger object
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedMetadataJSONHandler.class);

	/**
	 * This method retrieves fully qualified factory classname of
	 * {@link FeedProcessorFactory} and instantiated the factory object.
	 * 
	 * The control is passed to the the next handler in the chain to process the
	 * feed further
	 * 
	 *
	 */
	@Override
	protected boolean executeHandler() {

		// Instantiates ParserFactory Object
		FeedProcessor feedProcessor = FeedProcessorFactory.getFeedProcessor(
				Constants.FeedSourceType.valueOf(feedType.toUpperCase()).name(), inputJsonTemplatePath);

		Map<String, String> feedMetadataJsonMap = feedProcessor.prepareFeed((FeedConfigResources) feedMetadataConfigMap
				.get(FeedMetadataConfigResource.FEEDMETADATA_CONFIG_RESOURCE.name()));

		LOGGER.info("feedMetadataJsonMap {} ", feedMetadataJsonMap);

		feedMetadataConfigMap.put(FeedMetadataConfigResource.FEED_METADATA_JSON.name(), feedMetadataJsonMap);

		System.out.println("in json handler " + feedMetadataConfigMap);

		return true;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getInputJsonTemplatePath() {
		return inputJsonTemplatePath;
	}

	public void setInputJsonTemplatePath(String inputJsonTemplatePath) {
		this.inputJsonTemplatePath = inputJsonTemplatePath;
	}

}
