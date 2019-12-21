package feed.builder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;



import feed.dbcp.DBCPDataSourceFactory;

public class TableBuilder extends FeedBuilder {

	public TableBuilder(FeedMetadata feedMetadata) {
		this.feedMetadata = feedMetadata;

	}

	public TableBuilder sourceTableSchemaName(String dbName, TableDetails tableDetail) {
		
			feedMetadata.getTable().getSourceTableSchema().setName(dbName.concat(".").concat(tableDetail.getTableName()));
			feedMetadata.getTable().getFeedTableSchema().setName(null);
			//feedMetadata.getTable().
		return this;

	}

	public TableBuilder existingTableSchemaName(String dbName, String tableName ) {

		feedMetadata.getTable().setExistingTableName(dbName.concat(Constants.DOT).concat(tableName));
		return this;
	}


	public TableBuilder method(String methodName) {

		feedMetadata.getTable().setMethod(methodName);
		return this;
	}

	public TableBuilder description(String description) {

		feedMetadata.getTable().setDescription(description);
		return this;
	}

	public TableBuilder partitions(List<PartitionField> partitions) {
		
		feedMetadata.getTable().setPartitions(partitions);
		
		return this;
	}
	
	
	public TableBuilder tableType(String tableType) {

		feedMetadata.getTable().setTableType(tableType);
		return this;
	}

	public TableBuilder option(TableOptions tableOptions) {

		feedMetadata.getTable().setOptions(tableOptions);
		return this;
	}
	
	public TableBuilder targetFormat(String targetFormat) {

		feedMetadata.getTable().setTargetFormat(targetFormat);
		return this;
	}
	
	public TableBuilder feedFormat(String feedFormat) {

		feedMetadata.getTable().setFeedFormat(feedFormat);
		return this;
	}
	
	public TableBuilder targetMergeStrategy(String targetMergeStrategy) {

		feedMetadata.getTable().setTargetMergeStrategy(targetMergeStrategy);
		return this;
	}
	
	public TableBuilder setSourceTableIncrementalDateField(String sourceTableIncrementalDateField) {

		feedMetadata.getTable().setSourceTableIncrementalDateField(sourceTableIncrementalDateField);
		return this;
	}
	
	public TableBuilder fieldPolicy(List<Field> columnDetails) {
		PolicyBuilder policyBuilder=new PolicyBuilder();
		List<FieldPolicy> fieldPolicies=policyBuilder.populateFieldPolicy(columnDetails);
		feedMetadata.getTable().setFieldPolicies(fieldPolicies);
		return this;
	}
	
	
	 
//	  public TableBuilder tableFieldsBuilder(List<ColumnDetails> columnDetails,String sourceTableSchemaName,String tableSchemaName ) {
//
//		
//		feedMetadata.getTable().getSourceTableSchema().getFields().addAll(populateColumns(columnDetails));
//		feedMetadata.getTable().getSourceTableSchema().setName(sourceTableSchemaName);
//		feedMetadata.getTable().getTableSchema().getFields().addAll(populateColumns(columnDetails));
//		feedMetadata.getTable().getTableSchema().setName(tableSchemaName);
//		
//	
//		feedMetadata.getTable().getFeedTableSchema().getFields().addAll(populateColumns(columnDetails));
//
//		return this;
//	}
	 public TableBuilder tableFieldsBuilder(TableSchema tableSchrma,String sourceTableSchemaName,String tableSchemaName ) {
		 
		 
		 		feedMetadata.getTable().getSourceTableSchema().getFields().addAll(tableSchrma.getFields());
		 		feedMetadata.getTable().getSourceTableSchema().setName(sourceTableSchemaName);
		 		
		 		feedMetadata.getTable().getTableSchema().getFields().addAll(tableSchrma.getFields());
		 		feedMetadata.getTable().getTableSchema().setName(tableSchemaName);
		 		
		 	
		 		feedMetadata.getTable().getFeedTableSchema().getFields().addAll(tableSchrma.getFields());
		
		 		return this;
		 	}

	private List<DefaultField> populateColumns(List<ColumnDetails> columnDetails) {
		List<DefaultField> defaultFieldList=new ArrayList<>();
		for (ColumnDetails columns : columnDetails) {

			DefaultField f1 = new DefaultField();
		
			f1.setName(columns.getField());
			f1.setNativeDataType(columns.getNativeDataType());
			f1.setDescription("");
			f1.setSampleValues(null);
			f1.setDerivedDataType(columns.getDerivedType().toLowerCase());
			f1.setPrimaryKey(columns.getPrimaryKey());
			f1.setNullable(columns.getIsNull());
			f1.setModifiable(true);//Not available in source table
			f1.setUpdatedTracker(false);//Not available in source table
			f1.setCreatedTracker(false);//Not available in source table
			f1.setSampleValues(new ArrayList<>());
			f1.setTags(null);

			defaultFieldList.add(f1);
//			sourceTableSchema.getFields().add(f1);
//			tableSchema.getFields().add(f1);
//			sourceTableSchema.getFields().add(f1);
		
		}
		
		return defaultFieldList;
	}
	public FeedMetadata build() {

		return feedMetadata;
	}

}
