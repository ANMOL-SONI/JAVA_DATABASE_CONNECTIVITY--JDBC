package x.y.z;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class Conn_Pool_Test {
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		OracleConnectionPoolDataSource ds=null;
		ResultSet rs=null;
		try {
			//Create DataSource object Represention Empty JDBC Conn POOL
			ds= new OracleConnectionPoolDataSource();
			if(ds!=null){
			//Set JDBC Properties to DataSource Object to Create Conn Object in the conn pool
			ds.setDriverType("thin");
			ds.setURL("jdbc:oracle:thin:@localhost:1521:ORCL");
			ds.setUser("scott");
			ds.setPassword("tiger");
			//get Conn obj from JDBC Conn pool through DataSource Obj
			con=ds.getConnection();
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
