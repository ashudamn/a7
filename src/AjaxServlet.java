
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
 * Servlet implementation class AjaxServlet
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/json");
		String driver=request.getParameter("driver");
		String url=request.getParameter("url");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String query=request.getParameter("query");
		PrintWriter out=response.getWriter();
		ResultSet rs=null;
		try{
			AccessDB.connect2DB(driver, url, username, password);
			AccessDB.setQuery(query);
			AccessDB.execute(query);
			System.out.println("connected to database");
			rs=AccessDB.getRs();
		}
		catch(Exception e)
		{
			System.out.println("problem in connection to database");
			out.print("There is some error bhai");
			e.printStackTrace();
		}
		try {
			JSONArray obj=ResultToJson.convert(rs);
			AccessDB.setResultSize(obj.length());
			out.println(obj);
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
		
	}

}
