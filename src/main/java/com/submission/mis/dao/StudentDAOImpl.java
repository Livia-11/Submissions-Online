package com.submission.mis.dao;

import com.submission.mis.config.HibernateUtil;
import com.submission.mis.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {
    
    @Override
    public void save(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Student WHERE email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Optional<Student> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Student.class, id));
        }
    }

    @Override
    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(s) FROM Student s WHERE s.email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return count > 0;
        }
    }
} 