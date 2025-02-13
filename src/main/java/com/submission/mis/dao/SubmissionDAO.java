package com.submission.mis.dao;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Student;
import com.submission.mis.model.Submission;
import java.util.List;
import java.util.Optional;

public interface SubmissionDAO {
    void save(Submission submission);
    Optional<Submission> findById(Long id);
    List<Submission> findByAssignment(Assignment assignment);
    List<Submission> findByStudent(Student student);
    Optional<Submission> findByStudentAndAssignment(Student student, Assignment assignment);
    void update(Submission submission);
    void delete(Long id);
} 