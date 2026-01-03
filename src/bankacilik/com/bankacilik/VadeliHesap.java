package com.bankacilik;

public class VadeliHesap extends Hesap {
    private double faizOrani;

    public VadeliHesap(int hesapNo, double bakiye, double faizOrani) {
        super(hesapNo, bakiye);
        this.faizOrani = faizOrani;
    }
    @Override
    public boolean paraCek(double miktar) {
        double islemUcreti = 15.0; 
        double toplamTutar = miktar + islemUcreti;

        if (this.bakiye >= toplamTutar) {
            this.bakiye -= toplamTutar;
            System.out.println("Vadeli hesaptan para çekildi. İşlem Ücreti: " + islemUcreti + " TL");
            System.out.println("Kalan Bakiye: " + this.bakiye + " TL");
            return true;
        } else {
            System.out.println("Yetersiz Bakiye (İşlem ücreti dahil)!");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Vadeli Hesap No: " + getHesapNo() + " | Faiz: %" + faizOrani + " | Bakiye: " + getBakiye() + " TL";
    }
}