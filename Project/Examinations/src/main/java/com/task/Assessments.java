package com.task;
import com.task.StudentWelcome;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
			pw.println("<center><span id=\"msg\"></span></center>");
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
				//
				LocalDate currentDate=LocalDate.now();
				LocalTime currentTime=LocalTime.now();
				//
				DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter timeFormat=DateTimeFormatter.ofPattern("HH:mm");
				//
				LocalDate allowedDate=LocalDate.parse(rs.getString(2),dateFormat);
				LocalTime startTime=LocalTime.parse(rs.getString(3),timeFormat);
				LocalTime endTime=startTime.plusHours(1);
				//
				if(currentDate.isEqual(allowedDate)&&currentTime.isAfter(startTime)&&currentTime.isBefore(endTime))
				{
					pw.println("<td><form method=\"post\" action=\"attempt\"><input type=\"hidden\" name=\"testname\" value=\"" + rs.getString(1) + "\"><input type=\"submit\" value=\"Attempt\"></form></td>");
				}
				else if(currentDate.isBefore(allowedDate)||(currentDate.isEqual(allowedDate)&&currentTime.isBefore(startTime)))
				{
					pw.println("<td><button onclick=\"beforeTime()\">Attempt</button></td>");
				}
				else
				{
					pw.println("<td><button onclick=\"afterTime()\">Attempt</button></td>");
				}
				while(rs2.next())
				{
					pw.println("<td> "+rs2.getString(testname)+"</td>");
				}
				pw.println("</th>");
				pw.println("</tr>");
				rs2.beforeFirst(); 
			}
			pw.println("</table>");
			pw.println("<h5>You have access to attempt the test only after the test commencement and for up to 1 hour after the test commencement.</h5>");
			pw.println("</center>");
			pw.println("<script>");
	    	pw.println("function beforeTime(){");
	    	pw.println("document.getElementById(\"msg\").innerText=\"There is still time to start exam\";}");
	    	pw.println("function afterTime(){");
	    	pw.println("document.getElementById(\"msg\").innerText=\"You are too late to attempt\";}");
	    	pw.println("</script>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}