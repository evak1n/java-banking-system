package com.bankacilik;

import java.time.LocalDateTime;

public class VadesizHesap extends Hesap {
    
    public VadesizHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
    }

    @Override
    public boolean paraCek(double miktar) {

        if (!(Double.isFinite(miktar) && miktar > 0)) {
            System.out.println("Geçersiz miktar!");
            return false;
        }

        if (miktar > this.bakiye) {
            System.out.println("Yetersiz Bakiye! İşlem yapılamadı.");
            return false;
        }

        this.bakiye -= miktar;
               System.out.println("Çekilen: " + miktar + " TL | Kalan: " + this.bakiye + " TL");

               kaydetIslem(new Transaction(
                       Transaction.Type.WITHDRAW,
                       getHesapNo(),
                       null,
                       miktar,
                       LocalDateTime.now(),
                       null,
                       this.getBakiye()
               ));

               return true;
           }

    @Override
    public String toString() {
        return "Vadesiz Hesap No: " + getHesapNo() + " | Bakiye: " + getBakiye() + " TL";
    }
}