package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginPage extends HttpServlet
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
		String password=req.getParameter("txtpassword");
		String str="";
		String cap=req.getParameter("txtcapture");
		String st=req.getParameter("txtfirst");
		try
		{
			PreparedStatement ps=con.prepareStatement("Select * from Login where Username=?");
			ps.setString(1,name);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				str=rs.getString(2);
			}

			if((password.equals(str)))
			{
				out.println("<html><body background='abc2.jpg'></body></html>");
				out.println("<center><font size='6' style='color:red;background-color:pink;font-family:Comic Sans MS;'>Welcome</font><center>");
				out.println("<br><strong><a href='Unique.html'>Add Book</a></strong>");
				out.println("<strong><a href='Search.html'>Search Book</a></strong>");
				out.println("<b><a href='Details.html'>Student Details</a></b>");
				out.println("<b><a href='Change.html'>Change Password</a></b>");
				out.println("<hr>");
				RequestDispatcher rd=req.getRequestDispatcher("issue.html");
				rd.include(req,res);
				out.println("<hr>");
				RequestDispatcher rd1=req.getRequestDispatcher("Return.html");
				rd1.include(req,res);
			}
			else
			{
				out.println("invalid password");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		out.println("<br><a href='index.html'>Home</a>");
	}
}