package com.submission.mis.servlet.student;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Student;
import com.submission.mis.service.AssignmentService;
import com.submission.mis.service.SubmissionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet("/student/submit")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class SubmitAssignmentServlet extends HttpServlet {
    private SubmissionService submissionService;
    private AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize services
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        request.setAttribute("assignment", assignment);
        request.getRequestDispatcher("/WEB-INF/views/student/submit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));
            Assignment assignment = assignmentService.getAssignment(assignmentId);
            Student student = (Student) request.getSession().getAttribute("user");
            Part filePart = request.getPart("file");

            submissionService.submitAssignment(student, assignment, filePart);
            response.sendRedirect(request.getContextPath() + "/student/submissions");
            
        } catch (Exception e) {
            request.setAttribute("error", "Submission failed: " + e.getMessage());
            doGet(request, response);
        }
    }
} 