package com.bankacilik;

public class Musteri {
    private final String tckn;
    private String adSoyad;
    private String sifre;

    public Musteri(String tckn, String adSoyad, String sifre) {
        if (tckn == null || tckn.isBlank()) throw new IllegalArgumentException("TCKN boş olamaz");
        if (adSoyad == null || adSoyad.isBlank()) throw new IllegalArgumentException("Ad soyad boş olamaz");
        if (sifre == null || sifre.isBlank()) throw new IllegalArgumentException("Şifre boş olamaz");
        this.tckn = tckn;
        this.adSoyad = adSoyad;
        this.sifre = sifre;
    }

    public String getTckn() { return tckn; }
    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) {
        if (adSoyad == null || adSoyad.isBlank()) throw new IllegalArgumentException("Ad soyad boş olamaz");
        this.adSoyad = adSoyad;
    }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) {
        if (sifre == null || sifre.isBlank()) throw new IllegalArgumentException("Şifre boş olamaz");
        this.sifre = sifre;
    }

    @Override
    public String toString() {
        return "Musteri{tckn='%s', adSoyad='%s'}".formatted(tckn, adSoyad);
    }
}
