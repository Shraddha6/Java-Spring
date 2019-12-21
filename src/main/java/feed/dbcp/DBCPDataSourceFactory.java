package feed.dbcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;


import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;

public class DBCPDataSourceFactory {

	public static DataSource getDataSource(String dbType) throws URISyntaxException{
		Properties props = new Properties();
		FileInputStream fis = null;
		BasicDataSource ds = new BasicDataSource();
		
		try {
			 File file = new File(Thread.currentThread().getContextClassLoader()
		                .getResource("config.properties").toURI());
			fis = new FileInputStream(file);
			props.load(fis);
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		if("mysql".equalsIgnoreCase(dbType)){
			ds.setDriverClassName(props.getProperty("MYSQL_DB_DRIVER_CLASS"));
            ds.setUrl(props.getProperty("MYSQL_DB_URL"));
            ds.setUsername(props.getProperty("MYSQL_DB_USERNAME"));
            ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		}else if("oracle".equalsIgnoreCase(dbType)){
			ds.setDriverClassName(props.getProperty("ORACLE_DB_DRIVER_CLASS"));
            ds.setUrl(props.getProperty("ORACLE_DB_URL"));
            ds.setUsername(props.getProperty("ORACLE_DB_USERNAME"));
            ds.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
		}else if("teradata".equalsIgnoreCase(dbType)){
			ds.setDriverClassName(props.getProperty("TERADATA_DB_DRIVER_CLASS"));
            ds.setUrl(props.getProperty("TERADATA_DB_CONNECTION_URL"));
            ds.setUsername(props.getProperty("TERADATA_DB_USER_NAME"));
            ds.setPassword(props.getProperty("TERADATA_DB_PASSWORD"));	
            	
		}
		
		else{
			return null;
		}
		
		return ds;
	}
}

