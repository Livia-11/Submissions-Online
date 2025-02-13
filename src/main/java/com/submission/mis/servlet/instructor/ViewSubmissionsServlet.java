package com.submission.mis.servlet.instructor;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Submission;
import com.submission.mis.service.AssignmentService;
import com.submission.mis.service.SubmissionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/instructor/assignments/submissions")
public class ViewSubmissionsServlet extends HttpServlet {
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
        List<Submission> submissions = submissionService.getAssignmentSubmissions(assignment);
        
        request.setAttribute("assignment", assignment);
        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("/WEB-INF/views/instructor/view-submissions.jsp").forward(request, response);
    }
} 