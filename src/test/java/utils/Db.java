package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * db
 * @author zhaowen
 *@Time 2018-11-12
 */

public class Db {
	    private static final String Durl =System.getProperty("Durl") ;
	    private static final String name = System.getProperty("name");
	    private static final String Duser = System.getProperty("Duser");
	    private static final String Dpassword = System.getProperty("Dpassword");


	    private ResultSet ret = null;
	    private Connection conn = null;
	    private PreparedStatement pst = null;
	    private static ResultSetMetaData m=null;//Get column information

	    public void setsql(String sql) {
	        try {
	            Class.forName(name);//Get column information...
	            conn = DriverManager.getConnection(Durl, Duser, Dpassword);//Get connected
	            pst = conn.prepareStatement(sql);//Prepare to execute the statement
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void close() {
	        try {
	            this.conn.close();
	            this.pst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public  String[] getsql(String sql) {
	        setsql(sql);//Create a DBHelper object
	        String[] str = new String[50];
	        try {
	            ret = pst.executeQuery();//Execute the statement to get the result set
	            m=ret.getMetaData();
	            int columns=m.getColumnCount();          //Get the number of columns
	            while (ret.next()) {
	                for(int i=1;i<=columns;i++){
	                    str[i-1]=ret.getString(i);
	                    //	System.out.print(str[i-1]+"\t");
	                }
	            }//Display Data
	            ret.close();
	            close();//Close the connection
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return str;
	    }

	    public static void main(String[] args) {
	        Db db1=new Db();
	        String sql = "" ;  

	        String[] str = new String[50];
	        str=db1.getsql(sql);
	        for(String s:str){
	            if(s==null)
	                break;
	            System.out.print(s+"\t");
	        }
	    }
}
