package com.submission.mis.service;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Student;
import com.submission.mis.model.Submission;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public interface SubmissionService {
    Submission submitAssignment(Student student, Assignment assignment, Part filePart) throws IOException;
    List<Submission> getStudentSubmissions(Student student);
    List<Submission> getAssignmentSubmissions(Assignment assignment);
    Submission getSubmission(Long id);
    void deleteSubmission(Long id) throws IOException;
    boolean hasStudentSubmitted(Student student, Assignment assignment);
} 