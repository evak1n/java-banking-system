package com.bankacilik;

public class VadesizHesap extends Hesap {
    
    public VadesizHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
    }

    @Override
    public boolean paraCek(double miktar) {
        if (miktar > 0 && this.bakiye >= miktar) {
            this.bakiye -= miktar;
            System.out.println("Çekilen: " + miktar + " TL | Kalan: " + this.bakiye + " TL");
            return true;
        } else {
            System.out.println("Yetersiz Bakiye! İşlem yapılamadı.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Vadesiz Hesap No: " + getHesapNo() + " | Bakiye: " + getBakiye() + " TL";
    }
}