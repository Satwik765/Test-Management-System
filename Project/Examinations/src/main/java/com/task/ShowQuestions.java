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

public class ShowQuestions extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		try 
		{
			String testname=req.getParameter("testname");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examination", "root", "root");
            PreparedStatement pst = con.prepareStatement("select * from "+testname);
            ResultSet rs = pst.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("showquestions.html?testname="+testname);
			rd.include(req, res);
            while (rs.next()) 
            {
            	pw.println("<table>");
            	pw.println("<tr>");
            	pw.println("<th>Question:"+rs.getString(1)+"</th>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>Option 1:"+rs.getString(2)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>Option 2:"+rs.getString(3)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>Option 3:"+rs.getString(4)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>Option 4:"+rs.getString(5)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>Correct Option:"+rs.getString(6)+"</td>");
            	pw.println("</tr>");
            	pw.println("</table>");
            	pw.println("<form method=\"get\" action=\"deletequestion\"><input type=\"hidden\" id=\"testname\" name=\"testname\"><input type=\"hidden\" id=\"question\" name=\"question\"><input type=\"submit\" value=\"remove\"></form>");
            }
            con.close();
        } 
		catch (Exception e) 
		{
			System.out.println(e);
        }
	}
}