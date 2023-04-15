package org.AIMS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class cgpaTest {

    @Test
    public void flowTest() {

        cgpa cg = new cgpa();

        double cg1 = cg.flow("zz",2);
        assertEquals(9.666666666666666,cg1);

        double cg2 = cg.flow("zz",1);
        assertEquals(0.0,cg2);

        ///// incorrect username

        double cg5 = cg.flow("ppppp",8);
        assertEquals(-1.0,cg5);

    }
}