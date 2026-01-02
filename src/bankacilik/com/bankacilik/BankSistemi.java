package com.bankacilik;

public class BankSistemi {
    private final Musteri[] musteriler = new Musteri[500];
    private int count = 0;
    private final Hesap[] hesaplar = new Hesap[1000];
    private final String[] hesapSahibiTckn = new String[1000];
    private int hesapCount = 0;
    private static int NEXT_HESAP_NO = 100000;
    private static synchronized int yeniHesapNo() {
        return ++NEXT_HESAP_NO;
    }

    public boolean musteriEkle(Musteri m) {
        if (m == null || count >= musteriler.length) return false;
        if (musteriBulTckn(m.getTckn()) != null) return false;
        musteriler[count++] = m;
        return true;
    }

    public Musteri musteriBulTckn(String tckn) {
        for (int i = 0; i < count; i++) {
            if (musteriler[i].getTckn().equals(tckn)) return musteriler[i];
        }
        return null;
    }
    public Hesap hesapBulByTckn(String tckn) {
        for (int i = 0; i < hesapCount; i++) {
            if (hesapSahibiTckn[i].equals(tckn)) return hesaplar[i];
        }
        return null;
    }
    public Hesap hesapAcVadesiz(String tckn) {
        if (musteriBulTckn(tckn) == null) throw new IllegalArgumentException("TCKN kayıtlı değil!");
        if (hesapBulByTckn(tckn) != null) throw new IllegalStateException("Bu TCKN için zaten hesap var!");
        if (hesapCount >= hesaplar.length) throw new IllegalStateException("Hesap kapasitesi dolu!");

        int no = yeniHesapNo();
        Hesap h = new VadesizHesap(no, 0.0);
        hesaplar[hesapCount] = h;
        hesapSahibiTckn[hesapCount] = tckn;
        hesapCount++;
        return h;
    }
    
    public Hesap hesapAcVadeli(String tckn, double faizOrani) {
        if (musteriBulTckn(tckn) == null) throw new IllegalArgumentException("TCKN kayıtlı değil!");
        if (hesapBulByTckn(tckn) != null) throw new IllegalStateException("Bu TCKN için zaten hesap var!");
        if (hesapCount >= hesaplar.length) throw new IllegalStateException("Hesap kapasitesi dolu!");

        int no = yeniHesapNo();
        Hesap h = new VadeliHesap(no, 0.0, faizOrani);
        hesaplar[hesapCount] = h;
        hesapSahibiTckn[hesapCount] = tckn;
        hesapCount++;
        return h;
    }
}
