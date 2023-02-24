package jpa.service;

import jpa.entitymodels.Student;
import junit.framework.TestCase;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class StudentServiceTest extends TestCase {

    Student stud1;
    Student stud2;
    Student stud3;
    StudentService studentService;

    @BeforeEach
    public void setUp() throws Exception {
        stud1 = new Student("ew@gmail.com","Ezra Williams","password123");
        stud2 = new Student("an@gmail.com","Alex Nobito","password456");
        stud3 = new Student("ra@gmail.com","Randolf Alexis","password789");
        studentService = new StudentService();
    }

    @Test
    public void testGetAllStudents() {
       List<Student> expected = new ArrayList<>();
       expected.add(stud1);
       expected.add(stud2);
       expected.add(stud3);

       List<Student> actual = new ArrayList<>();
       Student  actstud1 = new Student("ew@gmail.com","Ezra Williams","password123");
       Student  actstud2 = new Student("an@gmail.com","Alex Nobito","password456");
       Student  actstud3 = new Student("ra@gmail.com","Randolf Alexis","password789");

       actual.add(actstud1);
       actual.add(actstud2);
       actual.add(actstud3);

        //1. Test equal.
        assertThat(actual, is(expected));

        //2. If List has this value?
        assertThat(actual, hasItems(actstud1));

        //3. Check List Size
        assertThat(actual, hasSize(3));

        assertThat(actual.size(), is(3));

        //4.  List order

        // Ensure Correct order
        assertThat(actual, contains(stud1, stud2, stud3));

        // Can be any order
        assertThat(actual, containsInAnyOrder(stud3, stud2, stud1));

        //5. check empty list
        assertThat(new ArrayList<>(), IsEmptyCollection.empty());

        //Check if this is an empty collection
        //assertThat(new ArrayList<>(), not(IsEmptyCollection.empty()));

    }

    @Test
    public void testGetStudentByEmail() {
        assertNotNull(studentService.getStudentByEmail("cstartin3@flickr.com"));
    }

    @Test
    public void testValidateStudent() {
        assertTrue(studentService.validateStudent("abc@test.com","password"));
    }

    @Test
    public void testRegisterStudentToCourse() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStudentCourses() {
        fail("Not yet implemented");
    }
}