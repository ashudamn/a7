import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;

public class ResultToJson {
	public ResultToJson()
	{
		
	}
	public static JSONArray convert(ResultSet rs) throws SQLException, JSONException{
		//make keys and put values one by one and make array of json objects
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd=(ResultSetMetaData) rs.getMetaData();
		int j=1;
		while(rs.next()){
			int numColumns=rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			for(int i=1;i<numColumns+1;i++){
				String column_name=rsmd.getColumnName(i);
				if(column_name.compareTo("Picture")==0){
					//System.out.println(rsmd.getColumnTypeName(i));
					obj.put(column_name, "Image"+j);
					j++;
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
					obj.put(column_name,rs.getArray(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
					obj.put(column_name,rs.getInt(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
					obj.put(column_name,rs.getBlob(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
					obj.put(column_name,rs.getBoolean(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.CLOB){
					obj.put(column_name,rs.getClob(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
					obj.put(column_name,rs.getDate(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DECIMAL){
					obj.put(column_name,rs.getBigDecimal(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
					obj.put(column_name,rs.getDouble(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
					obj.put(column_name,rs.getFloat(i));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
			         obj.put(column_name, rs.getNString(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
			         obj.put(column_name, rs.getString(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
			         obj.put(column_name, rs.getDate(column_name));
			        }
			    else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
			        obj.put(column_name, rs.getTimestamp(column_name));   
			        }
			    else{
			         obj.put(column_name, rs.getObject(column_name));
			        }

			}
			json.put(obj);
		}
		
		return json;
	}
}
