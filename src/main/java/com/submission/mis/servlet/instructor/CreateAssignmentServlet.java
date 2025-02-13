package com.submission.mis.servlet.instructor;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Instructor;
import com.submission.mis.service.AssignmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/instructor/assignments/create")
public class CreateAssignmentServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize assignmentService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/instructor/create-assignment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Assignment assignment = new Assignment();
            assignment.setTitle(request.getParameter("title"));
            assignment.setDescription(request.getParameter("description"));
            
            // Parse deadline from form
            String deadlineStr = request.getParameter("deadline");
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr, 
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            assignment.setDeadline(deadline);
            
            // Get allowed file types
            String[] allowedTypes = request.getParameterValues("allowedFileTypes");
            assignment.setAllowedFileTypes(String.join(",", allowedTypes));
            
            // Set instructor from session
            Instructor instructor = (Instructor) request.getSession().getAttribute("user");
            assignment.setInstructor(instructor);

            assignmentService.createAssignment(assignment);
            response.sendRedirect(request.getContextPath() + "/instructor/assignments");
            
        } catch (Exception e) {
            request.setAttribute("error", "Failed to create assignment: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/instructor/create-assignment.jsp")
                    .forward(request, response);
        }
    }
} 