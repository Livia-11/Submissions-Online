package com.submission.mis.service;

import com.submission.mis.dao.StudentDAO;
import com.submission.mis.dao.InstructorDAO;
import com.submission.mis.model.Student;
import com.submission.mis.model.Instructor;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {
    private final StudentDAO studentDAO;
    private final InstructorDAO instructorDAO;

    public AuthServiceImpl(StudentDAO studentDAO, InstructorDAO instructorDAO) {
        this.studentDAO = studentDAO;
        this.instructorDAO = instructorDAO;
    }

    @Override
    public Student loginStudent(String email, String password) {
        return studentDAO.findByEmail(email)
                .filter(student -> BCrypt.checkpw(password, student.getPassword()))
                .orElse(null);
    }

    @Override
    public Instructor loginInstructor(String email, String password) {
        return instructorDAO.findByEmail(email)
                .filter(instructor -> BCrypt.checkpw(password, instructor.getPassword()))
                .orElse(null);
    }

    @Override
    public void registerStudent(Student student) {
        if (!isEmailAvailable(student.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
        studentDAO.save(student);
    }

    @Override
    public void registerInstructor(Instructor instructor) {
        if (!isEmailAvailable(instructor.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        instructor.setPassword(BCrypt.hashpw(instructor.getPassword(), BCrypt.gensalt()));
        instructorDAO.save(instructor);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !studentDAO.emailExists(email) && !instructorDAO.emailExists(email);
    }
} 