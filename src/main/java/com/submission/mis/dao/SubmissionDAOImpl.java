package com.submission.mis.dao;

import com.submission.mis.config.HibernateUtil;
import com.submission.mis.model.Assignment;
import com.submission.mis.model.Student;
import com.submission.mis.model.Submission;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class SubmissionDAOImpl implements SubmissionDAO {
    
    @Override
    public void save(Submission submission) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(submission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Submission> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Submission.class, id));
        }
    }

    @Override
    public List<Submission> findByAssignment(Assignment assignment) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Submission s WHERE s.assignment = :assignment ORDER BY s.submissionTime DESC", 
                    Submission.class)
                    .setParameter("assignment", assignment)
                    .list();
        }
    }

    @Override
    public List<Submission> findByStudent(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Submission s WHERE s.student = :student ORDER BY s.submissionTime DESC", 
                    Submission.class)
                    .setParameter("student", student)
                    .list();
        }
    }

    @Override
    public Optional<Submission> findByStudentAndAssignment(Student student, Assignment assignment) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Submission s WHERE s.student = :student AND s.assignment = :assignment", 
                    Submission.class)
                    .setParameter("student", student)
                    .setParameter("assignment", assignment)
                    .uniqueResultOptional();
        }
    }

    @Override
    public void update(Submission submission) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(submission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Submission submission = session.get(Submission.class, id);
            if (submission != null) {
                session.remove(submission);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
} 