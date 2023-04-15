package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class gradesTest {

    @Test
    void allGrades() {
        grades g = new grades();
        boolean c = g.allGrades("nitin");
        assertEquals(true,c);

        // invalid User

        boolean c1 = g.allGrades("ndhcb");
        assertEquals(false,c1);

    }

    @Test
    void allgradesadmin() {
        grades g = new grades();
        boolean c = g.allgradesadmin();
        assertEquals(true,c);
    }

    @Test
    void studentGrade() {
        grades g = new grades();
        boolean c = g.studentGrade("ppp");
        assertEquals(true,c);

        // invalid user

        boolean c1 = g.studentGrade("ppp22");
        assertEquals(false,c1);

    }
}