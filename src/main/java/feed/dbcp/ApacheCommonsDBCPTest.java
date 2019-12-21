package feed.dbcp;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class ApacheCommonsDBCPTest {
	public static void main(String[] args) {
		try {
			testDBCPDataSource("oracle");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void testDBCPDataSource(String dbType) throws URISyntaxException {
		
		DataSource ds = DBCPDataSourceFactory.getDataSource(dbType);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from test");
			while(rs.next()){
				System.out.println("Employee ID="+rs.getInt("QLID")+", Name="+rs.getString("CUSTOMER_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(con != null) con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
