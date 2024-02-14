package com.task;

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
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("select * from tests");
			ResultSet rs=pst.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("Assessments.html");
			rd.include(req, res);
			while(rs.next())
			{
				pw.println("<center>");
				pw.println("<table>");
				pw.println("<tr>");
				pw.println("<th>");
					pw.println("<td>"+rs.getString(1)+"</td>");
					pw.println("<td>"+rs.getString(2)+"</td>");
					pw.println("<td>"+rs.getString(3)+"</td>");
					pw.println("<td><form method=\"post\" action=\"attempt\"><input type=\"hidden\" name=\"testname\" value=\"" + rs.getString(1) + "\"><input type=\"submit\" value=\"Attempt\"></form></td>");
					pw.println("<td>Result</td>");
				pw.println("</th>");
				pw.println("</tr>");
				pw.println("</table>");
				pw.println("</center>");
				pw.println("<br>");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}