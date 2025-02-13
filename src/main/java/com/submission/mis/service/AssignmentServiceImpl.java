package com.submission.mis.service;

import com.submission.mis.dao.AssignmentDAO;
import com.submission.mis.model.Assignment;
import com.submission.mis.model.Instructor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentDAO assignmentDAO;

    public AssignmentServiceImpl(AssignmentDAO assignmentDAO) {
        this.assignmentDAO = assignmentDAO;
    }

    @Override
    public void createAssignment(Assignment assignment) {
        validateAssignment(assignment);
        assignmentDAO.save(assignment);
    }

    @Override
    public Assignment getAssignment(Long id) {
        return assignmentDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }

    @Override
    public List<Assignment> getInstructorAssignments(Instructor instructor) {
        return assignmentDAO.findAllByInstructor(instructor);
    }

    @Override
    public List<Assignment> getActiveAssignments() {
        return assignmentDAO.findAllActive();
    }

    @Override
    public void updateAssignment(Assignment assignment) {
        validateAssignment(assignment);
        assignmentDAO.update(assignment);
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentDAO.delete(id);
    }

    @Override
    public boolean isDeadlinePassed(Assignment assignment) {
        return LocalDateTime.now().isAfter(assignment.getDeadline());
    }

    @Override
    public boolean isValidFileType(Assignment assignment, String fileType) {
        Set<String> allowedTypes = Arrays.stream(assignment.getAllowedFileTypes().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        
        return allowedTypes.contains(fileType.toLowerCase());
    }

    private void validateAssignment(Assignment assignment) {
        if (assignment.getTitle() == null || assignment.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (assignment.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline is required");
        }
        if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }
        if (assignment.getAllowedFileTypes() == null || assignment.getAllowedFileTypes().trim().isEmpty()) {
            throw new IllegalArgumentException("Allowed file types must be specified");
        }
    }
} 