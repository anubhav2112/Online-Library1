package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ChangePassword extends HttpServlet
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
		String pass=req.getParameter("txtpassword");
		String newpass=req.getParameter("txtnewpassword");
		String retypepass=req.getParameter("txtretypepassword");
		String str="";
		out.println(newpass);
		try
		{
			String query[]={"Select * from Login where Username=?","update Login set where password=?"};
			//PreparedStatement ps=con.prepareStatement("Select * from Login where Username=?");
			PreparedStatement ps=null;
			ResultSet rs=null;
			ps=con.prepareStatement(query[0]);
			ps.setString(1,name);

			rs=ps.executeQuery();
			if(rs.next())
			{
				out.println("Record Found");
				str=rs.getString(2);
			}
			else
			{
				out.println("Record Not found");
			}
			out.println(str);
			if(pass.equals(str))
			{
				out.println("Hello");
				if(newpass.equals(retypepass))
				{
					ps=con.prepareStatement(query[1]);
					ps.setString(2,newpass);

					int x=ps.executeUpdate();
					if(x==1)
					{
						out.println("Password changed");
					}
					else
					{
						out.println("See Server Console");
					}
		
					out.println("hiii");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		out.println("<br><a href='index.html'>Home</a>");
	}
}