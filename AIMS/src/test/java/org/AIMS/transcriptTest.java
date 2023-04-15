package org.AIMS;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class transcriptTest {

    @Test
    void reciept() throws IOException {

        transcript tc = new transcript();
        boolean b1 = tc.reciept("zz");
        assertEquals(true,b1);
    }
}