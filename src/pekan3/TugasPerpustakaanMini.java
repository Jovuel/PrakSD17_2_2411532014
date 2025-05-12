package pekan3;
import java.util.Scanner;
import java.util.Stack;

class Buku {
	String judul;
	Buku(String judul) {
		this.judul = judul;
	}
}

class TugasPerpustakaanMini {
	Stack<Buku> tumpukanBuku = new Stack<>();
	
	void tambahBuku(String judul) {
		tumpukanBuku.add(new Buku(judul));
	}
	
	void ambilBuku() {
		if(!tumpukanBuku.isEmpty()) {
			Buku diambil = tumpukanBuku.pop();
			System.out.println("Buku yang diambil adalah: "+diambil.judul);
		} else {
			System.out.println("Tumpukannya kosong, belum ada buku.");
		}
	}
	
	void lihatTumpukan() {
		if(!tumpukanBuku.isEmpty()) {
			System.out.println("Tumpukan buku saat ini: ");
			for(int i = tumpukanBuku.size() - 1; i >= 0; i--) {
				System.out.println("- " + tumpukanBuku.get(i).judul);
			}
		} else {
			System.out.println("Buku di perpustakaan lagi kosong.");
		}
	}
	
	void cariBuku(String judul) {
		boolean ketemu = false;
		for(Buku b : tumpukanBuku) {
			if(b.judul.equalsIgnoreCase(judul)) {
				System.out.println("Ketemu! Ini bukunya: " + judul);
				ketemu = true;
				break;
			} 
		}
		
		if(!ketemu) {
			System.out.println("Tidak ketemu bukunya di tumpukan.");
		}
	}
	
	void tambahBukuAwal() {
		tambahBuku("Algoritma Dasar");
		tambahBuku("Struktur Data");
		tambahBuku("Basis Data");
		tambahBuku("Pemrograman Java");
		tambahBuku("Jaringan Komputer");
		tambahBuku("Sistem Operasi");
	}
	
	public static void main(String[] args) {
		TugasPerpustakaanMini perpus = new TugasPerpustakaanMini();
		perpus.tambahBukuAwal(); //Otomatis menambahkan beberapa buku di awal program.
		Scanner scanner = new Scanner(System.in);
		boolean status = true;
		
		while(status) {
			System.out.println("=== MENU PERPUSTAKAAN MINI ===\n1. Menambahkan buku ke tumpukan.\n2. Ambil buku di posisi teratas.\n3. Lihat tumpukan buku.\n4. Cari buku.\n5. Keluar.");
			System.out.print("Masukkan pilihan: ");
			
			if(scanner.hasNextInt()) {
			int pilihan = scanner.nextInt();
			scanner.nextLine();
			
			switch(pilihan) {
			case 1:
				System.out.print("Masukkan judul buku: ");
				String judul = scanner.nextLine();
				perpus.tambahBuku(judul);
				break;
			case 2:
				perpus.ambilBuku();
				break;
			case 3:
				perpus.lihatTumpukan();
				break;
			case 4:
				System.out.print("Masukkan judul buku yang dicari: ");
				String judulCari = scanner.nextLine();
				perpus.cariBuku(judulCari);
				break;
			case 5:
				System.out.println("Leave now.");
				status = false;
				break;
			default:
				System.out.println("Pilihan kamu out of the box, gak ada di menu.");
				break;
			}
			} else {
				System.err.println("Silahkan pilih sesuai menu.");
				scanner.nextLine();
			}
		}
		scanner.close();
	}
}