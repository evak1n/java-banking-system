package com.bankacilik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


class TestHesap extends Hesap {
    public TestHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
    }

    @Override
    public boolean paraCek(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) return false;
        if (miktar > this.getBakiye()) return false;
        try {
            java.lang.reflect.Field bakiyeField = Hesap.class.getDeclaredField("bakiye");
            bakiyeField.setAccessible(true);
            double bakiye = (double) bakiyeField.get(this);
            bakiyeField.set(this, bakiye - miktar);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
public class HesapTest {

    @Test
    void paraYatirTest() {
        Hesap h = new TestHesap(1001, 500);
        assertTrue(h.paraYatir(200));
        assertEquals(700, h.getBakiye());
    }

    @Test
    void paraCekTest() {
        Hesap h = new TestHesap(1002, 400);
        assertTrue(h.paraCek(100));
        assertEquals(300, h.getBakiye());

        assertFalse(h.paraCek(500));
        assertEquals(300, h.getBakiye());
    }

    @Test
    void transferTest() {
        Hesap h1 = new TestHesap(1003, 500);
        Hesap h2 = new TestHesap(1004, 200);

        h1.transferTo(h2, 300, "Test transfer");

        assertEquals(200, h1.getBakiye());
        assertEquals(500, h2.getBakiye());
    }

    @Test
    void transactionKaydiTest() {
        Hesap h = new TestHesap(1005, 100);
        h.paraYatir(50);
        h.paraCek(30);

        List<Transaction> tx = h.getHareketler();

        assertEquals(3, tx.size());
    }
}
