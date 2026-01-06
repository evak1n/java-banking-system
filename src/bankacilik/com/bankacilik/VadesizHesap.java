package com.bankacilik;

import java.time.LocalDateTime;

/**
 * Vadesiz hesap sınıfı.
 * <p>
 * Hesap bakiyesinden doğrudan para çekilebilir ve para yatırma işlemleri yapılabilir.
 * </p>
 */
public class VadesizHesap extends Hesap {
    
	/**
     * Vadesiz hesap oluşturur.
     *
     * @param hesapNo Hesap numarası
     * @param bakiye Başlangıç bakiyesi
     */
    public VadesizHesap(int hesapNo, double bakiye) {
        super(hesapNo, bakiye);
    }

    /**
     * Hesaptan para çeker.
     * <p>
     * Çekim miktarı geçersiz ise IllegalArgumentException fırlatılır.
     * Yetersiz bakiye varsa işlem gerçekleşmez.
     * </p>
     *
     * @param miktar Çekilmek istenen miktar
     * @return İşlem başarılı ise true, aksi halde false
     */
    @Override
    public boolean paraCek(double miktar) {

        if (!(Double.isFinite(miktar) && miktar > 0)) {
            throw new IllegalArgumentException("Geçersiz miktar!"); 
        }

        if (miktar > this.bakiye)  return false;
        

        this.bakiye -= miktar;
        
        // İşlem hakkında kullanıcıya bilgi verilir
               System.out.println("Çekilen: " + miktar + " TL | Kalan: " + this.bakiye + " TL");
               
               // İşlem kaydedilir
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
    
    /**
     * Hesaba para yatırır.
     *
     * @param miktar Yatırılacak miktar
     * @return İşlem başarılı ise true
     */
    public boolean paraYatir(double miktar) {
        if (!(Double.isFinite(miktar) && miktar > 0)) {
            throw new IllegalArgumentException("Geçersiz miktar!");
        }
        this.bakiye += miktar;
        
        // İşlem kaydedilir
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
     * Hesabın bilgilerini string olarak döner.
     *
     * @return Hesap bilgileri
     */
    @Override
    public String toString() {
        return "Vadesiz Hesap No: " + getHesapNo() + " | Bakiye: " + getBakiye() + " TL";
    }
}