package pekan6;

import java.util.Scanner;

class ItemBelanja {
    String nama;
    int kuantitas;
    String kategori;

    ItemBelanja(String nama, int kuantitas, String kategori) {
        this.nama = nama;
        this.kuantitas = kuantitas;
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return nama + " (" + kuantitas + ") [" + kategori + "]";
    }
}

class Node {
    ItemBelanja item;
    Node next, prev;

    Node(ItemBelanja item) {
        this.item = item;
        this.next = null;
        this.prev = null;
    }
}

class DaftarBelanja {
    Node head, tail;

    void tambahItem(String nama, int kuantitas, String kategori) {
        ItemBelanja newItem = new ItemBelanja(nama, kuantitas, kategori);
        Node newNode = new Node(newItem);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        System.out.println("Item berhasil ditambahkan!");
    }

    void hapusItem(String nama) {
        Node temp = head;
        while (temp != null) {
            if (temp.item.nama.equalsIgnoreCase(nama)) {
                if (temp.prev != null) {
                    temp.prev.next = temp.next;
                } else {
                    head = temp.next;
                }
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                } else {
                    tail = temp.prev;
                }
                System.out.println("Item berhasil dihapus.");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Item tidak ditemukan.");
    }

    void tampilkanSemuaItem() {
        if (head == null) {
            System.out.println("Daftar belanja kosong.");
            return;
        }
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.item);
            temp = temp.next;
        }
    }

    void tampilkanPerKategori(String kategori) {
        Node temp = head;
        boolean ditemukan = false;
        System.out.println("Item dalam kategori '" + kategori + "' --");
        while (temp != null) {
            if (temp.item.kategori.equalsIgnoreCase(kategori)) {
                System.out.println("- " + temp.item);
                ditemukan = true;
            }
            temp = temp.next;
        }
        if (!ditemukan) {
            System.out.println("Tidak ada item dalam kategori ini.");
        }
    }

    void cariItem(String nama) {
        Node temp = head;
        while (temp != null) {
            if (temp.item.nama.equalsIgnoreCase(nama)) {
                System.out.println("Item ditemukan: " + temp.item);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Item tidak ditemukan.");
    }
}

public class TugasPraktikumPekan6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DaftarBelanja daftar = new DaftarBelanja();
        int pilihan;

        do {
            System.out.println("\n== MENU DAFTAR BELANJA ==");
            System.out.println("1. Tambah Item");
            System.out.println("2. Hapus Item");
            System.out.println("3. Tampilkan Semua Item");
            System.out.println("4. Tampilkan Item Berdasarkan Kategori");
            System.out.println("5. Cari Item");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan nama item: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan kuantitas: ");
                    int kuantitas = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan kategori: ");
                    String kategori = scanner.nextLine();
                    daftar.tambahItem(nama, kuantitas, kategori);
                    break;
                case 2:
                    System.out.print("Masukkan nama item yang ingin dihapus: ");
                    nama = scanner.nextLine();
                    daftar.hapusItem(nama);
                    break;
                case 3:
                    daftar.tampilkanSemuaItem();
                    break;
                case 4:
                    System.out.print("Masukkan kategori: ");
                    kategori = scanner.nextLine();
                    daftar.tampilkanPerKategori(kategori);
                    break;
                case 5:
                    System.out.print("Masukkan nama item yang dicari: ");
                    nama = scanner.nextLine();
                    daftar.cariItem(nama);
                    break;
                case 6:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        } while (pilihan != 6);

        scanner.close();
    }
}
