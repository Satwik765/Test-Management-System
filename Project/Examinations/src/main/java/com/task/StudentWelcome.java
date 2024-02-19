package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentWelcome extends HttpServlet
{
	static String usernameGlobal;
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("select * from student where username=? and password=?");
			pst.setString(1,username);
			pst.setString(2,password);
			ResultSet rs=pst.executeQuery();
			String msg;
			if(rs.next())
			{
				ResultSetMetaData rsmd=rs.getMetaData();
				int id=rs.getInt("id");
				usernameGlobal=rs.getString("username");
				String fname=rs.getString("firstname");
				String lname=rs.getString("lastname");
				res.sendRedirect("StudentPage.html?username="+username+"&id="+id+"&fname="+fname+"&lname="+lname);
			}
			else
			{
				if(username=="")
				msg="Please enter username";
				else if(password=="")
				msg="Please enter password";
				else
				msg="Invalid Credentials";
				res.sendRedirect("StudentLogin.html?msg="+msg);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}