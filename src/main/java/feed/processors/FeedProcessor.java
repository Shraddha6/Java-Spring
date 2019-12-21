package feed.processors;

import java.util.Map;


import feed.common.FeedUtils;
import feed.entity.FeedConfigResources;

public interface FeedProcessor {
	default FeedMetadata initFeedMetadata(String templatePath) {
	// feedMetadata object
	FeedMetadata feedMetadata = FeedUtils.getFeedMetaData(templatePath);
	return feedMetadata;
	}
	void process(String inputTemplatePath);
	
	Map<String,String> prepareFeed(FeedConfigResources feedConfigResource);
	
}
