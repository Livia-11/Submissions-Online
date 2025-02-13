package com.submission.mis.dao;

import com.submission.mis.config.HibernateUtil;
import com.submission.mis.model.Assignment;
import com.submission.mis.model.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AssignmentDAOImpl implements AssignmentDAO {
    
    @Override
    public void save(Assignment assignment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(assignment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Assignment> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Assignment.class, id));
        }
    }

    @Override
    public List<Assignment> findAllByInstructor(Instructor instructor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Assignment a WHERE a.instructor = :instructor ORDER BY a.deadline DESC", 
                    Assignment.class)
                    .setParameter("instructor", instructor)
                    .list();
        }
    }

    @Override
    public List<Assignment> findAllActive() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Assignment a WHERE a.deadline > :now ORDER BY a.deadline ASC", 
                    Assignment.class)
                    .setParameter("now", LocalDateTime.now())
                    .list();
        }
    }

    @Override
    public void update(Assignment assignment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(assignment);
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
            Assignment assignment = session.get(Assignment.class, id);
            if (assignment != null) {
                session.remove(assignment);
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