package feed.builder;

import javax.jdo.metadata.Metadata;

import feed.handlers.FeedExecutorHandler;
import feed.handlers.FeedFactory;

/**
 * This class extends {@link FeedBuilder} ,helps to populate data source connections
*/
public class DataSourceBuilder extends FeedBuilder {
	
	public void getDataDourceAvailableOption() {
		
		System.out.println(" this.feedMetadata getProperties:  "+this.feedMetadata.getProperties());
		
	}

}
