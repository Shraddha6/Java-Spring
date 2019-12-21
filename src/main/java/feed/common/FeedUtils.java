package feed.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terdata.autoconfig.entity.FeedConfiguration;

import feed.config.common.Constants;
import feed.dbcp.DBCPDataSourceFactory;
import feed.encryption.CryptoUtils;


/**
 *<code>FeedUtils</code> is a utility class which provides implementation of several utilities methods.
 *
 * */

public class FeedUtils {

	public static ObjectMapper getObjectMapper() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	public static FeedMetadata updateFeedName(FeedMetadata feedMetadata, String tableName) {
		feedMetadata.setProperties(null);
		feedMetadata.setFeedName(tableName.concat(Constants.DOT).concat(Constants.FEED.toLowerCase()));
		feedMetadata.setSystemFeedName(tableName.concat(Constants.UNDERSCORE).concat(Constants.FEED.toLowerCase()));
		return feedMetadata;
	}

	public static void encryptPassword() {
		try {
			CryptoUtils.encrypt(CryptoUtils.generateKey(), "thinkbig", new File(".encrypted"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static FeedMetadata getFeedMetaData(String templatePath) {
		try {
			
			FeedMetadata obj = FeedUtils.getObjectMapper().readValue(new File(templatePath), FeedMetadata.class);

			return obj;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Properties loadProps(String fileName) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileName);
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;

	}

	public static Map<String, FeedConfiguration> readFeedConfiguration(String fileName) {
		HashMap<String, FeedConfiguration> feedConfigMap = null;
		try {
			// convert the json back to object

			JsonFactory jsonFactory = new JsonFactory();
			@SuppressWarnings("deprecation")
			JsonParser jp = jsonFactory.createJsonParser(new File(fileName));
			jp.setCodec(new ObjectMapper());
			JsonNode jsonNode = jp.readValueAsTree();
			// JsonRootName jsonRoot=
			feedConfigMap = (HashMap<String, FeedConfiguration>) parseFeedConfigJsonData(jsonNode);

		} catch (IOException e) {
	
			e.printStackTrace();
		}

		return feedConfigMap;
	}
	
	private static Map<String, FeedConfiguration> parseFeedConfigJsonData(JsonNode jsonNode) {
		Iterator<Map.Entry<String, JsonNode>> ite = jsonNode.fields();
		HashMap<String, FeedConfiguration> feedConfigMap = new HashMap<>();
		FeedConfiguration feedConfiguration;
		try {
			while (ite.hasNext()) {
				Map.Entry<String, JsonNode> entry = ite.next();
				if (entry.getValue().isObject()) {
					feedConfiguration = FeedUtils.getObjectMapper().treeToValue(entry.getValue(),
							FeedConfiguration.class);
					feedConfigMap.put(entry.getKey(), feedConfiguration);

				} else {
					System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
				}
			}
			return feedConfigMap;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feedConfigMap;
	}

	public static TableSchema getDBSchemaDetails(String dbProvider, String dbName, String tableName) {
		TableSchema tableSchema = null;
		try {

			// dataSource
			DBSchemaParser schemaParser = getSchemaParser(dbProvider);
			tableSchema = schemaParser.describeTable(dbName, tableName);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return tableSchema;
	}

	public static List<String> getSourceTableList(String dbProvider) {
		List<String> tables = null;
		DBSchemaParser schemaParser = null;
		try {
			schemaParser = getSchemaParser(dbProvider);
			tables = schemaParser.listTables("ORACLE");
			return tables;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return tables;
	}

	private static DBSchemaParser getSchemaParser(String dbProvider) {
		DBSchemaParser schemaParser = null;
		try {
			// dataSource
			schemaParser = new DBSchemaParser(getDataSources(dbProvider), new KerberosTicketConfiguration());

			return schemaParser;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return schemaParser;
	}

	private static DataSource getDataSources(String dbProvider) {
		DataSource dataSource = null;
		try {
			dataSource = DBCPDataSourceFactory.getDataSource(dbProvider);
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
}
