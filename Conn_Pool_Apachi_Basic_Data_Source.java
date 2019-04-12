package x.y.z;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conn_Pool_Apachi_Basic_Data_Source {
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		BasicDataSource bs=null;
		ResultSet rs=null;
		try {
			//Create DataSource object Represention Empty JDBC Conn POOL
			bs= new BasicDataSource();
			if(bs!=null){
			//Set JDBC Properties to DataSource Object to Create Conn Object in the conn pool
			bs.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			bs.setUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
			bs.setUsername("scott");
			bs.setPassword("tiger");
			//get Conn obj from JDBC Conn pool through DataSource Obj
			con=bs.getConnection();
		}
		//Create Statement Object
		if(con!=null)
			st=con.createStatement();
		//send and Execute SQL Query
		if(st!=null){
		rs=st.executeQuery("SELECT SNO,SNAME,SADD FROM STUDENT");
		}
		//PROCESS THE RS		
		if(rs!=null) {
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"    "+rs.getString(3));
			}//while
		}//if
	}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	//	catch(ClassNotFoundException cnf) {
	//		cnf.printStackTrace();	}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally{
			//close ALL jdbc objects
			try{
						if(rs!=null)
						rs.close();
				}
				catch(SQLException se){													  
								se.printStackTrace();
				}
				try{
						if(st!=null)
						st.close();
				}
				catch(SQLException se){													  
								se.printStackTrace();
				}
			
				try{
						if(con!=null)
							con.close();
				}
				catch(SQLException se){													  
								se.printStackTrace();
				}
				
		}//finally

	}//mm

}//class
