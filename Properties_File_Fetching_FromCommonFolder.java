package a.b.c.PropsTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Properties_File_Fetching_FromCommonFolder {

	public static void main(String[] args) {
		InputStream is=null;
		Properties props=null;
		try {
			//Locate Properties file Using STreams
			is=new FileInputStream("src/a/b/c/commons/MyBio.properties");
			//create Properties class object
			props=new Properties();
			props.load(is);
			System.out.println("Properties file info::"+props);
			System.out.println("...........................");
			System.out.println("Name is::\t\t"+props.getProperty("Name"));
			System.out.println("Age is ::\t\t"+props.getProperty("Age"));
			System.out.println("Address is ::\t"+props.getProperty("Addrs"));
			System.out.println("Pin Code is ::\t"+props.getProperty("PinCode"));
			System.out.println("Country is ::\t"+props.getProperty("Country"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}//main
}//class
