package org.AIMS;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class adminTest {

    @Test
    void adminFlow() throws IOException {
        admin admin = new admin();
        boolean b1 = admin.adminFlow(1,1);
        assertEquals(true,b1);
        boolean b2 = admin.adminFlow(2,1);
        assertEquals(true,b2);
        boolean b3 = admin.adminFlow(3,1);
        assertEquals(true,b3);
        boolean b4 = admin.adminFlow(4,1);
        assertEquals(true,b4);
//        boolean b5 = admin.adminFlow(1,0);
//        assertEquals(true,b5);
    }

    @Test
    void adm() throws IOException {
        admin admin = new admin();
        boolean b1 = admin.adm(1,"me123","metallurgy",2020,"mme",2,"c",5.0,"-1");
        assertEquals(true,b1);

        boolean b2 = admin.adm(2,"me123","metallurgy",2020,"mme",2,"c",5.0,"-1");
        assertEquals(true,b2);

    }

}