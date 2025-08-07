package com.portal;

import java.io.IOException;
//import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if ("admin".equals(user) && "admin123".equals(pass)) {
            request.getSession().setAttribute("admin", "true");
            response.sendRedirect("ViewApplications");
        } else {
            response.getWriter().println("Invalid credentials");
        }
    }
}
