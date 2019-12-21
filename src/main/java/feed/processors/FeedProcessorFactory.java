package feed.processors;

import feed.config.common.Constants.FeedSourceType;

public abstract class FeedProcessorFactory {
	
	public static FeedProcessor getFeedProcessor(String feedType,String sampleTemplatePath) {
        switch (FeedSourceType.valueOf(feedType)) {
        case DB:
            return (FeedProcessor) DBFeedProcessor.getInstance(sampleTemplatePath);
        case FILESYSTEM:
            break;
        default:
            break;
        }
        return null;
    }
}