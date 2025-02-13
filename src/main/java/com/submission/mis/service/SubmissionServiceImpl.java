package com.submission.mis.service;

import com.submission.mis.dao.SubmissionDAO;
import com.submission.mis.model.Assignment;
import com.submission.mis.model.Student;
import com.submission.mis.model.Submission;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionDAO submissionDAO;
    private final FileService fileService;
    private final AssignmentService assignmentService;

    public SubmissionServiceImpl(SubmissionDAO submissionDAO, FileService fileService, AssignmentService assignmentService) {
        this.submissionDAO = submissionDAO;
        this.fileService = fileService;
        this.assignmentService = assignmentService;
    }

    @Override
    public Submission submitAssignment(Student student, Assignment assignment, Part filePart) throws IOException {
        validateSubmission(student, assignment, filePart);

        String fileName = filePart.getSubmittedFileName();
        String fileType = fileService.getFileExtension(fileName);
        String directory = "submissions/" + assignment.getId();
        String filePath = fileService.saveFile(filePart, directory);

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setFilePath(filePath);
        submission.setFileName(fileName);
        submission.setFileType(fileType);
        submission.setSubmissionTime(LocalDateTime.now());

        submissionDAO.save(submission);
        return submission;
    }

    @Override
    public List<Submission> getStudentSubmissions(Student student) {
        return submissionDAO.findByStudent(student);
    }

    @Override
    public List<Submission> getAssignmentSubmissions(Assignment assignment) {
        return submissionDAO.findByAssignment(assignment);
    }

    @Override
    public Submission getSubmission(Long id) {
        return submissionDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));
    }

    @Override
    public void deleteSubmission(Long id) throws IOException {
        Submission submission = getSubmission(id);
        fileService.deleteFile(submission.getFilePath());
        submissionDAO.delete(id);
    }

    @Override
    public boolean hasStudentSubmitted(Student student, Assignment assignment) {
        return submissionDAO.findByStudentAndAssignment(student, assignment).isPresent();
    }

    private void validateSubmission(Student student, Assignment assignment, Part filePart) {
        if (assignmentService.isDeadlinePassed(assignment)) {
            throw new IllegalStateException("Assignment deadline has passed");
        }

        if (hasStudentSubmitted(student, assignment)) {
            throw new IllegalStateException("You have already submitted this assignment");
        }

        String fileName = filePart.getSubmittedFileName();
        String fileType = fileService.getFileExtension(fileName);
        if (!assignmentService.isValidFileType(assignment, fileType)) {
            throw new IllegalArgumentException("Invalid file type. Allowed types: " + assignment.getAllowedFileTypes());
        }
    }
} 