package pekan8;

// Nama : Jovantri Immanuel Gulo
// NIM : 2411532014

import java.util.Arrays;

public class TugasSortingLanjutan {
	
	public static void metodePengurutanInsertion(int[] arr) {
		int panjang = arr.length;
		int langkah = 1;
		
		for(int i = 1; i < panjang; i++) {
			int kunci = arr[i];
			int j = i-1;
			while(j >= 0 && arr[j] < kunci) {
				arr[j+1] = arr[j];
				j = j-1;
			}
			arr[j+1] = kunci;
			System.out.println("Langkah " + langkah + ": " + Arrays.toString(arr));
			langkah++;
		}
	}
	
	public static void main(String[] args) {
		int[] listBilanganPrima = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
		System.out.println("Deret awalnya: " + Arrays.toString(listBilanganPrima));
		System.out.println("Algoritmanya : Insertion Sort (BP 14)");
		System.out.println("");
		metodePengurutanInsertion(listBilanganPrima);
		System.out.println();
		System.out.println("Hasilnya : " + Arrays.toString(listBilanganPrima));
	}
}