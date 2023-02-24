package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentCourseService;
import jpa.service.StudentService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import static java.lang.System.out;

/**
 * 1
 *
 * @author Harry
 */
public class SMSRunner {

    private final Scanner sin;
    private final StringBuilder sb;

    private final CourseService courseService;
    private final StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        SMSRunner sms = new SMSRunner();
        sms.run();
    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();
                }
                break;
            case 2:
                out.println("Goodbye!");
                System.exit(0);
                break;

            default:

        }
    }

    private int menu1() {
        sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
        out.print(sb);
        sb.delete(0, sb.length());

        return sin.nextInt();
    }

    private boolean studentLogin() {
        out.print("Enter your email address: ");
        String email = sin.next();
        out.print("Enter your password: ");
        String password = sin.next();

        boolean retValue = studentService.validateStudent(email, password);

        if (retValue) {
            List<Course> courses = studentService.getStudentCourses(email);
            List<Student> students = studentService.getStudentByEmail(email);
            if (students != null) {
                currentStudent = students.get(0);
            }
            // System.out.println(courses);
            out.println("Classes for: " + currentStudent.getName());
            out.println("-----------------------------------------");
            for (Course course : courses) {
                out.println(course);
            }
            return true;
        } else {
            out.println("User Validation failed. GoodBye!");
            return false;
        }

    }

    private void registerMenu() {
        sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
        out.print(sb);
        sb.delete(0, sb.length());

        switch (sin.nextInt()) {
            case 1:
                List<Course> allCourses = courseService.getAllCourses();
                List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getEmail());
                allCourses.removeAll(studentCourses);
                out.printf("%5s%15S%15s\n", "ID", "Course", "Instructor");
                for (Course course : allCourses) {
                    out.println(course);
                }
                out.println();
                out.print("Enter Course Number: ");
                int number = sin.nextInt();
                Course newCourse = courseService.getCourseById(number).get(0);

                if (newCourse != null) {
                    studentService.registerStudentToCourse(currentStudent.getEmail(), newCourse);
                    Student temp = studentService.getStudentByEmail(currentStudent.getEmail()).get(0);

                    StudentCourseService scService = new StudentCourseService();
                    List<Course> sCourses = scService.getAllStudentCourses(temp.getEmail());


                    out.println("Classes for: " + currentStudent.getName());
                    out.println("-----------------------------------------");
                    for (Course course : sCourses) {
                        out.println(course);
                    }
                    registerMenu();
                }
                break;
            case 2:
            default:
                out.println("Goodbye!");
        }
    }
}
