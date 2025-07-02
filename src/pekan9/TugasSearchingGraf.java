package pekan9;

import java.util.*;

public class TugasSearchingGraf {
	private Map<String, List<String>> adjacencyList;
	
	private void addEdge(String a, String b) { // Logika method tambah tepian graf
		adjacencyList.computeIfAbsent(a, z -> new ArrayList<>()).add(a);
		adjacencyList.computeIfAbsent(b, z -> new ArrayList<>()).add(b);
	}
	
	public TugasSearchingGraf() {
		adjacencyList = new HashMap<>();
		addEdge("A", "B"); // Membuat graf dengan menambahkan tepiannya dulu
        addEdge("A", "C");
        addEdge("B", "D");
        addEdge("C", "D");
        addEdge("C", "E");
        addEdge("D", "F");
        addEdge("E", "F");
        addEdge("E", "H");
        addEdge("F", "G");
        addEdge("H", "G");
	}
	
	public void BFSearch(String nodeAwal, String nodeTujuan) {
		System.out.println("Nama: Jovantri Immanuel Gulo");
		System.out.println("NIM : 2411532014");
		System.out.println();
		System.out.println();
		System.out.println("Node awal: " + nodeAwal);
		System.out.println("Node tujuan: " + nodeTujuan);
		System.out.println("Menggunakan Algoritma : BFS");
		System.out.println();
		System.out.println();
		
		Queue<String> antrian = new LinkedList<>();
		Set<String> sudahDikunjungi = new HashSet<>();
		Map<String, String> parentMap = new HashMap<>();
		
		int langkah = 1;
		
		antrian.offer(nodeAwal);
		sudahDikunjungi.add(nodeAwal);
		parentMap.put(nodeAwal, null); // Karena pada node awal, tidak ada parentnya.
		
		boolean tujuanDitemukan = false;
		String nodeSekarang = null;
		
		while(!antrian.isEmpty()) {
			nodeSekarang = antrian.poll();
			System.out.println("Langkah " + langkah++ + ": Kunjungi " + nodeSekarang);
			if(nodeSekarang.equals(nodeTujuan)) {
				tujuanDitemukan = true;
				System.out.println("Yay, tujuan " + nodeTujuan + " telah ditemukan!");
				break;
			}
			List<String> tetangga = adjacencyList.getOrDefault(nodeSekarang, Collections.emptyList());
			Collections.sort(tetangga);
			
			for(String neighbor : tetangga) {
				if(!sudahDikunjungi.contains(tetangga)) {
					sudahDikunjungi.add(neighbor);
					parentMap.put(neighbor, nodeSekarang);
					antrian.offer(neighbor);
				}
			}
		}
		if(tujuanDitemukan) {
			List<String> jalur = new LinkedList<>();
			String jalurNode = nodeTujuan;
			while(jalurNode != null) {
				jalur.add(0, jalurNode);
				jalurNode = parentMap.get(jalurNode);
			}
			System.out.print("Rute: ");
			for(int x = 0; x < jalur.size(); x++) {
				System.out.print(jalur.get(x));
				if(x < jalur.size() - 1) {
					System.out.print("->");
				}
			}
			System.out.println();
		} else {
			System.out.println("Tujuan " + nodeTujuan + " tidak ditemukan dari " + nodeAwal);
		}
	}
	
	public static void main(String[] args) {
		TugasSearchingGraf graf = new TugasSearchingGraf();
		graf.BFSearch("A", "G");
	}
	
}