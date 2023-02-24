package jpa.service;

import jpa.entitymodels.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CourseServiceTest {
    CourseService courseService;

    public void setUp() throws Exception {
        courseService = new CourseService();
    }

    @Test
    public void testGetAllCourses() {
        fail("Not yet implemented");
    }
}