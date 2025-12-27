package com.bankacilik;

public class BankSistemi {
    private final Musteri[] musteriler = new Musteri[500];
    private int count = 0;

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
}