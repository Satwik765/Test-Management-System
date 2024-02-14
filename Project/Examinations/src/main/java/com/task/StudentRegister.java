package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentRegister extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String fname=req.getParameter("fname");
		String lname=req.getParameter("lname");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("insert into student(firstname,lastname,username,password) values(?,?,?,?)");
			pst.setString(1,fname);
			pst.setString(2,lname);
			pst.setString(3,username);
			pst.setString(4,password);
			int t=pst.executeUpdate();
			pw.print(t);
			con.close();
			System.out.println("Record inserted successfully");
			String msg="successfully registered";
			res.sendRedirect("StudentLogin.html?msg="+msg);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			pw.print("Username Already Exists");
		}
	}
}
