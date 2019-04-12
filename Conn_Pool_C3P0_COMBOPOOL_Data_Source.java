package x.y.z;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Conn_Pool_C3P0_COMBOPOOL_Data_Source {
	public static void main(String[] args) throws PropertyVetoException,IOException,SQLException {
		Connection con=null;
		Statement st=null;
		ComboPooledDataSource cs=null;
		ResultSet rs=null;
		try {
			//Create DataSource object Represention Empty JDBC Conn POOL
			cs= new ComboPooledDataSource();
			if(cs!=null){
			//Set JDBC Properties to DataSource Object to Create Conn Object in the conn pool
			cs.setDriverClass("oracle.jdbc.driver.OracleDriver");
			cs.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
			cs.setUser("scott");
			cs.setPassword("tiger");
			//get Conn obj from JDBC Conn pool through DataSource Obj
			con=cs.getConnection();
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
				try{
					if(cs!=null)
						cs.close();
				}
				catch(Exception e){													  
								e.printStackTrace();
			}
				
		}//finally

	}//mm

}//class
