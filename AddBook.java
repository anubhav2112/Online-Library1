package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddBook extends HttpServlet
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

		String id=req.getParameter("txtunique");
		String name=req.getParameter("txtname");
		String author=req.getParameter("txtauthor");
		String publisher=req.getParameter("txtpublisher");
		String edition=req.getParameter("txtedition");

		try
		{
			PreparedStatement ps=con.prepareStatement("insert into Library values(?,?,?,?,?)");
			ps.setString(1,id);
			ps.setString(2,name);
			ps.setString(3,author);
			ps.setString(4,publisher);
			ps.setString(5,edition);

			int x=ps.executeUpdate();

			if(x==1)
			{
				out.println("Record Inserted");
			}
			else
			{
				out.println("check in server");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		out.println("<br><a href='index.html'>Home</a>");
	}
}
		