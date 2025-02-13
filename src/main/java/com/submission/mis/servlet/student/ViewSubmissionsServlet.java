package com.submission.mis.servlet.student;

import com.submission.mis.model.Student;
import com.submission.mis.model.Submission;
import com.submission.mis.service.SubmissionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/submissions")
public class ViewSubmissionsServlet extends HttpServlet {
    private SubmissionService submissionService;

    @Override
    public void init() throws ServletException {
        // TODO: Initialize submissionService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Student student = (Student) request.getSession().getAttribute("user");
        List<Submission> submissions = submissionService.getStudentSubmissions(student);
        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("/WEB-INF/views/student/submissions.jsp").forward(request, response);
    }
} 