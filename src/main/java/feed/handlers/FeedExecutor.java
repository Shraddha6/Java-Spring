package feed.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import feed.config.common.Constants;

/**
 * Responsible  to executors in chain of sequence
 * 
 */
public class FeedExecutor {
	
private Map<String, List<FeedExecutorHandler>> handlerMap = new HashMap<String, List<FeedExecutorHandler>>();
private static final Logger LOGGER = LoggerFactory.getLogger(FeedExecutor.class);

	public Map<String, List<FeedExecutorHandler>> getHandlerMap() {
		return handlerMap;
	}

	/**
	 * Setter method to set handler Map
	 * 
	 * @param handlerMap
	 */
	public void setHandlerMap(Map<String, List<FeedExecutorHandler>> handlerMap) {
		this.handlerMap = handlerMap;
	}
	
	/**
	 * Retrieves the executor chain and start the execution of the first handler in the chain
	 * 
	 * @param feedConfig
	 */
	public void execute(){
		
		List<FeedExecutorHandler> executeHandlerList = handlerMap.get(Constants.FEED_EXECUTOR_CHAIN);
		Iterator<FeedExecutorHandler> iterator = executeHandlerList.iterator();
		LOGGER.debug(" executeHandlerList {} ",executeHandlerList);
		if (iterator.hasNext()) {
			/*
			 *	executes handleMessage of next handler, if it has next handler
			 */
			iterator.next().executeChain(iterator);
		}
	}

}
