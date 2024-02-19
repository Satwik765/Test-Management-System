package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateTestDetails extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String testname=req.getParameter("testname1");
		String testdate=req.getParameter("testdate");
		String testtime=req.getParameter("testtime");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("update tests set testdate=? where testname=?");
			pst.setString(1, testdate);
			pst.setString(2, testname);
			pst.executeUpdate();
			PreparedStatement pst2=con.prepareStatement("update tests set testtime=? where testname=?");
			pst2.setString(1, testtime);
			pst2.setString(2, testname);
			pst2.executeUpdate();
			pw.println("<script>");
            pw.println("window.close();");
            pw.println("</script>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
