package com.submission.mis.dao;

import com.submission.mis.model.Assignment;
import com.submission.mis.model.Instructor;
import java.util.List;
import java.util.Optional;

public interface AssignmentDAO {
    void save(Assignment assignment);
    Optional<Assignment> findById(Long id);
    List<Assignment> findAllByInstructor(Instructor instructor);
    List<Assignment> findAllActive();
    void update(Assignment assignment);
    void delete(Long id);
} 