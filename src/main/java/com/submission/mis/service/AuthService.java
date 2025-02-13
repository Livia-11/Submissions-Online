package com.submission.mis.service;

import com.submission.mis.model.Student;
import com.submission.mis.model.Instructor;
import org.mindrot.jbcrypt.BCrypt;

public interface AuthService {
    Student loginStudent(String email, String password);
    Instructor loginInstructor(String email, String password);
    void registerStudent(Student student);
    void registerInstructor(Instructor instructor);
    boolean isEmailAvailable(String email);
} 