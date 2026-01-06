package com.bankacilik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hesap sınıfı tüm hesap türleri için temel özellikleri ve işlemleri içerir.
 * Her hesap için bakiye, hesap numarası ve işlem geçmişi tutulur.
 */
public abstract class Hesap {
	
	/** Hesap numarası, değiştirilemez */
    private final int hesapNo;
    
    /** Hesap bakiyesi, protected olduğu için alt sınıflardan erişilebilir */
    protected double bakiye;
    
    /** Hesap üzerindeki tüm işlemlerin kaydı */
    private final List<Transaction> hareketler = new ArrayList<>();
    
    /**
     * Hesap nesnesi oluşturur.
     *
     * @param hesapNo Hesap numarası (pozitif olmalı)
     * @param baslangicBakiyesi Başlangıç bakiyesi (>= 0 ve sonlu olmalı)
     */
    public Hesap(int hesapNo, double baslangicBakiyesi) {

    	if (hesapNo <= 0) throw new IllegalArgumentException("Hesap numarası pozitif olmalıdır");
        	if (!Double.isFinite(baslangicBakiyesi) || baslangicBakiyesi < 0)
            throw new IllegalArgumentException("Başlangıç bakiyesi >= 0 ve sonlu olmalıdır");

        this.hesapNo = hesapNo;
        this.bakiye = baslangicBakiyesi;
        
        // Başlangıç bakiyesi varsa işlemi kaydet
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
    

    /**
     * Hesaba para yatırır.
     *
     * @param miktar Yatırılacak miktar (>0 ve sonlu olmalı)
     * @return Başarılıysa true, aksi halde exception fırlatılır
     */
    public boolean paraYatir(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) {
            throw new IllegalArgumentException("Geçersiz miktar!");
        }
        this.bakiye += miktar;

        // İşlem kaydı
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
    /**
     * Hesaptan para çeker. Her alt sınıf kendi çekme kurallarını uygular.
     *
     * @param miktar Çekilecek miktar
     * @return Başarılıysa true, aksi halde false
     */
    public abstract boolean paraCek(double miktar);
    

    /**
     * Bu hesaptan başka bir hesaba transfer yapar.
     *
     * @param hedef Hedef hesap
     * @param miktar Transfer miktarı
     * @param aciklama İşlem açıklaması
     */
public void transferTo(Hesap hedef, double miktar, String aciklama) {
        if (hedef == null) throw new IllegalArgumentException("Hedef hesap null olamaz");
        if (hedef == this) throw new IllegalArgumentException("Aynı hesaba transfer yapılamaz");
        if (!(Double.isFinite(miktar) && miktar > 0))
            throw new IllegalArgumentException("Geçersiz miktar: pozitif ve sonlu olmalı");
        if (miktar > this.bakiye)
            throw new IllegalStateException("Yetersiz bakiye");

        // Bakiyeleri güncelle
        this.bakiye -= miktar;
        hedef.bakiye += miktar;

        LocalDateTime now = LocalDateTime.now();
        
        // İşlem kayıtları
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


        // Konsola çıktı
        System.out.println("Transfer: " + this.hesapNo + " -> " + hedef.hesapNo +
                " | " + miktar + " TL | Kaynak yeni bakiye: " + this.bakiye +
                " | Hedef yeni bakiye: " + hedef.bakiye);
    }

	/** @return Hesap bakiyesi */
    public double getBakiye() { return bakiye; }
    
    /** @return Hesap numarası */
    public int getHesapNo() { return hesapNo; }
    
    /** @return İşlem geçmişi, değiştirilemez listede döner */
    public List<Transaction> getHareketler()	{
         return Collections.unmodifiableList(hareketler);
         } 
    
    /**
     * İşlem kaydını listeye ekler.
     *
     * @param tx İşlem nesnesi
     */

         protected void kaydetIslem(Transaction tx) {
                this.hareketler.add(tx);
            }
}