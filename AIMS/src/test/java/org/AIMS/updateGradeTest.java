package org.AIMS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class updateGradeTest {

    @Test
    void flow() {
        updateGrade ug = new updateGrade();
        boolean b = ug.flow();
        assertEquals(true,b);
    }
}