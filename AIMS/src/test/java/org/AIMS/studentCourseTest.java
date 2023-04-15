package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class studentCourseTest {

    @Test
    void getsem() {

        studentCourse sc = new studentCourse();

        int b1 = sc.getsem("ppp");
        assertEquals(2,b1);

    }

    @Test
    void getbatch() {
        studentCourse sc = new studentCourse();
        int b1 = sc.getbatch("ppp");
        assertEquals(2020,b1);
    }

    @Test
    void getcurrentsemCredits() {
        studentCourse sc = new studentCourse();
        Double b1 = sc.getcurrentsemCredits("zz");
        assertEquals(18,b1);
    }

    @Test
    void getccc() {
        studentCourse sc = new studentCourse();
        Double b1 = sc.getccc("hs222");
        assertEquals(15,b1);
    }

    @Test
    void requisite_check() {
        studentCourse sc = new studentCourse();
        boolean b1 = sc.requisite_check("hs222","zz");
        assertEquals(true,b1);

        boolean b2 = sc.requisite_check("hs111","zz");
        assertEquals(true,b2);

    }

    @Test
    void cgCheck() {
        studentCourse sc = new studentCourse();
        boolean b1 = sc.cgCheck("zz","hs222",2);
        assertEquals(true,b1);
    }

    @Test
    void creditlimit() {
        studentCourse sc = new studentCourse();
        Double b1 = sc.creditlimit("zz",1);
        assertEquals(18,b1);
    }

    @Test
    void viewCourseOfferings() {
        studentCourse sc = new studentCourse();
        boolean b1 = sc.viewCourseOfferings("nitin",1);
        assertEquals(true,b1);
    }

    @Test
    void viewMyCourse() {
        studentCourse sc = new studentCourse();
        boolean b1 = sc.viewMyCourse("ppp",7);
        assertEquals(true,b1);
    }

    @Test
    void checkAvailability() {
        studentCourse sc = new studentCourse();
        boolean b1 = sc.checkAvailability("zz","hs222");
        assertEquals(false,b1);

        boolean b2 = sc.checkAvailability("zz","hs2sdch2");
        assertEquals(false,b2);

    }

}