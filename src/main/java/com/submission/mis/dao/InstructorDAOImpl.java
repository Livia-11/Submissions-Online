package com.submission.mis.dao;

import com.submission.mis.config.HibernateUtil;
import com.submission.mis.model.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {
    
    @Override
    public void save(Instructor instructor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(instructor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Instructor> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Instructor WHERE email = :email", Instructor.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Instructor.class, id));
        }
    }

    @Override
    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("SELECT COUNT(i) FROM Instructor i WHERE i.email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return count > 0;
        }
    }
} 