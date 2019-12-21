package feed.builder;

import org.apache.commons.lang3.StringUtils;


public class TableBucketBuilder extends FeedBuilder{
	
	private  String CLUSTERED_BY="CLUSTERED BY ";

	public TableBucketBuilder() {}	

public String buildBucketExpr(TableBucketConfig bucketConfig) {
	 StringBuilder sb=new StringBuilder();
    	sb.append(CLUSTERED_BY).append("(").append(StringUtils.join(bucketConfig.getColumnNames(), ',')).append(")").append(" ").append("INTO ").append(bucketConfig.getNoOfBuckets()).append(" BUCKETS ");
    	System.out.println("Bucket Expr is "+sb.toString());
    	return sb.toString();
    }
	
}


