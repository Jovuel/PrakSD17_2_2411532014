package pekan5;
import java.util.Scanner;

class Pasien {
    int noAntrian;
    String nama;
    String keluhan;
    Pasien next;

    public Pasien(int noAntrian, String nama, String keluhan) {
        this.noAntrian = noAntrian;
        this.nama = nama;
        this.keluhan = keluhan;
        this.next = null;
    }
}

class AntrianPasien {
    private Pasien head;

    public void tambahPasien(int noAntrian, String nama, String keluhan) {
        Pasien baru = new Pasien(noAntrian, nama, keluhan);
        if (head == null) {
            head = baru;
        } else {
            Pasien temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = baru;
        }
        System.out.println("Data pasien berhasil ditambahkan!");
    }

    public void tampilkanAntrian() {
        if (isEmpty()) {
            System.out.println("Antrian kosong.");
            return;
        }
        System.out.println("--- Daftar Antrian Pasien ---");
        Pasien temp = head;
        int i = 1;
        while (temp != null) {
            System.out.println(i + ". [" + temp.noAntrian + "] " + temp.nama + " - " + temp.keluhan);
            temp = temp.next;
            i++;
        }
    }

    public void hapusPasienPertama() {
        if (isEmpty()) {
            System.out.println("Antrian kosong, tidak ada pasien yang bisa dilayani.");
            return;
        }
        System.out.println("Pasien dengan nama " + head.nama + " telah dilayani.");
        head = head.next;
    }

    public void cariPasien(String nama) {
        Pasien temp = head;
        while (temp != null) {
            if (temp.nama.equalsIgnoreCase(nama)) {
                System.out.println("Pasien ditemukan: [" + temp.noAntrian + "] " + temp.nama + " - " + temp.keluhan);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Pasien dengan nama " + nama + " tidak ditemukan dalam antrian.");
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int hitungPasien() {
        int count = 0;
        Pasien temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}

public class TugasPraktikumPekan5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AntrianPasien antrian = new AntrianPasien();

        while (true) {
            System.out.println("\n== SISTEM ANTRIAN PASIEN KLINIK ==");
            System.out.println("1. Tambah Pasien");
            System.out.println("2. Tampilkan Antrian");
            System.out.println("3. Layani Pasien");
            System.out.println("4. Cari Pasien");
            System.out.println("5. Jumlah Pasien");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Membuang newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Nomor Antrian: ");
                    int noAntrian = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan Nama Pasien: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Keluhan: ");
                    String keluhan = scanner.nextLine();
                    antrian.tambahPasien(noAntrian, nama, keluhan);
                    break;
                case 2:
                    antrian.tampilkanAntrian();
                    break;
                case 3:
                    antrian.hapusPasienPertama();
                    break;
                case 4:
                    System.out.print("Masukkan nama pasien yang dicari: ");
                    String cariNama = scanner.nextLine();
                    antrian.cariPasien(cariNama);
                    break;
                case 5:
                    System.out.println("Jumlah pasien saat ini: " + antrian.hitungPasien());
                    break;
                case 6:
                    System.out.println("Terima kasih telah menggunakan sistem antrian pasien!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }
}
