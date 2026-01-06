package com.bankacilik;

import java.time.LocalDateTime;
/**
 * Vadeli hesap sınıfı. Hesap bakiyesine faiz uygulanabilir ve para çekme işlemlerinde işlem ücreti alınır.
 * <p>
 * Bu sınıf, {@link Hesap} sınıfını genişletir ve çekim işlemlerinde işlem ücreti uygular.
 * </p>
 */

public class VadeliHesap extends Hesap {
	
	/** Vadeli hesap için faiz oranı (%) */
    private double faizOrani;
  
    /**
     * Vadeli hesap oluşturur.
     * @param hesapNo Hesap numarası
     * @param bakiye Başlangıç bakiyesi
     * @param faizOrani Hesap için faiz oranı (%)
     */
    public VadeliHesap(int hesapNo, double bakiye, double faizOrani) {
        super(hesapNo, bakiye);
        this.faizOrani = faizOrani;
    }
    
    /**
     * Hesabın faiz oranını döner.
     *
     * @return Faiz oranı (%)
     */
    public double getFaizOrani() {
        return faizOrani;
    }
    
    /**
     * Vadeli hesaptan para çeker.
     * <p>
     * Çekim sırasında sabit bir işlem ücreti (15 TL) uygulanır.
     * Yetersiz bakiye durumunda işlem gerçekleşmez.
     * </p>
     *
     * @param miktar Çekilmek istenen miktar
     * @return İşlem başarılı ise true, aksi halde false
     */
    @Override
    public boolean paraCek(double miktar) {
    	 if (!(Double.isFinite(miktar) && miktar > 0)) {
    	            System.out.println("Geçersiz miktar!");
    	            return false;
    	        }
        double islemUcreti = 15.0; 
        double toplamTutar = miktar + islemUcreti;

        if (this.bakiye >= toplamTutar) {
            this.bakiye -= toplamTutar;
            System.out.println("Vadeli hesaptan para çekildi. İşlem Ücreti: " + islemUcreti + " TL");
            System.out.println("Kalan Bakiye: " + this.bakiye + " TL");
              kaydetIslem(new Transaction(
                    Transaction.Type.WITHDRAW,
                    getHesapNo(),
                    null,
                    miktar,
                    LocalDateTime.now(),
                    "Vadeli çekim (ücret: " + islemUcreti + " TL)",

                    this.getBakiye()
            		  ));
            return true;
        } else {
            System.out.println("Yetersiz Bakiye (İşlem ücreti dahil)!");
            return false;
        }
    }
    
    /**
     * Vadeli hesabın bilgilerini string olarak döner.
     *
     * @return Hesap bilgileri
     */
    @Override
    public String toString() {
        return "Vadeli Hesap No: " + getHesapNo() + " | Faiz: %" + faizOrani + " | Bakiye: " + getBakiye() + " TL";
    }
}