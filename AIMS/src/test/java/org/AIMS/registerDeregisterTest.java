package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class registerDeregisterTest {

    @Test
    void registerDeregisterInstructor() {

        registerDeregister rd = new registerDeregister();

        boolean b1 = rd.registerDeregisterInstructor(1,"nitin","cd234",5.0);
        assertEquals(true,b1);
        boolean b2 = rd.registerDeregisterInstructor(2,"nitin","cd234",5.0);
        assertEquals(true,b2);

    }

    @Test
    void registerDeregisterStudent() {

        registerDeregister rd = new registerDeregister();

        boolean b1 = rd.registerDeregisterStudent(1,"hs222","zz");
        assertEquals(false,b1);
        boolean b2 = rd.registerDeregisterStudent(2,"hs222","zz");
        assertEquals(true,b2);

        boolean b3 = rd.registerDeregisterStudent(1,"cs101","zz");
        assertEquals(false,b1);
        boolean b4 = rd.registerDeregisterStudent(2,"cs101","zz");
        assertEquals(true,b2);

        boolean b5 = rd.registerDeregisterStudent(1,"cs101","xx");
        assertEquals(false,b5);
        boolean b6 = rd.registerDeregisterStudent(2,"cs101","xx");
        assertEquals(true,b6);

    }
}