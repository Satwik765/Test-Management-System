package com.task;
import com.task.StudentWelcome;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Assessments extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		try
		{
			String id=req.getParameter("idd");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("select * from tests");
			ResultSet rs=pst.executeQuery();
			PreparedStatement pst2=con.prepareStatement("select * from student where username='"+StudentWelcome.usernameGlobal+"'");
			ResultSet rs2=pst2.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("Assessments.html");
			rd.include(req, res);
			pw.println("<center>");
			pw.println("<table>");
			while(rs.next())
			{
				String testname=rs.getString(1);
				pw.println("<tr>");
				pw.println("<th>");
					pw.println("<td>"+rs.getString(1)+"</td>");
					pw.println("<td>"+rs.getString(2)+"</td>");
					pw.println("<td>"+rs.getString(3)+"</td>");
					pw.println("<td><form method=\"post\" action=\"attempt\"><input type=\"hidden\" name=\"testname\" value=\"" + rs.getString(1) + "\"><input type=\"submit\" value=\"Attempt\"></form></td>");
					while(rs2.next())
					{
						pw.println("<td> "+rs2.getString(testname)+"</td>");
					}
				pw.println("</th>");
				pw.println("</tr>");
				rs2.beforeFirst(); 
			}
			pw.println("</table>");
			pw.println("</center>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}