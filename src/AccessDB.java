

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class AccessDB {
	private static ResultSet rs;
	private static Statement st;
	private static Connection conn;
	private static String query;
	private static int resultSize;
	public static void connect2DB(String driver,String url,String username,String password) throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		conn=(Connection) DriverManager.getConnection(url,username,password);
		st=(Statement) conn.createStatement();
		
	}
	public static void execute(String query) throws SQLException{
		ResultSet rs=st.executeQuery(query);
		setRs(rs);
	}
	public static ResultSet getRs() {
		return rs;
	}
	public static void setRs(ResultSet rs) {
		AccessDB.rs = rs;
	}
	public static ArrayList<ArrayList<String>> resultSet2arrayList()
	{
		ArrayList<ArrayList<String>> record= new ArrayList<ArrayList<String>>();
		try {
			ResultSetMetaData rsmd=rs.getMetaData();
			int columns=rsmd.getColumnCount();
			ArrayList<String> temp=new ArrayList<String>();
			for(int i=1;i<=columns;i++)
			{
				temp.add(rsmd.getColumnName(i));
				System.out.print(rsmd.getColumnName(i)+"   ");
			}
			record.add(temp);
			System.out.println();
			int j=1;
			while(rs.next())
			{
				temp=new ArrayList<String>();
				for(int i=1;i<=columns;i++)
				{
					String typeOfColumn = rsmd.getColumnTypeName(i);
					if (typeOfColumn.contains("BLOB") || typeOfColumn.contains("CLOB")){
						temp.add("Image "+j);
						System.out.print("Image "+j);
					}
					else
					{
						System.out.print(rs.getString(i)+"  ");
						temp.add(rs.getString(i));
					}
				}
				System.out.println();
				j++;
				record.add(temp);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	public static void closeAll(){
		try {
			AccessDB.st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("problem in closing statement");
			e.printStackTrace();
		}
		try {
			AccessDB.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("problem in closing connection");
			e.printStackTrace();
		}
		
	}
	public static String getQuery() {
		return query;
	}
	public static void setQuery(String query) {
		AccessDB.query = query;
	}
	public static int getResultSize() {
		return resultSize;
	}
	public static void setResultSize(int resultSize) {
		AccessDB.resultSize = resultSize;
	}
}
