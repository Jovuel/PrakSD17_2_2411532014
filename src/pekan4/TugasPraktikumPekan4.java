package pekan4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Pelanggan {
	String idPelanggan;
	int jumlahPesanan;
	
	Pelanggan(String idPelanggan, int jumlahPesanan){
		this.idPelanggan = idPelanggan;
		this.jumlahPesanan = jumlahPesanan;
	}
	
	public String getId() {
		return idPelanggan;
	}
	
	public int getJumlahPesanan() {
		return jumlahPesanan;
	}
}

public class TugasPraktikumPekan4 {
	public static void main(String[] args) {
		Scanner baca = new Scanner(System.in);
		Queue<Pelanggan> antrian = new LinkedList<>();
		System.out.print("Input pesanan: ");
		int n = baca.nextInt();
		
		for(int i = 0; i < n; i++) {
			String id = baca.next();
			int pesanan = baca.nextInt();
			antrian.add(new Pelanggan(id, pesanan));
		}
		
		baca.close();
		
		int totalWaktu = 0;
		while(!antrian.isEmpty()) {
			Pelanggan sekarang = antrian.poll();
			totalWaktu = totalWaktu + sekarang.jumlahPesanan;
			System.out.println(sekarang.idPelanggan + " selesai dalam " + totalWaktu + " menit");
		}
	}
}