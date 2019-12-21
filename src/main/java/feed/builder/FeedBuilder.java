package feed.builder;

public class FeedBuilder {

	protected FeedMetadata feedMetadata;
	
	public FeedBuilder() {
	}

	public TableBuilder sourceTableBuild(FeedMetadata feedMetadata) {
		return new TableBuilder(feedMetadata);
	}

	public PropertyBuilder propertiesBuild(FeedMetadata feedMetadata) {
		return new PropertyBuilder(feedMetadata);
	}
	

	public void setFeedMetadata(FeedMetadata feedMetadata) {
		this.feedMetadata = feedMetadata;
	}

	public FeedMetadata getFeedMetadata() {
		return feedMetadata;
	}

}
