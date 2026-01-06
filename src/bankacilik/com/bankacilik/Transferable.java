
package com.bankacilik;

/**
 * Transfer işlemi yapılabilen hesaplar için arayüz.
 * 
 * <p>Her sınıf bu arayüzü uygularsa, başka bir hesaba para transferi yapabilir.</p>
 */
public interface Transferable {
	
    /**
     * Belirtilen hedef hesaba miktar ve isteğe bağlı açıklama ile transfer yapar.
     *
     * @param hedef Hedef hesap
     * @param miktar Transfer edilecek miktar
     * @param aciklama Transfer açıklaması (opsiyonel, null olabilir)
     */

    void transferTo(Hesap hedef, double miktar, String aciklama);
    
    /**
     * Belirtilen hedef hesaba sadece miktar ile transfer yapar.
     * Açıklama verilmezse varsayılan olarak null atanır.
     *
     * @param hedef Hedef hesap
     * @param miktar Transfer edilecek miktar
     */
    
    default void transferTo(Hesap hedef, double miktar) {
        transferTo(hedef, miktar, null);
    }
}
