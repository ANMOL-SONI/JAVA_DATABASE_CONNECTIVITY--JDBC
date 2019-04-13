/*
 SQL> create table product(pid number(10)primary key,pname varchar2(20),price number(10,2),qty number(5));
 SQL> insert into product values('&pid','&pname','&price','&qty');
 SQL> select * from product;

       PID PNAME                     PRICE        QTY
---------- -------------------- ---------- ----------
      1001 MOBILE                      100          5
      1002 WATCH                       200          4
      1003 GUN    	                     300          3
      1004 LAPTOP                      400          2
      1005 BULLET                      500          1

SQL> select * from account;

      ACNO HOLDER                  BALANCE
---------- -------------------- ----------
 112233001 ANMOL SONI                15000
 112233002 HIMANSHU S                15000
 112233003 ARVINDRA                  	30000
 */

package x.y.z;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

public class SAVE_POINT_TEST {
public  static final String CHOOSE_PRODUCT_QUERY="SELECT PRICE FROM PRODUCT WHERE PID=?";
public  static final String BOOK_PRODUCT_QUERY="UPDATE PRODUCT SET QTY=QTY-1 WHERE PID=?";
public  static final String PAYMENT_PRODUCT_QUERY="UPDATE ACCOUNT SET BALANCE=BALANCE-? WHERE ACNO=?";
	
	public static void main(String[] args) {
		Scanner sc=null;
		int pno=0,acno=0;
		float price=0;
		Connection con=null;
		PreparedStatement ps=null,ps1=null,ps2=null;
		int status=0;
		ResultSet rs=null;
		int rs1=0,rs2=0;
		boolean flag=false;
		Savepoint sp=null;

		//read input
try{
		sc=new Scanner(System.in);
					if(sc!=null){
							System.out.println("Enter the Product N0  :");
							pno=sc.nextInt();
							System.out.println("Enter the Bank Acc N0  :");
							acno=sc.nextInt();
							}//if
					//Convert input values as required for SQL Query

		//Reg Jdbc Driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//Establish Conn Between java and oracle
		con=DriverManager.getConnection("jdbc:Oracle:thin:@localhost:1521:ORCL","scott","tiger");
		//Create P Statement Obj	
		if(con!=null)
		ps=con.prepareStatement(CHOOSE_PRODUCT_QUERY);
							
		// Set each values Details to Query Pattern
		if(ps!=null)
					ps.setInt(1, pno);
					
		//Execute the SQL query
		if(ps!=null) {
					rs=ps.executeQuery();
		}
		if(ps!=null) {
			if(rs.next())
					price=rs.getFloat(1);
		
		else {
			System.out.println("		OUT OF STUCK------PRODUCT NOT AVAILABLE NOW");
			return;
			}
		}//if
		//Begin TXN
		if(con!=null)
			con.setAutoCommit(false);
		//Execute query in TXN
		if(con!=null) {
			ps1=con.prepareStatement(BOOK_PRODUCT_QUERY);
			ps2=con.prepareStatement(PAYMENT_PRODUCT_QUERY);
		}
		//Set values to Query Pattern
		if(ps1!=null && ps2!=null) {
			ps1.setInt(1, pno);
			ps2.setFloat(1, price);
			ps2.setInt(2, acno);
		}
		//Execute the Query
		if(ps1!=null && ps2!=null) {
			rs1=ps1.executeUpdate();
			sp=con.setSavepoint("p1");
			rs2=ps2.executeUpdate();
		}
		//Manage payment
		if(rs1!=0 && rs2!=0) {
			status=1;
		}
		else if(rs1!=0 && rs2==0) {
			status=2;
		}
		else if(rs1==0 && rs2!=0) {
			status=3;
		}
		else if(rs1==0 && rs2==0) {
			status=3;
		}
	}//try
catch(SQLException se){     
	se.printStackTrace();
	System.out.println("OOPS!!! SQL Record insertion failed ");             //Known Exception
}
catch(Exception e){ 
	e.printStackTrace();
	System.out.println("OOPS!!! Record insertion failed  SOMETHING WRONG IN YOUR INPUT, Cleck CAREFULLY");             //Known Exception
}


//close ALL jdbc objects
finally{
	try {
		if(status==1) {
			con.commit();
			System.out.println("	PRODUCT HAS BEEN SELECTED----------And TXN Done Successfully!!!---- PLEASE RATE US");
		}
		else if(status==2) {
			con.rollback();
			con.commit();
			System.out.println("	PRODUCT HAS BEEN SELECTED----------But TXN FAILED---- PLEASE CHOOSE ''CASH ON DELIVERY'' OPTION");
		}
		else if(status==3 || status==1) {
			con.rollback();
			System.out.println("		OOPS!!! Also  WRONG ACC no...");
		}
	}//try
	catch(SQLException se){     
		se.printStackTrace();
		System.out.println("OOPS!!! SQL Record insertion failed ");             //Known Exception
		}
	
		try{
				if(ps!=null)
				ps.close();
		}
		catch(SQLException se){													  
						se.printStackTrace();
		}
		try{
			if(ps1!=null)
			ps1.close();
		}
		catch(SQLException se){													  
						se.printStackTrace();
		}
		try{
			if(ps2!=null)
			ps2.close();
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
				if(sc!=null)
					sc.close();
		}
		catch(Exception e){													 
						e.printStackTrace();
		}
		}//finally
	}//main
}//class
