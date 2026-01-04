
package com.bankacilik;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_IN,
        TRANSFER_OUT
    }

    private final Type type;
    private final Integer kaynakHesapNo;
    private final Integer hedefHesapNo;
    private final double miktar;
    private final LocalDateTime zaman;
    private final String aciklama;
    private final double bakiyeSon;

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
