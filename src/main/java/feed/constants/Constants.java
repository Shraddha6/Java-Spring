package feed.constants;

import java.util.ArrayList;
import java.util.List;

import feed.builder.FeedCategoryBuilder;
import feed.config.common.FeedConfigConstants.FeedCategory;

public interface Constants {
	
	public  String COMMA=",";
	public  String DOT=".";
	public String DASH="-";
	public String UNDERSCORE="_";
	public String BLANKSTRING="";
	public String FEED="FEED";
	public String FEED_EXECUTOR_CHAIN="FEED_EXECUTOR_CHAIN";
	
	public enum FeedSourceType {
		DB,FILESYSTEM,S3,HDFS,REST_SERVICE }
	
	
	public enum Methods {
		EXISTING_TABLE }
	
	public enum FeedMetadataConfigResource{
		
		FEEDMETADATA_CONFIG_RESOURCE,REQUEST_URL,FEED_METADATA_JSON,FEED_CONFIG_MAP
	}
	
	public enum FeedCategory{
	WEBSITE("Website","0970c047-8cd1-46bf-b0a0-434d3b365c69","website"),
	CONCERTS("Concerts","d5ff80ca-3873-472a-a0cc-e472d4438689","concerts"),
	TOY_STORE("Toy Store","6997a4c1-e7df-42d8-9995-78bccc30fc9f","toy_store"),
	SYSTEM("System","3bb6cd6e-88dd-42b9-8ecd-13e6667d40e0","system");
	
	private String categoryName;
	private String categoryId;
	private String systemName;
	
	  // Constructor 
	 FeedCategory(String categoryName, String categoryId, String systemName) {
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.systemName = systemName;
	}

 
	 public String getCategoryName() {
		    return categoryName;
		  }
	 public String getCategoryId() {
		    return categoryId;
		  }
	 public String getSystemName() {
		    return systemName;
		  }
	
		public static FeedCategoryBuilder getFeedCategory(String feedCategory) {
			FeedCategoryBuilder feedCat=null;
			for (FeedCategory tf : FeedCategory.values()) {
			if(feedCategory.equals(tf.name())){
				feedCat=new FeedCategoryBuilder(tf.getCategoryName(), tf.getCategoryId(), tf.getSystemName());
			}
			}
			return feedCat;
		}
	}

	
	public enum TableType {
		SNAPSHOT 
		}
	
	public enum NifiProperties{
		INPUT_DIRECTORY("Input Directory"),
		FILE_FILTER("File Filter"),
		LOAD_STRATEGY("Load Strategy");
		
		private String value; 

		  // Constructor 
		NifiProperties(String values) {
		    this.value = values;
		  }

		  public String getValue() {
		    return value;
		  }
	}
	public enum TargetMergeStrategy{
		DEDUPE_AND_MERGE("DEDUPE_AND_MERGE"),
		MERGE("MERGE"),
		SYNC("Sync"),
		MERGE_USING_PRIMARY("Merge using primary key");
		
		private String value; 

		  // Constructor 
		TargetMergeStrategy(String values) {
		    this.value = values;
		  }

		  public String getValue() {
		    return value;
		  }
	}
	public enum LoadStrategy{
		FULL_LOAD("FULL_LOAD"),
		INCREMENTAL("INCREMENTAL");
		
		private String value; 

		  // Constructor 
		LoadStrategy(String values) {
		    this.value = values;
		  }

		  public String getValue() {
		    return value;
		  }
	}
	
	public enum FeedFormat{
		FEED_FORMAT("ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde' WITH SERDEPROPERTIES ( 'separatorChar' = ',' ,'escapeChar' = '\\\\\\\\' ,'quoteChar' = '\\\"') STORED AS TEXTFILE");
		
		private String value; 

		  // Constructor 
		FeedFormat(String values) {
		    this.value = values;
		  }

		  public String getValue() {
		    return value;
		  }
		  
		  
	}
	public enum TargetFormat{
		ORC("STORED AS ORC"),
		PARQUET("STORED AS PARQUET"),
		TEXTFILE("ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde' WITH SERDEPROPERTIES ( 'separatorChar' = ',' ,'escapeChar' = '\\\\' ,'quoteChar' = '\"') STORED AS TEXTFILE"),
		RCFILE("ROW FORMAT SERDE \"org.apache.hadoop.hive.serde2.columnar.ColumnarSerDe\" STORED AS RCFILE"),
		AVRO("STORED AS AVRO");
		
		private String value; 

		  // Constructor 
		TargetFormat(String values) {
		    this.value = values;
		  }

		  public String getValue() {
		    return value;
		  }
	}
	public enum FEED_ERROR_CODE {
        /**
         * FEED Internal Error
         */
        FEED_ERR_000("feed_err_000"),
        /**
         * Required field is missing
         */
        FEED_ERR_100("feed_err_100"),
        /**
         * Required field is missing
         */
        FEED_ERR_404("feed_err_404"),
        /**
         * Parsing Error
         */
        FEED_ERR_101("feed_err_101"),
        /**
         * FEED Not found
         */
        FEED_ERR_102("feed_err_102"),
        /**
         * FEED Handler Not found
         */
        FEED_ERR_103("feed_handler_err_103"),
        /**
         * FEED Configuration Error
         */
        FEED_CONFIGURATION_ERR_104("feed_configuration_err_104");

        private final String key;

        private FEED_ERROR_CODE(String key) {
            this.key = key;
        }

        public String getValue() {
            return key;
        }
    }
}
