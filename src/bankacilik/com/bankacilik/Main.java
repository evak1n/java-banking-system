package com.bankacilik;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankSistemi bank = new BankSistemi();
        Scanner sec = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== ANA MENÜ ===");
            System.out.println("[1] Kullanıcı Girişi");
            System.out.println("[2] Yeni Kullanıcı Kaydı");
            System.out.println("[0] Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = sec.nextLine().trim();

            switch (secim) {
                case "1" -> giris(sec, bank);
                case "2" -> kayit(sec, bank);
                case "0" -> { System.out.println("Çıkış yapılıyor..."); sec.close(); return; }
                default -> System.out.println("Geçersiz seçim!");
            }
        }
    }

    private static void giris(Scanner sec, BankSistemi bank) {
        System.out.println("\n— Kullanıcı Girişi —");
        System.out.print("TCKN: ");
        String tckn = sec.nextLine().trim();
        System.out.print("Şifre: ");
        String sifre = sec.nextLine();

        Musteri m = bank.musteriBulTckn(tckn);
        if (m != null && m.getSifre().equals(sifre)) {
            System.out.println("Giriş başarılı. Hoş geldiniz, " + m.getAdSoyad() + "!");
            islemMenusu(sec, bank, m);
        } else {
            System.out.println("Giriş başarısız. (TCKN/Şifre hatalı)");
        }
    }

    private static void kayit(Scanner sec, BankSistemi bank) {
        System.out.println("\n— Yeni Kullanıcı Kaydı —");
        System.out.print("TCKN (11 hane): ");
        String tckn = sec.nextLine().trim();
        System.out.print("Ad Soyad: ");
        String adSoyad = sec.nextLine();
        System.out.print("Şifre (min 4 karakter): ");
        String sifre = sec.nextLine();

        boolean valid =
            tckn.length() == 11 && tckn.chars().allMatch(Character::isDigit) &&
            !adSoyad.isBlank() &&
            sifre.length() >= 4;

        if (!valid) { System.out.println("Kayıt başarısız (geçersiz alanlar)."); return; }
        if (bank.musteriBulTckn(tckn) != null) {
            System.out.println("Kayıt başarısız (TCKN zaten kayıtlı)."); return;
        }

        boolean ok = bank.musteriEkle(new Musteri(tckn, adSoyad, sifre));
        System.out.println(ok ? "Kayıt başarılı." : "Kayıt başarısız (kapasite dolu olabilir).");
    }

private static void islemMenusu(Scanner sec, BankSistemi bank, Musteri m) {
	Hesap aktifHesap = bank.hesapBulByTckn(m.getTckn());

    while (true) {
        System.out.println("\n=== İŞLEM MENÜSÜ ===");
        System.out.println("[0] Hesap Aç");
        System.out.println("[1] Bakiye Görüntüle");
        System.out.println("[2] Para Yatır");
        System.out.println("[3] Para Çek");
        System.out.println("[9] Oturumdan Çık");
        System.out.print("Seçiminiz: ");
        String secim = sec.nextLine().trim();

        switch (secim) {
            case "0" -> {
                if (aktifHesap == null) {
                	try {
                		aktifHesap = bank.hesapAcVadesiz(m.getTckn());
                            System.out.println("Hesap açıldı. Hesap No: " + aktifHesap.getHesapNo());
                        } catch (Exception e) {
                            System.out.println("Hesap açma hatası: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Zaten bir hesabınız var.");
                    }
                }

            case "1" -> {
                if (aktifHesap == null) { System.out.println("Önce hesap açın."); break; }
                System.out.println("Bakiye: " + aktifHesap.getBakiye() + " TL");
            }
            case "2" -> {
                if (aktifHesap == null) { System.out.println("Önce hesap açın."); break; }
                System.out.print("Yatırılacak miktar: ");
                String giris = sec.nextLine();
                try {
                    double miktar = Double.parseDouble(giris);
                    if (miktar > 0) {
                        aktifHesap.paraYatir(miktar);
                    } else {
                        System.out.println("Geçersiz miktar!");
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Lütfen geçerli bir sayı girin.");
                }
            }
            case "3" -> {
                if (aktifHesap == null) { System.out.println("Önce hesap açın."); break; }
                System.out.print("Çekilecek miktar: ");
                String giris = sec.nextLine();

                try {
                    double miktar = Double.parseDouble(giris);
                    aktifHesap.paraCek(miktar);
                } catch (NumberFormatException nfe) {
                    System.out.println("Lütfen geçerli bir sayı girin.");
                }
            }
            case "9" -> {
                System.out.println("Ana menüye dönülüyor...");
                return;
            }
            default -> System.out.println("Geçersiz seçim!");
        }
    }
}
}

