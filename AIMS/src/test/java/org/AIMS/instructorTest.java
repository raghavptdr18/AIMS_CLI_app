package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class instructorTest {

    @Test
    void checkWindow() {

        instructor it = new instructor();

        int i = it.checkWindow(1);
        assertEquals(1,i);

    }
}