package pekan7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class InsertionSortGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private int[] array;
	private JLabel[] labelArray;
	private JButton stepButton, resetButton, setButton;
	private JTextField inputField;
	private JPanel panelArray;
	private JTextArea stepArea;
	
	private int i = 1, j; // i adalah indeks elemen yang akan disisipkan
	private boolean sorting = false;
	private int stepCount = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertionSortGUI frame = new InsertionSortGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InsertionSortGUI() {
		setTitle("Insertion Sort Langkah per Langkah");
		setSize(750, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputField = new JTextField(30);
		setButton = new JButton("Set Array");
		inputPanel.add(new JLabel("Masukkan angka (pisahkan dengan koma) :"));
		inputPanel.add(inputField);
		inputPanel.add(setButton);
		
		panelArray = new JPanel();
		panelArray.setLayout(new FlowLayout());
		
		JPanel controlPanel = new JPanel();
		stepButton = new JButton("Langkah Selanjutnya");
		resetButton = new JButton("Reset");
		stepButton.setEnabled(false);
		controlPanel.add(stepButton);
		controlPanel.add(resetButton);
		
		stepArea = new JTextArea(8, 60);
		stepArea.setEditable(false);
		stepArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(stepArea);
		
		add(inputPanel, BorderLayout.NORTH);
		add(panelArray, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.EAST);
		
		// Event set Array
		setButton.addActionListener(e -> setArrayFromInput());
		
		// Event langkah selanjutnya
		stepButton.addActionListener(e -> performStep());
		
		//Event reset
		resetButton.addActionListener(e -> reset());
	}

	private void performStep() {
		if(i < array.length && sorting) {
			// PERBAIKAN: Ambil elemen pada indeks i sebagai key
			int key = array[i]; 
			j = i -1;
			
			StringBuilder stepLog = new StringBuilder();
			// Menambahkan informasi elemen yang sedang di-highlight atau menjadi 'key'
            stepLog.append("Langkah ").append(stepCount).append(": Key = ").append(key).append(" (elemen ke-").append(i + 1).append(")\n");
            stepLog.append("   Membandingkan ").append(key).append(" dengan elemen di sebelah kirinya.\n");


			// Visualisasi: Highlight elemen 'key'
            if (labelArray != null && i < labelArray.length) {
                // Reset warna label sebelumnya
                for (JLabel lbl : labelArray) {
                    lbl.setOpaque(false); // Pastikan bisa diwarnai
                    lbl.setBackground(null); // Warna default
                    lbl.setForeground(Color.BLACK); // Warna teks default
                }
                labelArray[i].setOpaque(true);
                labelArray[i].setBackground(Color.YELLOW); // Warna highlight untuk key
            }
			
			int originalJ = j; // Simpan nilai j awal untuk logging
			boolean shifted = false;

			while(j >= 0 && array[j] > key) {
				stepLog.append("   -> Geser ").append(array[j]).append(" (di posisi ").append(j+1).append(") ke kanan (posisi ").append(j+2).append(")\n");
				array[j + 1] = array[j];
				// Visualisasi pergeseran
                if (labelArray != null && (j + 1) < labelArray.length) {
                    labelArray[j + 1].setOpaque(true);
                    labelArray[j + 1].setBackground(Color.ORANGE); // Warna untuk elemen yang digeser
                }
				j--;
				shifted = true;
			}
			array[j + 1] = key;
			
            if (shifted) {
                stepLog.append("   Sisipkan ").append(key).append(" pada posisi ").append(j + 2).append(".\n");
            } else if (originalJ >=0) {
                 stepLog.append("   ").append(key).append(" sudah pada posisi yang benar atau lebih kecil dari ").append(array[originalJ]).append(".\n");
            } else {
                 stepLog.append("   ").append(key).append(" adalah elemen terkecil sejauh ini, ditempatkan di awal.\n");
            }

			updateLabels(); // Update semua label setelah satu langkah selesai

            // Setelah updateLabels, kembalikan warna elemen yang sudah terurut
            for(int k_sorted = 0; k_sorted <= i; k_sorted++){
                if(labelArray != null && k_sorted < labelArray.length){
                    labelArray[k_sorted].setOpaque(true);
                    labelArray[k_sorted].setBackground(Color.LIGHT_GRAY); // Warna untuk bagian yang sudah terurut
                }
            }


			stepLog.append("Hasil sementara: ").append(arrayToString(array)).append("\n\n");
			stepArea.append(stepLog.toString());
			
			i++;
			stepCount++;
			
			if(i == array.length) {
				sorting = false;
				stepButton.setEnabled(false);
				// Reset warna semua label ke final sorted color
                for (JLabel lbl : labelArray) {
                    lbl.setOpaque(true);
                    lbl.setBackground(Color.GREEN); // Warna akhir setelah sorting selesai
                    lbl.setForeground(Color.BLACK);
                }
				JOptionPane.showMessageDialog(this, "Sorting Selesai!");
			}
		}
	}

	private void setArrayFromInput() {
		String text = inputField.getText().trim();
		if(text.isEmpty()){
            JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
		String[] parts = text.split(",");
        if (parts.length == 0 || (parts.length == 1 && parts[0].trim().isEmpty())) {
             JOptionPane.showMessageDialog(this, "Masukkan setidaknya satu angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
		array = new int[parts.length];
		
		try {
			for(int k = 0; k < parts.length ; k++) {
				array[k] = Integer.parseInt(parts[k].trim());
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Masukkan hanya angka yang dipisahkan dengan koma!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		i = 1; // Insertion sort dimulai dari elemen kedua (indeks 1)
		stepCount = 1;
		sorting = true;
		stepButton.setEnabled(true);
		stepArea.setText("Array awal: " + arrayToString(array) + "\n\n"); // Tampilkan array awal
		panelArray.removeAll();
		labelArray = new JLabel[array.length];
		for(int k = 0; k < array.length; k++) {
			labelArray[k] = new JLabel(String.valueOf(array[k]));
			labelArray[k].setFont(new Font("Arial", Font.BOLD, 24));
			labelArray[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			labelArray[k].setPreferredSize(new Dimension(50, 50));
			labelArray[k].setHorizontalAlignment(SwingConstants.CENTER);
            labelArray[k].setOpaque(true); // Agar background color terlihat
			panelArray.add(labelArray[k]);
		}
		// Highlight elemen pertama sebagai bagian yang "sudah terurut" secara default
        if (labelArray.length > 0) {
            labelArray[0].setBackground(Color.LIGHT_GRAY);
        }

		panelArray.revalidate();
		panelArray.repaint();
	}
	
	private void updateLabels() {
		for(int k = 0; k < array.length; k++) {
			labelArray[k].setText(String.valueOf(array[k]));
            // Reset warna dasar sebelum highlight langkah berikutnya, kecuali yang sudah terurut
            if (! (labelArray[k].getBackground() == Color.LIGHT_GRAY || labelArray[k].getBackground() == Color.GREEN) ) {
                 labelArray[k].setBackground(null);
                 labelArray[k].setOpaque(false);
            }
		}
	}
	
	private void reset() {
		inputField.setText("");
		panelArray.removeAll();
		panelArray.revalidate();
		panelArray.repaint();
		stepArea.setText("");
		stepButton.setEnabled(false);
		sorting = false;
		i = 1;
		stepCount = 1;
        array = null;
        labelArray = null;
	}
	
	private String arrayToString(int[] arr) {
        if (arr == null) return "";
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int k = 0; k < arr.length; k++) {
			sb.append(arr[k]);
			if(k < arr.length - 1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

}