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
            PreparedStatement pst2=con.prepareStatement("alter table "+testname+" add id"+Integer.toString(StudentWelcome.idGlobal)+" varchar(15)");
            pst2.execute();
            PreparedStatement pst3=con.prepareStatement("update "+testname+" set id"+Integer.toString(StudentWelcome.idGlobal)+"=? where question=?");
            PreparedStatement pst = con.prepareStatement("select * from "+testname);
            ResultSet rs = pst.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("showquestions.html?testname="+testname);
			rd.include(req, res);
			int i=1;
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
            	String response = req.getParameter(String.valueOf(i));
            	pst3.setString(1, response);
            	pst3.setString(2, rs.getString(1));
            	pw.println("</table>");
            	i++;
            }
            pw.println("<input type=\"submit\" value=\"submit\">");
            con.close();
        } 
		catch (Exception e) 
		{
			System.out.println(e);
        }
	}
}