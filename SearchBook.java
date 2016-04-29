package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SearchBook extends HttpServlet
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

		String value=req.getParameter("opt");
		try
		{
			PreparedStatement ps=null;
			ResultSet rs=null;

			if(value.equals("byId"))
			{
				ps=con.prepareStatement("Select * from Library where code=?");
				ps.setString(1,req.getParameter("txtname"));
				rs=ps.executeQuery();
			}

			if(value.equals("byName"))
			{
				ps=con.prepareStatement("Select * from Library where name=?");
				ps.setString(1,req.getParameter("txtname"));
				rs=ps.executeQuery();
			}

			if(rs.next())
			{
				out.println("<br>record found");
				out.println("<br>Code of Book is: "+rs.getString(1));
				out.println("<br>Name of Book is: "+rs.getString(2));
				out.println("<br>Author of book is: "+rs.getString(3));
				out.println("<br>Publisher of Book is: "+rs.getString(4));
				out.println("<br>Edition of Book is: "+rs.getString(5));
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