package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;

import javax.persistence.NoResultException;
import java.util.List;

public class CourseService implements CourseDAO {
    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = factory.openSession();
    Transaction t = session.beginTransaction();

    @Override
    public List<Course> getAllCourses() {
        List<Course> results = null;
        try {
            String hql = "FROM Course";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            results = (List<Course>) query.getResultList();
        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }
        return results;
    }

    public List<Course> getCourseById(int courseNumber) {
        List<Course> results = null;
        try {
            String hql = "FROM Course c WHERE c.id = ?1";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            results = (List<Course>) query.setParameter(1, courseNumber).getResultList();
        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }

        return results;
    }

}
