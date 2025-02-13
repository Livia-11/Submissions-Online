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
import java.util.List;

@WebServlet("/instructor/assignments")
public class ManageAssignmentsServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize assignmentService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Instructor instructor = (Instructor) request.getSession().getAttribute("user");
        List<Assignment> assignments = assignmentService.getInstructorAssignments(instructor);
        request.setAttribute("assignments", assignments);
        request.getRequestDispatcher("/WEB-INF/views/instructor/assignments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));

        if ("delete".equals(action)) {
            assignmentService.deleteAssignment(assignmentId);
        }

        response.sendRedirect(request.getContextPath() + "/instructor/assignments");
    }
} 