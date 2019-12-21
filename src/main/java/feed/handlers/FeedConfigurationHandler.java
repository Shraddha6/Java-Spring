package feed.handlers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.bcel.internal.Constants;

import feed.common.FeedErrorHandler;
import feed.common.FeedUtils;
import feed.config.common.CommonUtils;
import feed.connections.HttpClientRequestAPI;
import feed.constants.Constants.FeedMetadataConfigResource;

public class FeedConfigurationHandler extends  FeedExecutorHandler{
	@Autowired
	FeedErrorHandler feedErrorHandler;
	private String feedConfigFilePath;
	

	public Map<String, FeedConfiguration> getFeedConfig() {
		return FeedUtils.readFeedConfiguration(feedConfigFilePath);
	}
	
	/**
	 * Populates configuration metadata map
	 */

	@Override
	protected boolean executeHandler() {
	
			FeedConfigResources feedConfigResource = CommonUtils.readFeedConfiguration(Constants.FEED_METADATA_CONFIG_FILENAME);
			feedMetadataConfigMap.put(FeedMetadataConfigResource.FEEDMETADATA_CONFIG_RESOURCE.name(), feedConfigResource);
			feedMetadataConfigMap.put(FeedMetadataConfigResource.REQUEST_URL.name(), feedConfigResource.getRequestURL());
			
			return feedMetadataConfigMap.size()>0?true:false;
	}

	public String getFeedConfigFilePath() {
		return feedConfigFilePath;
	}

	public void setFeedConfigFilePath(String feedConfigFilePath) {
		this.feedConfigFilePath = feedConfigFilePath;
	}

	
}
