package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class loginTest {

    @Test
    void loginStudent() {

        login lt = new login();
        boolean b1 = lt.loginStudent("ppp","1234");
        assertEquals(true,b1);

        boolean b2 = lt.loginStudent("ppppp","1234");
        assertEquals(false,b2);

    }

    @Test
    void loginInstructor() {
        login lt = new login();
        boolean b1 = lt.loginInstructor("nitin","1234");
        assertEquals(true,b1);

        boolean b2 = lt.loginInstructor("ppppp","1234");
        assertEquals(false,b2);
    }

    @Test
    void loginadmin() {
        login lt = new login();
        boolean b1 = lt.loginadmin("aa","1234");
        assertEquals(true,b1);
        boolean b2 = lt.loginadmin("pppsdfvp","1234");
        assertEquals(false,b2);
    }
}