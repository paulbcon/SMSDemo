package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;

import javax.persistence.NoResultException;
import java.util.List;

import static java.util.Objects.isNull;

public class StudentService implements StudentDAO {
    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = factory.openSession();

    @Override
    public List<Student> getAllStudents() {
        List<Student> results = null;
        try {
            String hql = "FROM Student s";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            results = (List<Student>) query.getResultList();
        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }

        return results;
    }

    @Override
    public List<Student> getStudentByEmail(String email) {
        List<Student> results = null;
        try {
            String hql = "FROM Student s WHERE s.email = ?1";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            results = (List<Student>) query.setParameter(1, email).getResultList();

        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }
        return results;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        boolean validate = false;
        try {
            SelectionQuery<?> query = session.createSelectionQuery(
                    "FROM Student s WHERE s.email = ?1 AND s.password = ?2");
            query.setParameter(1, email)
                    .setParameter(2, password)
                    .getSingleResult();
            // System.out.println(results);
            validate = true;

        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }

        return validate;
    }

    @Override
    public void registerStudentToCourse(String email, Course course) {
        try {

                Transaction t = session.beginTransaction();
                SelectionQuery<?> qry = session.createSelectionQuery(
                        "FROM Student s WHERE s.email=?1");
                Student stud1 = (Student) qry.setParameter(1, email)
                        .getSingleResult();

                String hqlInsert = "insert into student_course (email, id) VALUES (?,?)";
                session.createNativeQuery(hqlInsert, Course.class)
                        .setParameter(1, email)
                        .setParameter(2, course.getId())
                        .executeUpdate();
                t.commit();

                System.out.println(stud1.getName() + " is now registered to course: " + course.getName());


        } catch (Exception nr) {

            System.out.println("You are already registered to this course.");
        }

    }

    @Override
    public List<Course> getStudentCourses(String email) {
        List<Course> results = null;
        try {
            //System.out.println("Testing");
            SelectionQuery<?> query = session.createSelectionQuery(
                    "SELECT c FROM Course c INNER JOIN c.student s WHERE s.email = ?1");
            results = (List<Course>) query.setParameter(1, email)
                    .getResultList();

        } catch (NoResultException nr) {
            System.out.println(nr.getMessage());
        }
        return results;
    }
}
