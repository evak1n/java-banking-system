package com.bankacilik;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VadeliHesapTest {
    private VadeliHesap hesap;

    @BeforeEach
    void setUp() {
        hesap = new VadeliHesap(3003, 1000.0, 5.0);
    }

    @Test
    void testParaCek() {
        assertTrue(hesap.paraCek(100));
        assertEquals(885.0, hesap.getBakiye(), 0.001);
    }

    @Test
    void testYetersizBakiye() {
        assertFalse(hesap.paraCek(990));
    }
}
