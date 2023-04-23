package com.example.myproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void testAddition() {
        int result = 2 + 2;
        Assertions.assertEquals(4, result);
    }

    @Test
    public void testSubtraction() {
        int result = 5 - 3;
        Assertions.assertEquals(2, result);
    }
}
