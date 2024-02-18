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

public class Attempt extends HttpServlet
{
	static int qGlobal=0;
	static String testnameGlobal;
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		try 
		{
			String id=req.getParameter("idd");
			String testname=req.getParameter("testname");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examination", "root", "root");
            PreparedStatement pst2=con.prepareStatement("alter table "+testname+" add "+StudentWelcome.usernameGlobal+" varchar(15)");
            pst2.execute();
            PreparedStatement pst = con.prepareStatement("select * from "+testname);
            ResultSet rs = pst.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("attemptquestions.html?testname="+testname);
			rd.include(req, res);
			int i=1;
			testnameGlobal=testname;
			pw.println("<form id=\"attemptForm\" method=\"post\" action=\"submit\">");
            while (rs.next()) 
            {
            	pw.println("<table>");
            	pw.println("<tr>");
            	pw.println("<th>"+i+": "+rs.getString(1)+"</th>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>A)  "+rs.getString(2)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>B)  "+rs.getString(3)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>C)  "+rs.getString(4)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>D)  "+rs.getString(5)+"</td>");
            	pw.println("</tr>");
            	pw.println("<tr>");
            	pw.println("<td>");
            	pw.println("<select id="+i+" name="+i+">");
            		pw.println("<option>Response</option>");
            		pw.println("<option>Option A</option>");
            		pw.println("<option>Option B</option>");
            		pw.println("<option>Option C</option>");
            		pw.println("<option>Option D</option>");
            	pw.println("</select>");
            	pw.println("</td>");
            	pw.println("</tr>");
            	pw.println("</table>");
            	i++;
            }
            qGlobal=i-1;
            pw.println("<input type=\"submit\" value=\"submit\"></form>");
            con.close();
        } 
		catch (Exception e) 
		{
			System.out.println(e);
			String msg="You reached your maximum attempts";
			pw.println("<center><h1>"+msg+"</h1></center>");
        }
	}
}