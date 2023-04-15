package org.AIMS;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class instCourseTest {

    @Test
    void viewCourseCatalog() {
        instCourse c = new instCourse();
        boolean b = c.viewCourseCatalog();
        assertEquals(true,b);
    }

    @Test
    void viewMyCourse() {
        instCourse c = new instCourse();
        boolean b = c.viewMyCourse("nitin");
        assertEquals(true,b);

        // invalid user

        boolean b1 = c.viewMyCourse("pfgcp");
        assertEquals(false,b1);

    }

    @Test
    void checkWindow() {
        instCourse c = new instCourse();
        int b = c.checkWindow(1);
        assertEquals(1,b);
    }

}