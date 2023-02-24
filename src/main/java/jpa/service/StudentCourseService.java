package jpa.service;

import jpa.entitymodels.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;

import javax.persistence.NoResultException;
import java.util.List;

public class StudentCourseService {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = factory.openSession();
    Transaction t = session.beginTransaction();

    public List<Course> getAllStudentCourses(String email) {
        List<Course> results = null;
        try {
            String hql = "FROM Course c INNER JOIN c.student s WHERE s.email = ?1";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            results = (List<Course>) query.setParameter(1, email).getResultList();
        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }
        //factory.close();
        session.close();
        return results;
    }

}
