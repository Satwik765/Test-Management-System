package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertTest extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String testname=req.getParameter("testname");
		String testdate=req.getParameter("testdate");
		String testtime=req.getParameter("testtime");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("insert into tests values(?,?,?)");
			pst.setString(1, testname);
			pst.setString(2, testdate);
			pst.setString(3, testtime);
			pst.executeUpdate();
			PreparedStatement pst2=con.prepareStatement("create table "+testname+"(question varchar(400),optionA varchar(50),optionB varchar(50),optionC varchar(50),optionD varchar(50),correct varchar(50))");
			pst2.execute();
			PreparedStatement pst3=con.prepareStatement("alter table student add "+testname+" varchar(10)");
			pst3.executeUpdate();
			PreparedStatement pst4=con.prepareStatement("update student set "+testname+"='-'");
			pst4.executeUpdate();
			res.sendRedirect("Questions.html?table="+testname);
		}
		catch(Exception e)
		{
			System.out.println(e);
			pw.println("<center><h1>Testname already exists. Please enter new test name</h1></center>");
		}
	}
}
