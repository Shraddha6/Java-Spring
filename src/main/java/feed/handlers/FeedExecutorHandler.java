package feed.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import feed.config.handler.FeedConfigurationImpl;


/**
 * {@link FeedExecutorHandler} is an abstract class which provides abstract methods which needs to be implemented by the derived
 * class.
 * <p>
 * Provides book-keeping methods for any error occured while feed-execution.
 *
 */
public abstract class FeedExecutorHandler {
	
	static Map<String,Object> feedMetadataConfigMap=new HashMap<>();
	FeedConfigurationImpl feedConfiguration;
	//encryptPassword();

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of the current handler then calls the
	 * <code>handleMessage</code> of the next handler
	 * 
	 
	 * 
	 */
	public void executeChain(Iterator<FeedExecutorHandler> iterator) {

		boolean result = executeHandler();
		if (iterator.hasNext() && result) {
			iterator.next().executeChain(iterator);
		}
	}

	/**
	 * This is an abstract method which contains the business logic of a particular handler and needs to be implemented in Derived
	 * class.
	 * 
	 * @param message
	 *            Message object
	 * @param inputStream
	 *            Stream of data to persist
	 * @return
	 */
	protected abstract boolean executeHandler();

	public FeedConfigurationImpl getFeedConfiguration() {
		return feedConfiguration;
	}

	public void setFeedConfiguration(FeedConfigurationImpl feedConfiguration) {
		this.feedConfiguration = feedConfiguration;
	}

	
	
}
