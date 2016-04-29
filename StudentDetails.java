package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class StudentDetails extends HttpServlet
{
	Connection con;
	public void init(ServletConfig config)
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ANUBHAV","anubhav12");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();

		String name=req.getParameter("txtname");
		try
		{
			PreparedStatement ps=con.prepareStatement("Select * from Details where name=?");
			ps.setString(1,name);

			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				out.println("Record Found");
				out.println("<br>Name: " +rs.getString(1));
				out.println("<br>Book: "+rs.getString(2));
				out.println("<br>Book: "+rs.getString(3));
			}
			else
			{
				out.println("Record Not Found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		out.println("<br><a href='index.html'>Home</a>");
	}
}