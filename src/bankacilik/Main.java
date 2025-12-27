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

    private static void giris(Scanner sc, BankSistemi bank) {
        System.out.println("\n— Kullanıcı Girişi —");
        System.out.print("TCKN: ");
        String tckn = sc.nextLine().trim();
        System.out.print("Şifre: ");
        String sifre = sc.nextLine();

        Musteri m = bank.musteriBulTckn(tckn);
        if (m != null && m.getSifre().equals(sifre)) {
            System.out.println("Giriş başarılı. Hoş geldiniz, " + m.getAdSoyad() + "!");
        } else {
            System.out.println("Giriş başarısız. (TCKN/Şifre hatalı)");
        }
    }

    private static void kayit(Scanner sc, BankSistemi bank) {
        System.out.println("\n— Yeni Kullanıcı Kaydı —");
        System.out.print("TCKN (11 hane): ");
        String tckn = sc.nextLine().trim();
        System.out.print("Ad Soyad: ");
        String adSoyad = sc.nextLine();
        System.out.print("Şifre (min 4 karakter): ");
        String sifre = sc.nextLine();

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
}
