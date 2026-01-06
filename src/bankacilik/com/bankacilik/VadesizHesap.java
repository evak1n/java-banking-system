package com.bankacilik;

import java.time.LocalDateTime;

public class VadesizHesap extends Hesap {
    
    public VadesizHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
    }

    @Override
    public boolean paraCek(double miktar) {

        if (!(Double.isFinite(miktar) && miktar > 0)) {
            throw new IllegalArgumentException("Geçersiz miktar!"); 
        }

        if (miktar > this.bakiye)  return false;
        

        this.bakiye -= miktar;
               System.out.println("Çekilen: " + miktar + " TL | Kalan: " + this.bakiye + " TL");

               kaydetIslem(new Transaction(
                       Transaction.Type.WITHDRAW,
                       getHesapNo(),
                       null,
                       miktar,
                       LocalDateTime.now(),
                       "Para çekme",
                       this.getBakiye()
               ));

               return true;
           }
    public boolean paraYatir(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) {
            throw new IllegalArgumentException("Geçersiz miktar!");
        }
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

    @Override
    public String toString() {
        return "Vadesiz Hesap No: " + getHesapNo() + " | Bakiye: " + getBakiye() + " TL";
    }
}