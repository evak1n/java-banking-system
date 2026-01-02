package com.bankacilik;

public abstract class Hesap {
    private final int hesapNo;
    protected double bakiye;
    
    public Hesap(int hesapNo, double baslangicBakiyesi) {
        this.hesapNo = hesapNo;
        this.bakiye = baslangicBakiyesi;
    }
    
    public void paraYatir(double miktar) {
        if (miktar > 0 && Double.isFinite(miktar)) {
            this.bakiye += miktar;
            System.out.println("Yatırılan: " + miktar + " TL | Yeni Bakiye: " + this.bakiye + " TL");
        } else {
            System.out.println("Geçersiz miktar!");
        }
    }
    public abstract boolean paraCek(double miktar);
    public double getBakiye() { return bakiye; }
    public int getHesapNo() { return hesapNo; }
}