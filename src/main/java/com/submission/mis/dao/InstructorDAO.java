package com.submission.mis.dao;

import com.submission.mis.model.Instructor;
import java.util.Optional;

public interface InstructorDAO {
    void save(Instructor instructor);
    Optional<Instructor> findByEmail(String email);
    Optional<Instructor> findById(Long id);
    boolean emailExists(String email);
} 