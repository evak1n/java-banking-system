package com.bankacilik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;


class TestHesap extends Hesap {
    public TestHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
        if (bakiye > 0) {
        	kaydetIslem(new Transaction(
                    Transaction.Type.DEPOSIT,
                    hesapNo,
                    hesapNo,
                    bakiye,
                    LocalDateTime.now(),
                    "Başlangıç Bakiyesi",
                    bakiye
        	));
        }
    }

    @Override
    public boolean paraCek(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) {
        	  System.out.println("Geçersiz miktar!");
        	  return false;
        }
        if (miktar > this.getBakiye()) {
            System.out.println("Yetersiz bakiye!");
            return false;
        }
        this.bakiye -= miktar;
        kaydetIslem(new Transaction(
                Transaction.Type.WITHDRAW,
                getHesapNo(),
                null,
                miktar,
                LocalDateTime.now(),
                "Test çekim",
                this.getBakiye()
            ));

            return true;
    }

    @Override
    
    public boolean paraYatir(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) return false;
        
        this.bakiye += miktar;
        kaydetIslem(new Transaction(
            Transaction.Type.DEPOSIT,
            getHesapNo(),
            null,
            miktar,
            LocalDateTime.now(),
            "Para yatırma",
            this.getBakiye()
        ));
        return true;
    }
}
public class HesapTest{

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
