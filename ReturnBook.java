package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ReturnBook extends HttpServlet
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
		String book=req.getParameter("txtbook");
		String returndate=req.getParameter("txtreturndate");
		String returnmonth=req.getParameter("txtreturnmonth");
		String value=req.getParameter("opt");
		String str="";
		String str1="";
		String str2="";
		try
		{
			PreparedStatement ps=null;
			ResultSet rs=null;
			String query[]={"Select * from Details where name=?","delete from Details where name=?"};

			if(value.equals("Search"))
			{
				ps=con.prepareStatement(query[0]);
				ps.setString(1,name);
				rs=ps.executeQuery();

				if(rs.next())
				{
					str=rs.getString(2);
					str1=rs.getString(3);
					str2=rs.getString(4);
				}
				int m=Integer.parseInt(str1);
				int y=Integer.parseInt(returndate);
				
				if(book.equals(str))
				{
					out.println("Book found");
					out.println("<br>Issue Date  "+str1+" "+str2);
					out.println("<br>Return Date  "+returndate+" "+returnmonth);
					int z=y-m;
					if(z>=7)
					{
						int a=z-7;
						out.println("<br><b><big>you are "+a+" days late to return the book</big></b>");
						out.println("<br><b><big>Fine Paid  "+a+"</big></b>");
					}
					if(z<0)
					{
						int a=31-m;
						int b=y+a;
						if(b>=7)
						{
							b=b-7;
							out.println("<br><b><big>you are "+b+" days late to return the book</big></b>");
							out.println("<br><b><big>Fine Paid  "+b+"</big></b>");
						}
					}
				}
				else
				{
					out.println("Not Book Issued");
				}
			}
			
			if(value.equals("Return"))
			{
				ps=con.prepareStatement(query[1]);
				ps.setString(1,name);
		
				int x=ps.executeUpdate();
				if(x==1)
				{
					out.println("Record Deleted Successfully");
					
				}
				else
				{
					out.println("See Server Console");
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