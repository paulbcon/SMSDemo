package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();
    List<Student> getStudentByEmail(String email);
    boolean validateStudent(String email, String password);
    void registerStudentToCourse(String email, Course course);
    List<Course> getStudentCourses(String email);
}
