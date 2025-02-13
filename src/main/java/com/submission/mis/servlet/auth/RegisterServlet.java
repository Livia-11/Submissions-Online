package com.submission.mis.servlet.auth;

import com.submission.mis.model.Student;
import com.submission.mis.model.Instructor;
import com.submission.mis.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize authService using dependency injection or factory
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        try {
            if (!authService.isEmailAvailable(email)) {
                request.setAttribute("error", "Email already exists");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            if ("student".equals(userType)) {
                Student student = new Student();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setEmail(email);
                student.setPassword(password);
                authService.registerStudent(student);
            } else if ("instructor".equals(userType)) {
                Instructor instructor = new Instructor();
                instructor.setFirstName(firstName);
                instructor.setLastName(lastName);
                instructor.setEmail(email);
                instructor.setPassword(password);
                authService.registerInstructor(instructor);
            }

            response.sendRedirect(request.getContextPath() + "/login?registered=true");
            
        } catch (Exception e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }
} 