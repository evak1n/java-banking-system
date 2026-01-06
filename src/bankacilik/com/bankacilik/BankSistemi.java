	package com.bankacilik;
	
	/**
	 * BankSistemi sınıfı, müşterileri ve hesapları yönetmek için kullanılır.
	 * Hesap açma, transfer ve hareketleri yazdırma gibi işlemleri sağlar.
	 */
	
	public class BankSistemi {
		
		 /** Maksimum müşteri sayısı */
	    private final Musteri[] musteriler = new Musteri[500];
	    
	    /** Şu ana kadar eklenen müşteri sayısı */
	    private int count = 0;

	    /** Maksimum hesap sayısı */
	    private final Hesap[] hesaplar = new Hesap[1000];
	    
	    /** Hesapların sahiplerinin TCKN bilgileri */
	    private final String[] hesapSahibiTckn = new String[1000];
	    
	    /** Şu ana kadar eklenen hesap sayısı */
	    private int hesapCount = 0;
	    
	    /** Hesap numarası için kullanılan static sayaç */
	    private static int NEXT_HESAP_NO = 100000;
	    /**
	     * Yeni hesap numarası oluşturur ve senkronize edilmiştir.
	     * @return int yeni hesap numarası
	     */
	    private static synchronized int yeniHesapNo() {
	        return ++NEXT_HESAP_NO;
	    }
	    /**
	     * Yeni müşteri ekler.
	     * @param m Eklenecek Musteri nesnesi
	     * @return boolean ekleme başarılı ise true, başarısız ise false
	     */
	    public boolean musteriEkle(Musteri m) {
	        if (m == null || count >= musteriler.length) return false;
	        if (musteriBulTckn(m.getTckn()) != null) return false;
	        musteriler[count++] = m;
	        return true;
	    }
	    /**
	     * TCKN'ye göre müşteri bulur.
	     * @param tckn Aranan TCKN
	     * @return Musteri nesnesi, bulunamazsa null
	     */
	
	    public Musteri musteriBulTckn(String tckn) {
	        for (int i = 0; i < count; i++) {
	            if (musteriler[i].getTckn().equals(tckn)) return musteriler[i];
	        }
	        return null;
	    }
	    
	    /**
	     * TCKN'ye göre hesabı bulur.
	     * @param tckn Hesap sahibinin TCKN'si
	     * @return Hesap nesnesi, bulunamazsa null
	     */
	    
	    public Hesap hesapBulByTckn(String tckn) {
	        for (int i = 0; i < hesapCount; i++) {
	            if (hesapSahibiTckn[i].equals(tckn)) return hesaplar[i];
	        }
	        return null;
	    }
	    /**
	     * Vadesiz hesap açar.
	     * @param tckn Hesap sahibi TCKN'si
	     * @return Açılan Hesap nesnesi
	     * @throws IllegalArgumentException TCKN kayıtlı değilse
	     * @throws IllegalStateException Hesap zaten varsa veya kapasite doluysa
	     */
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
	    /**
	     * Vadeli hesap açar.
	     * @param tckn Hesap sahibi TCKN'si
	     * @param faizOrani Vadeli hesap faiz oranı
	     * @return Açılan VadeliHesap nesnesi
	     * @throws IllegalArgumentException TCKN kayıtlı değilse
	     * @throws IllegalStateException Hesap zaten varsa veya kapasite doluysa
	     */
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
	    /**
	     * Hesaplar arasında para transferi yapar.
	     * @param fromNo Gönderen hesap numarası
	     * @param toNo Alıcı hesap numarası
	     * @param miktar Transfer miktarı
	     * @param aciklama Transfer açıklaması
	     * @throws IllegalArgumentException Hesap bulunamazsa
	     */
	 public void transferByNo(int fromNo, int toNo, double miktar, String aciklama) {
	     Hesap from = null, to = null;
	     for (int i = 0; i < hesapCount; i++) {
	         if (hesaplar[i].getHesapNo() == fromNo) from = hesaplar[i];
	         if (hesaplar[i].getHesapNo() == toNo) to = hesaplar[i];
	     }
	     if (from == null || to == null) throw new IllegalArgumentException("Hesap bulunamadı!");
	     from.transferTo(to, miktar, aciklama);
	 }
	 /**
	     * Belirli bir hesabın hareketlerini ekrana yazdırır.
	     * @param hesapNo Hesap numarası
	     */
	 public void hareketleriYazdir(int hesapNo) {
	     Hesap h = null;
	     for (int i = 0; i < hesapCount; i++) {
	         if (hesaplar[i].getHesapNo() == hesapNo) {
	             h = hesaplar[i];
	             break;
	         }
	     }
	     if (h != null) {
	         h.getHareketler().forEach(System.out::println);
	     } else {
	         System.out.println("Hesap bulunamadı!");
	     }
	 }

	}
