package com.portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewApplications")
public class ViewApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("forms/login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.initializeDatabase();
            Statement stmt = con.createStatement();

            // Dashboard Metrics
            ResultSet totalRs = stmt.executeQuery("SELECT COUNT(*) AS total FROM applications");
            int totalApplications = 0;
            if (totalRs.next()) totalApplications = totalRs.getInt("total");
            totalRs.close();

            ResultSet highCgpaRs = stmt.executeQuery("SELECT COUNT(*) AS high FROM applications WHERE cgpa > 8.0");
            int highCgpaCount = 0;
            if (highCgpaRs.next()) highCgpaCount = highCgpaRs.getInt("high");
            highCgpaRs.close();

            ResultSet roleRs = stmt.executeQuery("SELECT role, COUNT(*) AS count FROM applications GROUP BY role");
            Map<String, Integer> roleCounts = new HashMap<>();
            String popularRole = "";
            int maxCount = 0;
            while (roleRs.next()) {
                String role = roleRs.getString("role");
                int count = roleRs.getInt("count");
                roleCounts.put(role, count);
                if (count > maxCount) {
                    maxCount = count;
                    popularRole = role;
                }
            }
            roleRs.close();

            // Filter
         // Filter with validation
            String cgpaParam = request.getParameter("cgpa");
            String roleParam = request.getParameter("role");

            String query = "SELECT * FROM applications WHERE 1=1";
            boolean hasError = false;

            // Validate CGPA input
            Double cgpaValue = null;
            String errorMessage = null;

            if (cgpaParam != null && !cgpaParam.trim().isEmpty()) {
                try {
                    cgpaValue = Double.parseDouble(cgpaParam);
                    if (cgpaValue < 0 || cgpaValue > 10) {
                        errorMessage = "⚠️ CGPA must be between 0 and 10.";
                        hasError = true;
                    } else {
                        query += " AND cgpa >= " + cgpaValue;
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "⚠️ Invalid CGPA entered. Please enter a numeric value.";
                    hasError = true;
                }
            }


            // Validate role (optional, based on your dropdown options)
            if (roleParam != null && !roleParam.trim().isEmpty()) {
                query += " AND role = '" + roleParam.replace("'", "''") + "'"; // Escape single quotes
            }

            // Execute query only if no error
            ResultSet rs = null;
            if (!hasError) {
                rs = stmt.executeQuery(query);
            }

            // HTML Output
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>View Applications</title>");

            // Embedded CSS
            out.println("<style>");
            out.println("body { margin: 0; font-family: 'Segoe UI', sans-serif; background: #f0f4ff; padding: 20px; }");
            out.println(".top-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }");
            out.println(".top-bar h2 { margin: 0; }");
            out.println(".logout-btn { background: #dc2626; color: white; padding: 8px 16px; border-radius: 6px; text-decoration: none; font-weight: 600; }");

            out.println(".dashboard { display: flex; gap: 20px; flex-wrap: wrap; margin-bottom: 25px; }");
            out.println(".card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); min-width: 200px; flex: 1; }");
            out.println(".card h3 { margin: 0 0 10px; font-size: 16px; color: #333; }");
            out.println(".card span { font-size: 22px; font-weight: bold; color: #4f46e5; }");

            out.println(".filter-form { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.05); margin-bottom: 20px; display: flex; gap: 10px; align-items: center; flex-wrap: wrap; }");
            out.println(".filter-form input, .filter-form select { padding: 10px; border: 1px solid #ccc; border-radius: 8px; }");
            out.println(".filter-form button { background: #4f46e5; color: white; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }");

            out.println(".table-container { overflow-x: auto; }");
            out.println("table { width: 100%; border-collapse: collapse; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.05); }");
            out.println("th, td { padding: 12px; text-align: center; border-bottom: 1px solid #eee; }");
            out.println("th { background: #4f46e5; color: white; }");
            out.println("tr:hover { background-color: #f9f9f9; }");

            out.println(".download-form { margin-bottom: 20px; }");
            out.println(".download-form button { background: #10b981; color: white; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }");
            out.println("</style>");

            out.println("</head><body>");

            if (errorMessage != null) {
                out.println("<p style='color: red; font-weight: bold; margin-bottom: 20px;'>" + errorMessage + "</p>");
            }
            
            out.println("<div class='top-bar'>");
            out.println("<h2>Internship Applications</h2>");
            out.println("<a href='Logout' class='logout-btn'>Logout</a>");
            out.println("</div>");

            // Dashboard
            out.println("<div class='dashboard'>");
            out.println("<div class='card'><h3>Total Applications</h3><span>" + totalApplications + "</span></div>");
            out.println("<div class='card'><h3>High CGPA (&gt;8.0)</h3><span>" + highCgpaCount + "</span></div>");
            out.println("<div class='card'><h3>Popular Role</h3><span style='color: #a21caf;'>" + (popularRole.isEmpty() ? "N/A" : popularRole) + "</span></div>");
            out.println("</div>");

            // Filter Form
            out.println("<form action='ViewApplications' method='get' class='filter-form'>");
            out.println("<input type='number' step='0.01' name='cgpa' placeholder='e.g., 8.0' />");
            out.println("<select name='role'>");
            out.println("<option value=''>All Roles</option>");
            out.println("<option value='Developer'>Developer</option>");
            out.println("<option value='Designer'>Designer</option>");
            out.println("<option value='Tester'>Tester</option>");
            out.println("<option value='Analyst'>Analyst</option>");
            out.println("</select>");
            out.println("<button type='submit'>Apply Filters</button>");
            out.println("</form>");

            // Download Button
            out.println("<form action='DownloadCSV' method='get' class='download-form'>");
            out.println("<button type='submit'>Download CSV</button>");
            out.println("</form>");

            // Applications Table
            out.println("<div class='table-container'>");
            out.println("<table>");
            //out.println("<tr><th>ID</th><th>Name</th><th>College</th><th>Department</th><th>CGPA</th><th>Role</th><th>Resume</th></tr>");
            out.println("<tr><th>ID</th><th>Name</th><th>College</th><th>Department</th><th>CGPA</th><th>Role</th><th>Resume</th><th>Applied On</th></tr>");


            while (rs.next()) {
                String resumePath = rs.getString("resume_path");
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("college") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("<td>" + rs.getDouble("cgpa") + "</td>");
                out.println("<td>" + rs.getString("role") + "</td>");
                //out.println("<td>" + (resumePath != null && !resumePath.isEmpty() ? "<a href='/InternshipPortal/" + resumePath + "' target='_blank'>View</a>" : "N/A") + "</td>");
                String resumeLink = (resumePath != null && !resumePath.isEmpty())
                	    ? "<a href='/InternshipPortal/" + resumePath + "' target='_blank' style='color:white; background:#4f46e5; padding:6px 10px; border-radius:6px; text-decoration:none;'>Resume</a>"
                	    : "No Resume";

                	out.println("<td>" + resumeLink + "</td>");
                	out.println("<td>" + rs.getTimestamp("applied_at") + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>"); // table-container

            out.println("</body></html>");

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
