package com.task;
import com.task.Attempt;
import com.task.StudentWelcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Submission extends HttpServlet 
{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException 
    {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        try 
        {
            String testname = Attempt.testnameGlobal;
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examination", "root", "root");
            PreparedStatement pst2=con.prepareStatement("select * from "+testname);
            ResultSet rs=pst2.executeQuery();
            // Prepare statement to update responses
            PreparedStatement pst = con.prepareStatement("UPDATE " + testname + " SET "+StudentWelcome.usernameGlobal+" = ? WHERE question = ?");
            PreparedStatement pst3=con.prepareStatement("update student set "+testname+"=? where username='"+StudentWelcome.usernameGlobal+"'");
            // Iterate through questions and update responses
            int score=0;
            int i=1;
            while(rs.next())
            {
                String response = req.getParameter(Integer.toString(i)); 
                if(response.equals(rs.getString(6)))
                {
                	score++;
                }
                pst.setString(1, response); // Set response value
                pst.setString(2, rs.getString(1)); // Assuming questions are labeled as "Question 1", "Question 2", etc.
                pst.executeUpdate(); // Execute update query
                i++;
            }
            double r=(double)score/(i-1);
            int re=(int)(r*100);
            String result=Integer.toString(re)+"%";
            pst3.setString(1, result);
            pst3.executeUpdate();
            //Close database connection
            con.close();
            pw.println("<script>");
            pw.println("window.close();");
            pw.println("</script>");
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}
