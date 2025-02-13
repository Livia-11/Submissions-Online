package com.submission.mis.service;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Instructor;
import java.util.List;

public interface AssignmentService {
    void createAssignment(Assignment assignment);
    Assignment getAssignment(Long id);
    List<Assignment> getInstructorAssignments(Instructor instructor);
    List<Assignment> getActiveAssignments();
    void updateAssignment(Assignment assignment);
    void deleteAssignment(Long id);
    boolean isDeadlinePassed(Assignment assignment);
    boolean isValidFileType(Assignment assignment, String fileType);
} 