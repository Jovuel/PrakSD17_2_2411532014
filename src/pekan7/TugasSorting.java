package pekan7;

import java.util.Arrays;

public class TugasSorting {
	public static void InsertionSortJovan(char[] arr, int indexAwal, int indexAkhir) {
		if(indexAwal < 0 || indexAkhir >= arr.length || indexAwal >= indexAkhir) {
			System.out.println("Gak normal indexnya, coba ulangi lagi."); // Mengantisipasi index yang aneh-aneh wkwk
			return; // Program ngambek
		}
		
		int n = arr.length;
		for(int i = indexAwal + 1; i <= indexAkhir; i++) { // Supaya bs disesuaikan dengan keinginan
			char star = arr[i]; // Secara berulangan mengidentifikasi isi dari array character
			int j = i - 1; // Dimulai dari letak elemen yang sebelum key
			while(j >= indexAwal && arr[j] > star) { // Jikalau elemen sebelum kunci, lebih besar dari kunci, maka lakukan perulangan while (true)
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = star; // Selanjutnya, yang menjadi kunci adalah elemen berikutnya
		}
	}
	
	public static void main(String args[]) {
		char[] tugasSoalAlfabetTerbalik = {'Z', 'Y', 'X', 'W', 'V', 'U', 'T', 'S', 'R', 'Q', 'P', 'O', 'N', 'M', 'L', 'K', 'J', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
		System.out.println("Array sebelumnya: " + Arrays.toString(tugasSoalAlfabetTerbalik));
		InsertionSortJovan(tugasSoalAlfabetTerbalik, 12, 25); // Method yang sudah dibuat tadi, supaya bs kustom mengurutkan array di index tertentu
		System.out.println("Setelah diurutkan: " + Arrays.toString(tugasSoalAlfabetTerbalik)); // Metode Arrays.toString untuk menampilkan Array jadi String
		System.out.print("Sesuai format: ");
		for(int jo = 0; jo < tugasSoalAlfabetTerbalik.length; jo++) {
			System.out.print(tugasSoalAlfabetTerbalik[jo]);
			if(jo != tugasSoalAlfabetTerbalik.length - 1) { // Supaya strip "-" nya gak gantung di akhir :")
				System.out.print("-");
			}
		}
	}
	
	// Jovantri Immanuel Gulo
	// NIM 2411532014 
	// Mengurutkan alfabet terbalik dengan InsertionSort (Jalur SNBT)
	// Mengurutkan dari belakang (Jalur SNBT : GENAP)
	// NIM berakhiran 14 (14 karakter)
	
	// Meengurutkan alfabet terbalik dengan Insertion Sort dari belakang sebanyak 14 karakter
}