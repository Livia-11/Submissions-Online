package com.submission.mis.dao;

import com.submission.mis.model.Student;
import java.util.Optional;

public interface StudentDAO {
    void save(Student student);
    Optional<Student> findByEmail(String email);
    Optional<Student> findById(Long id);
    boolean emailExists(String email);
} 