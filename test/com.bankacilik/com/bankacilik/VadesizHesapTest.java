package com.bankacilik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class VadesizHesapTest {
    private VadesizHesap hesap;

    @BeforeEach
    void setUp() {
        hesap = new VadesizHesap(4004, 250.0);
    }
    @Test
    void testParaYatir() {
        assertTrue(hesap.paraYatir(100));
        assertEquals(350.0, hesap.getBakiye(), 0.001);
    }

    @Test
    void testParaCek() {
        assertTrue(hesap.paraCek(50));
        assertEquals(200.0, hesap.getBakiye(), 0.001);
    }

    @Test
    void testNegatifMiktar() {
        assertThrows(IllegalArgumentException.class, () -> {
            hesap.paraCek(-10);
        });
    }

    @Test
    void testYetersizBakiye() {
        assertFalse(hesap.paraCek(500));
    }
}
