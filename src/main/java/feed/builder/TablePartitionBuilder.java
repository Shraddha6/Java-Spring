package feed.builder;

import java.util.ArrayList;
import java.util.List;


public class TablePartitionBuilder extends FeedBuilder {

	public TablePartitionBuilder() {
		super();

	}

	public List<PartitionField> populateTablePartitions(FeedConfiguration feedConfig) {
		List<PartitionField> partitionFields=new ArrayList<PartitionField>();
		
			if(null!=feedConfig.getTablePartitions() && feedConfig.getTablePartitions().size()>0) {
				
				for(TablePartitionConfig tablePartitionFeedConfig: feedConfig.getTablePartitions()){
					PartitionField partitions=new PartitionField();
					partitions.setField(tablePartitionFeedConfig.getSourceField());
					partitions.setFormula("val");
					partitions.setPosition(tablePartitionFeedConfig.getPosition());
					partitions.setSourceDataType(tablePartitionFeedConfig.getSourceDataType());
					partitions.setSourceField(tablePartitionFeedConfig.getSourceField());
					partitionFields.add(partitions);
					
				}
			
			}
	
		return partitionFields;
	}

	
}
