package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class studentTest {

    @Test
    void graduationCheck() {

        student st = new student();
        boolean b1 = st.graduationCheck("rrr");
        assertEquals(false,b1);

        boolean b2 = st.graduationCheck("ppp");
        assertEquals(false,b2);

        boolean b3 = st.graduationCheck("xx");
        assertEquals(false,b3);



    }

    @Test
    void getsem() {
        student st = new student();

        int i1 = st.getsem("zz");
        assertEquals(5,i1);

        int i2 = st.getsem("xx");
        assertEquals(10,i2);

    }

    @Test
    void getcore() {
        student st = new student();

        Double i1 = st.getcore("zz");
        assertEquals(2.0,i1);

        Double i2 = st.getcore("xx");
        assertEquals(0.0,i2);
    }

    @Test
    void getelective() {
        student st = new student();

        Double i1 = st.getelective("zz");
        assertEquals(13.0,i1);

        Double i2 = st.getelective("xx");
        assertEquals(0.0,i2);
    }

    @Test
    void updateProfile() {
        student st = new student();
        boolean b1 = st.updateProfile("zz","0/0/0","99966211");
        assertEquals(true,b1);

    }
}