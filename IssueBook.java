package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class IssueBook extends HttpServlet
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
		String str=req.getParameter("txtbook");
		String date=req.getParameter("txtdate");
		String month=req.getParameter("txtmonth");
		String st="";
		String value=req.getParameter("opt");

		try
		{
			//String query[]={"Select * from Library where Name=?","insert into Details values(?,?,?,?)"};
			PreparedStatement ps=null;
			ResultSet rs=null;
			//ps=con.prepareStatement(query[0]);
			if(value.equals("Search"))
			{
				ps=con.prepareStatement("Select * from Library where Name=?");
				ps.setString(1,str);
				rs=ps.executeQuery();
			
				if(rs.next())
				{
					st=rs.getString(2);
				}
				out.println(st);
				//out.println(str);
				if(st.equals(str))
				{
					out.println("Book issued");		
				}
				else
				{
					out.println("Book does not found");
				}
			}
			if(value.equals("Issue"))
			{
				ps=con.prepareStatement("insert into Details values(?,?,?,?)");
				ps.setString(1,name);
				ps.setString(2,str);
				ps.setString(3,date);
				ps.setString(4,month);

				int x=ps.executeUpdate();
				if(x==1)
				{
					out.println("Book issued");
				}
				else
				{
					out.println("see server");
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
