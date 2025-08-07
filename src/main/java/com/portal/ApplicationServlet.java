package com.portal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ApplicationServlet")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,     // 2MB
	    maxFileSize = 1024 * 1024 * 15,          // 15MB
	    maxRequestSize = 1024 * 1024 * 20        // 20MB
	)
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Read text fields
            String name = request.getParameter("name");
            String college = request.getParameter("college");
            String department = request.getParameter("department");
            double cgpa = Double.parseDouble(request.getParameter("cgpa"));
            String role = request.getParameter("role");

            // Handle resume file
            Part filePart = request.getPart("resume");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Save resume to /uploads/
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);  // Save file to server

            // Store data in DB
            Connection con = DBConnection.initializeDatabase();
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO applications (name, college, department, cgpa, role, resume_path) VALUES (?, ?, ?, ?, ?, ?)");

            st.setString(1, name);
            st.setString(2, college);
            st.setString(3, department);
            st.setDouble(4, cgpa);
            st.setString(5, role);
            st.setString(6, "uploads/" + fileName);  // relative path stored

            st.executeUpdate();
            st.close();
            con.close();

            //response.sendRedirect("forms/success.html");
            response.sendRedirect(request.getContextPath() + "/forms/success.html");


        } catch (Exception e) {
            e.printStackTrace();
            //response.sendRedirect("forms/error.html");
            //response.sendRedirect(request.getContextPath() + "/forms/error.html");
            response.setContentType("text/html");
            response.getWriter().println("<h2 style='color:red;'>An error occurred while submitting your application.</h2>");
            response.getWriter().println("<a href='forms/studentForm.html'>Back to Form</a>");


        }
    }
}
