package feed.dbcp;

import java.net.URISyntaxException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;


public class DBSchemaDiscoveryTest {
	
	public static void main(String[] args) throws URISyntaxException {
		  DataSource dataSource= DBCPDataSourceFactory.getDataSource("oracle");
			
		 // dataSource
		  DBSchemaParser schemaParser = new DBSchemaParser(dataSource, new KerberosTicketConfiguration());
	        List<String> tables = schemaParser.listTables(null);
	        TableSchema tableSchrma= schemaParser.describeTable("toys", "customers");
	       
	        for (final String table : tables) {
	            System.out.println(table);
	     
	            String tableWithoutDBName = StringUtils.substringAfter(table, ".");
	            
	        }
	}

}
