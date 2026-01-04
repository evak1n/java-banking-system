package com.bankacilik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class Hesap {
    private final int hesapNo;
    protected double bakiye;
    
    private final List<Transaction> hareketler = new ArrayList<>();
    
    public Hesap(int hesapNo, double baslangicBakiyesi) {

    	if (hesapNo <= 0) throw new IllegalArgumentException("Hesap numarası pozitif olmalıdır");
        	if (!Double.isFinite(baslangicBakiyesi) || baslangicBakiyesi < 0)
            throw new IllegalArgumentException("Başlangıç bakiyesi >= 0 ve sonlu olmalıdır");

        this.hesapNo = hesapNo;
        this.bakiye = baslangicBakiyesi;
        

        if (baslangicBakiyesi > 0) {
            kaydetIslem(new Transaction(
                    Transaction.Type.DEPOSIT,
                    null, this.hesapNo,
                    baslangicBakiyesi,
                    LocalDateTime.now(),
                    "Başlangıç bakiyesi",
                    this.bakiye));
        }
    }
    
    public void paraYatir(double miktar) {	
        if (miktar > 0 && Double.isFinite(miktar)) {
            this.bakiye += miktar;
            System.out.println("Yatırılan: " + miktar + " TL | Yeni Bakiye: " + this.bakiye + " TL");

            kaydetIslem(new Transaction(
                              Transaction.Type.DEPOSIT,
                              null, this.hesapNo,
                              miktar,
                              LocalDateTime.now(),
                              null,
                              this.bakiye
                      ));

        } else {
            System.out.println("Geçersiz miktar!");
        }
    }
    public abstract boolean paraCek(double miktar);
    

public void transferTo(Hesap hedef, double miktar, String aciklama) {
        if (hedef == null) throw new IllegalArgumentException("Hedef hesap null olamaz");
        if (hedef == this) throw new IllegalArgumentException("Aynı hesaba transfer yapılamaz");
        if (!(Double.isFinite(miktar) && miktar > 0))
            throw new IllegalArgumentException("Geçersiz miktar: pozitif ve sonlu olmalı");
        if (miktar > this.bakiye)
            throw new IllegalStateException("Yetersiz bakiye");

        this.bakiye -= miktar;
        hedef.bakiye += miktar;

        LocalDateTime now = LocalDateTime.now();
        kaydetIslem(new Transaction(

Transaction.Type.TRANSFER_OUT,
                this.hesapNo, hedef.hesapNo,
                miktar, now, aciklama, this.bakiye
        ));
        hedef.kaydetIslem(new Transaction(
                Transaction.Type.TRANSFER_IN,
                this.hesapNo, hedef.hesapNo,
                miktar, now, aciklama, hedef.bakiye
        ));

        System.out.println("Transfer: " + this.hesapNo + " -> " + hedef.hesapNo +
                " | " + miktar + " TL | Kaynak yeni bakiye: " + this.bakiye +
                " | Hedef yeni bakiye: " + hedef.bakiye);
    }

    public double getBakiye() { return bakiye; }
    public int getHesapNo() { return hesapNo; }
    

    public List<Transaction> getHareketler()	{
         return Collections.unmodifiableList(hareketler);
         } 
         protected void kaydetIslem(Transaction tx) {
                this.hareketler.add(tx);
            }
}