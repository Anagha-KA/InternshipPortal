package com.portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DownloadCSV")
public class DownloadCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Admin check
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("forms/login.html");
            return;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"applications.csv\"");

        PrintWriter out = response.getWriter();
        out.println("ID,Name,College,Department,CGPA,Role");

        try (Connection con = DBConnection.initializeDatabase()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM applications");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                out.printf("%d,%s,%s,%s,%.2f,%s\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("college"),
                    rs.getString("department"),
                    rs.getDouble("cgpa"),
                    rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
