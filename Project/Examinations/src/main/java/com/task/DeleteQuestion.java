package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestion extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String testname=req.getParameter("testname");
		String question=req.getParameter("question");
		question = URLDecoder.decode(question, "UTF-8"); // Decode the question parameter
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
			PreparedStatement pst=con.prepareStatement("delete from "+testname+" where question=?");
			pst.setString(1, question);
			pst.executeUpdate();
			con.close();
			pw.println("<script>");
			pw.println("window.history.go(-2);");
            pw.println("</script>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
