

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Servlet implementation class SortedOutput
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/SortedOutput" })
public class SortedOutput extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int I=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortedOutput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		response.setContentType("application/json");
		//ArrayList<ArrayList<String>> records = null;
		String param=request.getParameter("param");
		String order=request.getParameter("order");
		String num=request.getParameter("num");
		int i=Integer.parseInt(num);
		ResultSet rs=null;
		String query;
		if(param.isEmpty()){
			if(num.isEmpty()){
				query=AccessDB.getQuery();
			}
			else{
				query=AccessDB.getQuery()+" LIMIT "+i+" OFFSET "+I;
			}
		}
		else
		{
			query=AccessDB.getQuery()+" ORDER BY "+param+" "+order+" LIMIT "+i+" OFFSET "+I;
		}
		
		
		//
		System.out.println(query);
		System.out.println("Offset is "+I+" vs result size "+AccessDB.getResultSize());
		try {
			AccessDB.execute(query);
			rs=AccessDB.getRs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONArray obj=ResultToJson.convert(rs);
			if(I>AccessDB.getResultSize()){
				I=0;
			System.out.print("you reached end");	
			}
			else
			{
				I=I+i;
				out.println(obj);
			}
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("Some mistake in response ");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
