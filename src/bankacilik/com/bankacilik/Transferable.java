
package com.bankacilik;

public interface Transferable {
    void transferTo(Hesap hedef, double miktar, String aciklama);

    default void transferTo(Hesap hedef, double miktar) {
        transferTo(hedef, miktar, null);
    }
}
