package com.submission.mis.servlet.auth;

import com.submission.mis.model.Student;
import com.submission.mis.model.Instructor;
import com.submission.mis.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize authService using dependency injection or factory
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        try {
            HttpSession session = request.getSession();
            
            if ("student".equals(userType)) {
                Student student = authService.loginStudent(email, password);
                if (student != null) {
                    session.setAttribute("user", student);
                    session.setAttribute("userType", "student");
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                    return;
                }
            } else if ("instructor".equals(userType)) {
                Instructor instructor = authService.loginInstructor(email, password);
                if (instructor != null) {
                    session.setAttribute("user", instructor);
                    session.setAttribute("userType", "instructor");
                    response.sendRedirect(request.getContextPath() + "/instructor/dashboard");
                    return;
                }
            }

            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during login");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
} 