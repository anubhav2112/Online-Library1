package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CreateAccount extends HttpServlet
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
		String retype=req.getParameter("txtretype");
		String number=req.getParameter("txtmbl");
		String gender=req.getParameter("gender");
		String str="";

		if(gender.equals("Male"))
		{
			str="male";
		}
		else
		{
			str="female";
		}
		String email=req.getParameter("txtemail");
		String state=req.getParameter("states");

		if(pass.equals(retype))
		{
			try
			{
				PreparedStatement ps=con.prepareStatement("insert into Login values(?,?,?,?,?,?,?)");
				ps.setString(1,name);
				ps.setString(2,pass);
				ps.setString(3,retype);
				ps.setString(4,number);
				ps.setString(5,str);
				ps.setString(6,email);
				ps.setString(7,state);

				int x=ps.executeUpdate();
				if(x==1)
				{
					out.println("Account Created");
				}
				else
				{
					out.println("Account not created");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			out.println("password does not match");
		}
		out.println("<br><a href='index.html'>Home</a>");
	}
}