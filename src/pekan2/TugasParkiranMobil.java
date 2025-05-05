package pekan2;

import java.util.ArrayList;
import java.util.Scanner;

class Mobil {
	private String platNomor;
	
	public Mobil(String platNomor) {
		this.platNomor = platNomor;
	}
	
	public String getPlatNomor() {
		return platNomor;
	}
}

class Parkiran {
	private ArrayList<Mobil> daftarMobil = new ArrayList<>();
	public Parkiran() {
		String[] platAwal = {"BA1234AB","BM1221CD"};
		for(String plat : platAwal) {
			daftarMobil.add(new Mobil(plat));
		}
	}
	public void tambahMobil(String platNomor) {
		daftarMobil.add(new Mobil(platNomor));
		System.out.println("Mobil " + platNomor + " berhasil ditambahkan.");
	}
	
	public void keluarkanMobil(String platNomor) {
		boolean ditemukan = false;
		for(int i = 0; i < daftarMobil.size();i++) {
			if(daftarMobil.get(i).getPlatNomor().equalsIgnoreCase(platNomor)) {
				daftarMobil.remove(i);
				System.out.println("Mobil "+platNomor+" telah keluar.");
				ditemukan = true;
				break;
			}
		}
		if(!ditemukan) {
			System.out.println("Mobil dengan plat "+platNomor+" tidak ditemukan.");
		}
	}
	
	public void tampilkanParkiran() {
		if(daftarMobil.isEmpty()) {
			System.out.println("Parkiran sedang kosong.");
		} else {
			System.out.println("Daftar mobil di parkiran:");
			int urutan = 1;
			for(Mobil m : daftarMobil) {
				System.out.println(urutan++ + ". " +  m.getPlatNomor());
			}
		}
	}
	
	public void cariMobil(String platNomor) {
		boolean ditemukan = false;
		for (Mobil m : daftarMobil) {
			if(m.getPlatNomor().equalsIgnoreCase(platNomor)) {
				ditemukan = true;
				break;
			}
		}
		if(ditemukan) {
			System.out.println("Mobil dengan plat "+platNomor+" masih terparkir.");
		} else {
			System.out.println("Mobil dengan plat "+platNomor+" tidak ditemukan.");
		}
	}
}

public class TugasParkiranMobil {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Parkiran parkiran = new Parkiran();
		
		int pilihan;
		
		do {
			System.out.println("\n=== Menu Parkiran Mobil ===\n1. Tambah mobil ke parkiran.\n2. Keluarkan mobil dari parkiran.\n3. Tampilkan isi parkiran.\n4. Cari mobil di parkiran.\n5. Keluar.");
			System.out.print("Pilih opsi: ");
			
			while(!scanner.hasNextInt()) {
				System.out.print("Masukkan angka yang valid: ");
				scanner.next();
			}
			
			pilihan = scanner.nextInt();
			scanner.nextLine();
			
			switch(pilihan) {
			case 1:
				System.out.print("Masukkan plat mobil: ");
				String platMasuk = scanner.nextLine().toUpperCase();
				parkiran.tambahMobil(platMasuk);
				break;
			case 2:
				System.out.print("Masukkan plat mobil yang ingin keluar: ");
				String platKeluar = scanner.nextLine().toUpperCase();
				parkiran.keluarkanMobil(platKeluar);
				break;
			case 3:
				parkiran.tampilkanParkiran();
				break;
			case 4:
				System.out.print("Masukkan plat nomor mobil yang dicari: ");
				String platCari = scanner.nextLine().toUpperCase();
				parkiran.cariMobil(platCari);
				break;
			case 5:
				System.out.println("Terima kasih ya, karena telah menggunakan sistem parkiran. :)");
				break;
			default:
				System.out.println("Pilihan tidak ada di daftar. Tolong ulangi kembali.");
			}
		} while (pilihan != 5);
		
		scanner.close();
	}
}