package com.bankacilik;

/**
 * Banka müşterisi sınıfı.
 * Müşteri bilgileri: TCKN, ad soyad ve şifre.
 * Bu sınıf, müşteri verilerini saklamak ve güvenli bir şekilde erişim sağlamak için tasarlanmıştır.
 */
public class Musteri {
    private final String tckn;	// Müşterinin TCKN'si, değiştirilemez
    private String adSoyad;		// Müşterinin adı ve soyadı
    private String sifre;		// Müşterinin şifresi

    /**
     * Yeni müşteri oluşturur.
     *
     * @param tckn Müşterinin TCKN'si (boş olamaz)
     * @param adSoyad Müşterinin adı ve soyadı (boş olamaz)
     * @param sifre Müşterinin şifresi (boş olamaz)
     * @throws IllegalArgumentException geçersiz değer girildiyse
     */

    public Musteri(String tckn, String adSoyad, String sifre) {
        if (tckn == null || tckn.isBlank()) throw new IllegalArgumentException("TCKN boş olamaz");
        if (adSoyad == null || adSoyad.isBlank()) throw new IllegalArgumentException("Ad soyad boş olamaz");
        if (sifre == null || sifre.isBlank()) throw new IllegalArgumentException("Şifre boş olamaz");
        this.tckn = tckn;
        this.adSoyad = adSoyad;
        this.sifre = sifre;
    }

    /** TCKN getter (değiştirilemez) */
    public String getTckn() { return tckn; }
    
    /** Ad soyad getter */
    public String getAdSoyad() { return adSoyad; }
    
    /** Ad soyad setter */
    public void setAdSoyad(String adSoyad) {
        if (adSoyad == null || adSoyad.isBlank()) throw new IllegalArgumentException("Ad soyad boş olamaz");
        this.adSoyad = adSoyad;
    }

    /** Şifre getter */
    public String getSifre() { return sifre; }
    
    /** Şifre setter */
    public void setSifre(String sifre) {
        if (sifre == null || sifre.isBlank()) throw new IllegalArgumentException("Şifre boş olamaz");
        this.sifre = sifre;
    }
    
    /** Müşteri bilgisini okunabilir string olarak döndürür */
    @Override
    public String toString() {
        return "Musteri{tckn='%s', adSoyad='%s'}".formatted(tckn, adSoyad);
    }
}
