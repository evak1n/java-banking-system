package com.bankacilik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BankSistemiTest {

    private BankSistemi bank;

    @BeforeEach
    void setUp() {
        bank = new BankSistemi();
    }

    @Test
    void testMusteriEkleVeBul() {
        Musteri m = new Musteri("11111111111", "Ali Veli", "1234");
        assertTrue(bank.musteriEkle(m));
        assertEquals(m, bank.musteriBulTckn("11111111111"));
        assertFalse(bank.musteriEkle(m));
    }

    @Test
    void testHesapAcVadesizVeVadeli() {
        bank.musteriEkle(new Musteri("22222222222", "Ayşe", "5678"));

        Hesap vadesiz = bank.hesapAcVadesiz("22222222222");
        assertNotNull(vadesiz);
        assertTrue(vadesiz instanceof VadesizHesap);

        assertThrows(IllegalStateException.class, () -> bank.hesapAcVadeli("22222222222", 0.05));

        bank.musteriEkle(new Musteri("33333333333", "Mehmet", "abcd"));
        Hesap vadeli = bank.hesapAcVadeli("33333333333", 0.1);
        assertTrue(vadeli instanceof VadeliHesap);
        assertEquals(0.1, ((VadeliHesap) vadeli).getFaizOrani());
    }

    @Test
    void testTransferByNoVeHareketler() {
        bank.musteriEkle(new Musteri("44444444444", "C", "1234"));
        bank.musteriEkle(new Musteri("55555555555", "D", "1234"));

        Hesap h1 = bank.hesapAcVadesiz("44444444444");
        Hesap h2 = bank.hesapAcVadesiz("55555555555");

        h1.paraYatir(1000);
        bank.transferByNo(h1.getHesapNo(), h2.getHesapNo(), 300, "Test transfer");

        assertEquals(700, h1.getBakiye());
        assertEquals(300, h2.getBakiye());

        List<Transaction> tx1 = h1.getHareketler();
        List<Transaction> tx2 = h2.getHareketler();
        assertEquals(2, tx1.size());
        assertEquals(1, tx2.size());
    }

    @Test
    void testHareketleriYazdir() {
        bank.musteriEkle(new Musteri("66666666666", "E", "1234"));
        Hesap h = bank.hesapAcVadesiz("66666666666");
        h.paraYatir(500);
        h.paraCek(200);

        assertDoesNotThrow(() -> bank.hareketleriYazdir(h.getHesapNo()));
    }

    @Test
    void testHesapBulByTckn() {
        bank.musteriEkle(new Musteri("77777777777", "F", "pw"));
        assertNull(bank.hesapBulByTckn("77777777777"));
        Hesap h = bank.hesapAcVadesiz("77777777777");
        assertEquals(h, bank.hesapBulByTckn("77777777777"));
    }
}
