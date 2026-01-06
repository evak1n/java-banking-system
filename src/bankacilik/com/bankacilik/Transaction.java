
package com.bankacilik;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Hesap üzerinde yapılan işlemleri temsil eden sınıf.
 * Her işlem; tür, kaynak/ hedef hesap numarası, miktar, zaman, açıklama ve işlem sonrası bakiye içerir.
 */
public class Transaction {
	
	/** İşlem türleri */
    public enum Type {
        DEPOSIT,       // Para yatırma
        WITHDRAW,      // Para çekme
        TRANSFER_IN,   // Hesaba gelen transfer
        TRANSFER_OUT   // Hesaptan giden transfer
    }

    
    private final Type type;               // İşlem türü
    private final Integer kaynakHesapNo;  // Kaynak hesap (transferlerde)
    private final Integer hedefHesapNo;   // Hedef hesap (transferlerde)
    private final double miktar;           // İşlem miktarı
    private final LocalDateTime zaman;     // İşlem zamanı
    private final String aciklama;         // İşlem açıklaması
    private final double bakiyeSon;        // İşlem sonrası bakiye

    /**
     * Yeni işlem kaydı oluşturur.
     *
     * @param type İşlem türü (DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT)
     * @param kaynakHesapNo Kaynak hesap numarası (yoksa null)
     * @param hedefHesapNo Hedef hesap numarası (yoksa null)
     * @param miktar İşlem miktarı
     * @param zaman İşlem zamanı
     * @param aciklama İşlem açıklaması
     * @param bakiyeSon İşlem sonrası bakiye
     */
    public Transaction(Type type,
                       Integer kaynakHesapNo,
                       Integer hedefHesapNo,
                       double miktar,
                       LocalDateTime zaman,
                       String aciklama,
                       double bakiyeSon) {
        this.type = type;
        this.kaynakHesapNo = kaynakHesapNo;
        this.hedefHesapNo = hedefHesapNo;
        this.miktar = miktar;
        this.zaman = zaman;
        this.aciklama = aciklama;
        this.bakiyeSon = bakiyeSon;
    }
    
    /**
     * İşlem bilgisini okunabilir string olarak döndürür.
     *
     * @return İşlem bilgisi string formatında
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[%s] %s | miktar=%.2f | kaynak=%s | hedef=%s | sonrası=%.2f%s"
                .formatted(
                        zaman.format(fmt),
                        type,
                        miktar,
                        kaynakHesapNo == null ? "-" : kaynakHesapNo,
                        hedefHesapNo == null ? "-" : hedefHesapNo,
                        bakiyeSon,
                        (aciklama == null || aciklama.isBlank()) ? "" : (" | " + aciklama)
                );
    }
}
