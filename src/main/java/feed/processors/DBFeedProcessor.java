package feed.processors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;


import feed.builder.FeedBuilder;
import feed.builder.FeedScheduler;
import feed.builder.TableBucketBuilder;
import feed.builder.TableOptionBuilder;
import feed.builder.TablePartitionBuilder;
import feed.common.FeedErrorHandler;
import feed.common.FeedUtils;
import feed.config.common.Constants;
import feed.config.common.Constants.Methods;
import feed.config.common.Constants.TableType;
import feed.dbcp.DBCPDataSourceFactory;
import feed.entity.FeedConfigResources;
import feed.entity.FeedConfiguration;
import feed.entity.TableBucketConfig;

public class DBFeedProcessor extends FeedBuilder implements FeedProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(DBFeedProcessor.class);

	static String feedTempaltePath;
	
	@Autowired
	FeedErrorHandler feedErrorHandler;
	private DBFeedProcessor() {
		
	}
	
	private void initFeedMetadata() {
		long startTime = System.currentTimeMillis();
		System.out.println(" Init Metadata Start time is ###### "+startTime);
		
		FeedMetadata feedMetadata=new FeedMetadata();
		feedMetadata = initFeedMetadata(feedTempaltePath);
		setFeedMetadata(feedMetadata);
		
		long endTime= System.currentTimeMillis();
		
		System.out.println(" Init METADATA End time is ###### "+endTime);
		
		long duration = endTime - startTime;
		System.out.println(" FEED METADATA Duration is ####### "+duration);
	}

	/**
	 * Prepare feed meta data for every table <code>prepareFeed</code>method of
	 * the current processor gets feed meta data config object from configuration resources
	 * and prepares feed meta data to create feed
	 * 
	 **/
	@Override
	public Map<String, String> prepareFeed(FeedConfigResources feedConfigResource) {
		//feedErrorHandler.handleRequiredFields(feedConfigResource);
		Map<String, String> feedJsonMap = new HashMap();
		Map<String, List<TableDetails>> sourceTableMetadataMaps = feedConfigResource.getTableMetadataList();
		Map<String, FeedConfiguration> feedConfigMap = feedConfigResource.getFeedConfigMap();

		LOGGER.debug(" FeedConfigResources is {}" , feedConfigResource);
		for (Map.Entry<String, List<TableDetails>> tableMetadataMap : sourceTableMetadataMaps.entrySet()) {
			LOGGER.debug("tableMetadataMap is {}" , tableMetadataMap);
			feedJsonMap = populateFeedMetadata(tableMetadataMap.getKey(), tableMetadataMap.getValue(), feedConfigMap);

		}
		
		// feedErrorHandler.handleError(FEED_ERROR_CODE.PARSER_ERR_100, new String[] {
		// "message" }, null);
		return feedJsonMap;
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */

	// populate feed and table metadata
	private Map<String, String> populateFeedMetadata(String dbName, List<TableDetails> srcTableDetailsList,Map<String, FeedConfiguration> feedConfigMap) {
		FeedMetadata targetFeedMetadata;
		Map<String, String> resultJsonMap = new HashMap();

		// Prepare tableFeedDetails
		List<String> tables=FeedUtils.getSourceTableList("oracle");
		for (String table : tables) {
			FeedConfiguration feedConfig = (FeedConfiguration) feedConfigMap.get(table.toLowerCase());
			if (null != feedConfig) {
				//FeedMetadata feedMetadata=getFeedMetadataObj();
				
				initFeedMetadata();
				String tableWithoutDBName = StringUtils.substringAfter(table, ".");
				
				String feedName = createFeedName(tableWithoutDBName);

				targetFeedMetadata = populateTableMetadata(dbName, tableWithoutDBName, feedConfig, feedName);
				
				targetFeedMetadata = populateFeedMetadata(targetFeedMetadata, feedConfig, feedName);
				
				LOGGER.info("targetFeedMetadata is {}" , targetFeedMetadata);
				
				resultJsonMap.putAll(getFeedMetadataJson(tableWithoutDBName, targetFeedMetadata));
			}

		}
		return resultJsonMap;
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	// populate table matadata
	private FeedMetadata populateTableMetadata(String dbName, String tableName,
			FeedConfiguration feedConfig, String feedName) {

		// Build the feed metadata
		DefaultTableSchema dts = (DefaultTableSchema) feedMetadata.getTable().getSourceTableSchema();
		dts.setDatabaseName(dbName);

		// get Source Table Columns Details
		//List<ColumnDetails> columnDetails = sourceTableDetails.getColumnDetails();
		FeedBuilder feed = new FeedBuilder();
		
		
		
		TableSchema tableSchema=FeedUtils.getDBSchemaDetails("ORACLE", dbName.toUpperCase(), tableName);
			        
		
		
		FeedMetadata targetFeedMetadata = feed.sourceTableBuild(feedMetadata)
											   .tableFieldsBuilder(tableSchema, dbName.concat(".").concat(tableName),feedName.toLowerCase())
											   .method(Methods.EXISTING_TABLE.toString()).existingTableSchemaName(dbName, tableName)
											   .targetMergeStrategy(Constants.TargetMergeStrategy.valueOf(feedConfig.getTargetFeedFormat().getTargetMergeStrategy().toUpperCase()).getValue())
											   .targetFormat(Constants.TargetFormat.valueOf(feedConfig.getTargetFeedFormat().getTargetFormat().toUpperCase()).getValue())
											   .feedFormat(setFeedFormat(feedConfig.getTableBucketing())).description(Constants.BLANKSTRING)
											   .fieldPolicy(tableSchema.getFields()).partitions(new TablePartitionBuilder().populateTablePartitions(feedConfig))
											   .option(new TableOptionBuilder().auditLogging(false).compress(false).encrypt(false).trackHistory(false) .compressFormat("SNAPPY").build())
											   .tableType(TableType.SNAPSHOT.toString()).setSourceTableIncrementalDateField(null).build();

		return targetFeedMetadata;
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	// populate TableMetadata

	private FeedMetadata populateFeedMetadata(FeedMetadata targetFeedMetadata, FeedConfiguration feedConfig,
			String feedName) {

		System.out.println("***** this.feedMetadata getProperties:  *****"+this.feedMetadata.getProperties());
		targetFeedMetadata.getProperties().forEach(nifiProperties->{
			System.out.println("***** getNameKey:  *****"+nifiProperties.getNameKey());
			System.out.println("***** getKey:  *****"+nifiProperties.getKey());
			System.out.println("***** getValue:  *****"+nifiProperties.getValue());
			//if(null!=nifiProperties.getPropertyDescriptor().getAllowableValues())
			List<NiFiAllowableValue> allowedValues=nifiProperties.getPropertyDescriptor().getAllowableValues();
			if(null!=allowedValues) {
				allowedValues.forEach(allowedValue->{
					System.out.println("#########-> allowedValue.getDisplayName().toLowerCase()"+allowedValue.getDisplayName().toLowerCase());
					System.out.println("#######-->"+feedConfig.getDataSource().toLowerCase());
								if(allowedValue.getDisplayName().toLowerCase().contains(feedConfig.getDataSource().toLowerCase())) {
									nifiProperties.setValue(allowedValue.getValue());	
									System.out.println("***** In LOOP getValue:  *****"+nifiProperties.getValue());
									System.out.println("### allowedValue ####"+allowedValue.getDisplayName());
								}
								else if(allowedValue.getDisplayName().toLowerCase().contains(feedConfig.getFeedLoadStrategy().toLowerCase())) {
									nifiProperties.setValue(allowedValue.getValue());	
									System.out.println("***** In LOOP getValue:  *****"+nifiProperties.getValue());
									System.out.println("### allowedValue ####"+allowedValue.getDisplayName());
								}
								
			});
			
		}
		}); 
			
		
	
		targetFeedMetadata.setProperties(feedMetadata.getProperties());
		//targetFeedMetadata.setProperties(feedMetadata.getProperties());
		targetFeedMetadata.setDataOwner("Marketing");
		targetFeedMetadata.setTags(getTagList());
		// DatasourceDefinition dsd=new DatasourceDefinition();
		targetFeedMetadata.setOwner(getOwner());
		targetFeedMetadata.setSchedule(new FeedScheduler().populateFeedSchedule(feedConfig));
		targetFeedMetadata.setFeedName(feedName);
		targetFeedMetadata.setSystemFeedName(feedName);
		targetFeedMetadata.setSecurityGroups(null);

		targetFeedMetadata
				.setCategory(new feed.builder.FeedCategoryBuilder().getFeedCategory(feedConfig));

		targetFeedMetadata.setIsReusableFeed(false);
		targetFeedMetadata.setAllowedActions(feedMetadata.getAllowedActions());
		targetFeedMetadata.setUserProperties(new HashSet<>());
		targetFeedMetadata.setHistoryReindexingStatus("NEVER_RUN");
		targetFeedMetadata.setAllowIndexing(true);
		targetFeedMetadata.setDescription(feedName + ":  feed created by auto feed framework");
		targetFeedMetadata.setState("ENABLED");
		targetFeedMetadata.setActive(true);

		return targetFeedMetadata;

	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	private String createFeedName(String tablenName) {
		LocalDateTime now = LocalDateTime.now();
		String time = now.format(DateTimeFormatter.ofPattern("HHmmssSSS"));
		return (tablenName + Constants.UNDERSCORE + Constants.FEED.toLowerCase() + Constants.UNDERSCORE + time)
				.toLowerCase();
	}

	private String setFeedFormat(TableBucketConfig bucketConfig) {

		if (null!=bucketConfig && bucketConfig.getColumnNames().size() > 0) {
			TableBucketBuilder tb = new TableBucketBuilder();
			StringBuilder sb = new StringBuilder();
			String str = tb.buildBucketExpr(bucketConfig);
			sb.append(str).append(Constants.FeedFormat.FEED_FORMAT.getValue());
			return sb.toString();
		}
		return Constants.FeedFormat.FEED_FORMAT.getValue();
	}

	private List<Tag> getTagList() {

		List<Tag> tags = new ArrayList<>();
		tags.add(new DefaultTag("users"));
		tags.add(new DefaultTag("registrations"));
		return tags;
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	private Map<String, String> getFeedMetadataJson(String tableName, FeedMetadata feedMetadata) {
		Map<String, String> feedJsonmap = new HashMap<>();
		try {
			feedJsonmap.put(tableName, FeedUtils.getObjectMapper().writeValueAsString(feedMetadata));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feedJsonmap;
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	private Set<String> getGroup() {

		Set<String> groups = new HashSet<>();
		groups.add("admin");
		groups.add("user");
		return groups;
	}

	private User getOwner() {
		User owner = new User();
		owner.setSystemName("dladmin");
		owner.setDisplayName("Data Lake Admin");

		owner.setGroups(getGroup());
		return owner;
	}

	@Override
	public void process(String inputTemplatePath) {
		// TODO Auto-generated method stub

	}

	/**
	 * SingletonFeed is loaded at the first execution of Singleton.getInstance() or
	 * the first access to SingletonFeed INSTANCE
	 * 
	 */
	private static class SingletonDBFeedProcessor {
		private static final DBFeedProcessor INSTANCE = new DBFeedProcessor();
	}

	/**
	 * Initiates the chain of handlers by calling the <code>process</code>method of
	 * the current handler then calls the <code>handleMessage</code> of the next
	 * handler
	 * 
	 * 
	 * 
	 */
	public static DBFeedProcessor getInstance(String sampleTemplatePath) {
		feedTempaltePath = sampleTemplatePath;
		return SingletonDBFeedProcessor.INSTANCE;
	}

	
}
