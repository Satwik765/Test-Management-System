package com.task;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestSave extends HttpServlet
{
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
	    	PrintWriter pw=response.getWriter();
	    	response.setContentType("text/html");
	    	String table=request.getParameter("tablename");
	        String question = request.getParameter("question");
	        String optionA = request.getParameter("option1");
	        String optionB = request.getParameter("option2");
	        String optionC = request.getParameter("option3");
	        String optionD = request.getParameter("option4");
	        String correctOption = request.getParameter("correctOption");

	        Connection con = null;
	        PreparedStatement pstmt = null;

	        try 
	        {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/examination","root","root");
	            pstmt = con.prepareStatement("INSERT INTO "+table+"(question,optionA,optionB,optionC,optionD,correct) VALUES (?, ?, ?, ?, ?, ?)");
	            pstmt.setString(1, question);
	            pstmt.setString(2, optionA);
	            pstmt.setString(3, optionB);
	            pstmt.setString(4, optionC);
	            pstmt.setString(5, optionD);
	            pstmt.setString(6, correctOption);
	            pstmt.executeUpdate();
	            String msg="question saved";
	            response.sendRedirect("Questions.html?table="+table+"&msg="+msg);
	        }
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	            pw.println("not saved");
	        }
	    
	}

}