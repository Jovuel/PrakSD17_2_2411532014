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

public class SelectionSortGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private int[] array;
    private JLabel[] labelArray;
    private JButton stepButton, resetButton, setButton;
    private JTextField inputField;
    private JPanel panelArray;
    private JTextArea stepArea;

    // Variabel untuk mengontrol state Selection Sort
    private int i;          // Indeks pass luar (posisi yang akan diisi elemen minimum)
    private int j;          // Indeks untuk memindai sisa array (inner loop)
    private int minIndex;   // Indeks dari elemen minimum yang ditemukan di bagian belum terurut
    private boolean sorting = false;
    private int stepCount = 1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectionSortGUI frame = new SelectionSortGUI();
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
    public SelectionSortGUI() {
        setTitle("Selection Sort Langkah per Langkah"); // Judul konsisten
        setSize(850, 450); // Sedikit diperbesar untuk JTextArea
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5)); // Memberi sedikit jarak antar komponen

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputField = new JTextField(25); // Ukuran disesuaikan
        setButton = new JButton("Set Array");
        inputPanel.add(new JLabel("Masukkan angka (pisahkan dengan koma):"));
        inputPanel.add(inputField);
        inputPanel.add(setButton);

        panelArray = new JPanel();
        panelArray.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Jarak antar label
        panelArray.setBorder(BorderFactory.createTitledBorder("Visualisasi Array"));


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepButton = new JButton("Langkah Selanjutnya");
        resetButton = new JButton("Reset");
        stepButton.setEnabled(false);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);

        stepArea = new JTextArea(10, 70); // Ukuran disesuaikan
        stepArea.setEditable(false);
        stepArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(stepArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Log Langkah"));


        add(inputPanel, BorderLayout.NORTH);
        add(panelArray, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        // Menggunakan panel tambahan untuk JTextArea agar layout lebih rapi
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(scrollPane, BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(eastPanel, BorderLayout.EAST);

        // Event set Array
        setButton.addActionListener(e -> setArrayFromInput());

        // Event langkah selanjutnya
        stepButton.addActionListener(e -> performStep());

        //Event reset
        resetButton.addActionListener(e -> reset());
    }

    private void performStep() {
        if (!sorting || array == null || array.length == 0) {
            return;
        }

        StringBuilder stepLog = new StringBuilder(); // Log dibuat baru untuk setiap klik "Langkah Selanjutnya"
        stepLog.append("Langkah ").append(stepCount).append(" (Pass ke-").append(i + 1).append("):\n");

        // Tahap 1: Memindai untuk menemukan elemen minimum dalam sisa array
        // Kondisi ini menangani satu langkah pemindaian (satu iterasi dari inner loop)
        if (j < array.length) {
            stepLog.append("   Membandingkan array[").append(j).append("] (").append(array[j]).append(") dengan kandidat minimum saat ini array[").append(minIndex).append("] (").append(array[minIndex]).append(").\n");
            if (array[j] < array[minIndex]) {
                minIndex = j; // Update minIndex jika ditemukan elemen yang lebih kecil
                stepLog.append("   -> Ditemukan minimum baru sementara di indeks ").append(minIndex).append(" (nilai ").append(array[minIndex]).append(").\n");
            }
            visualizeStep(j, minIndex, false); // Visualisasikan langkah pemindaian saat ini
            j++; // Pindah ke elemen berikutnya untuk dipindai di klik selanjutnya
        }
        // Tahap 2: Pemindaian untuk pass i saat ini selesai (j sudah mencapai akhir array)
        // Lakukan pertukaran jika perlu, dan siapkan untuk pass i berikutnya.
        else { // j >= array.length
            stepLog.append("   Selesai memindai untuk pass ke-").append(i + 1).append(". Minimum ditemukan di indeks ").append(minIndex).append(" (nilai ").append(array[minIndex]).append(").\n");
            if (minIndex != i) { // Jika minimum bukan elemen pertama dari bagian yang belum terurut
                stepLog.append("   Menukar array[").append(i).append("] (").append(array[i]).append(") dengan array[").append(minIndex).append("] (").append(array[minIndex]).append(").\n");
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            } else {
                stepLog.append("   Elemen array[").append(i).append("] (").append(array[i]).append(") sudah merupakan minimum di posisinya, tidak ada pertukaran.\n");
            }
            updateLabelsText(); // Update teks label setelah potensi pertukaran
            visualizeStep(-1, -1, true); // Visualisasi setelah swap, tandai elemen ke-i sebagai sorted

            i++; // Pindah ke pass berikutnya (elemen berikutnya yang akan diisi)

            if (i < array.length - 1) { // Jika masih ada pass berikutnya yang perlu dijalankan
                minIndex = i;    // Reset minIndex ke awal bagian yang belum terurut untuk pass baru
                j = i + 1;       // Reset j untuk memulai pemindaian dari elemen setelah i yang baru
                stepLog.append("   Memulai pass baru untuk posisi ke-").append(i+1).append(". Kandidat minimum awal: array[").append(minIndex).append("] (").append(array[minIndex]).append(").\n");
                visualizeStep(-1, minIndex, false); // Highlight minIndex awal untuk pass baru
            } else { // Sorting selesai (semua elemen kecuali mungkin yang terakhir sudah diurutkan)
                sorting = false;
                stepButton.setEnabled(false);
                stepLog.append("\nSorting Selesai!\n");
                visualizeStep(-1,-1,true); // Visualisasi final state (semua hijau)
                JOptionPane.showMessageDialog(this, "Sorting selesai!");
            }
        }

        stepLog.append("Array saat ini: ").append(arrayToString(array)).append("\n\n");
        stepArea.append(stepLog.toString()); // Tambahkan log dari langkah ini ke JTextArea
        stepCount++;
    }

    // Memperbarui teks pada label sesuai dengan array saat ini
    private void updateLabelsText() {
        if (labelArray == null || array == null) return;
        for (int k = 0; k < array.length; k++) {
            if (k < labelArray.length) { // Pastikan tidak out of bounds
                labelArray[k].setText(String.valueOf(array[k]));
            }
        }
    }

    // Mengatur warna background label untuk visualisasi
    private void visualizeStep(int currentScanIndex, int currentMinIndex, boolean passCompletedForI) {
        if (labelArray == null) return;

        for (int k = 0; k < labelArray.length; k++) {
            labelArray[k].setOpaque(true); // Penting agar background terlihat
            labelArray[k].setForeground(Color.BLACK); // Default warna teks

            if (k < i) { // Bagian yang sudah terurut (sebelum pass i saat ini)
                labelArray[k].setBackground(Color.GREEN);
            } else if (passCompletedForI && k == i -1 && i > 0) { // Elemen yang baru saja disortir di pass sebelumnya (i sudah di-increment)
                 labelArray[k].setBackground(Color.GREEN);
            }
            else { // Bagian yang belum terurut atau sedang diproses
                if (k == currentMinIndex) {
                    labelArray[k].setBackground(Color.YELLOW); // Kandidat minimum saat ini
                } else if (k == currentScanIndex) {
                    labelArray[k].setBackground(Color.ORANGE); // Elemen yang sedang dipindai/dibandingkan
                } else {
                    labelArray[k].setBackground(Color.WHITE); // Default untuk bagian belum terurut
                }
            }
        }
         // Jika sorting sudah selesai semua, pastikan semua hijau
        if (!sorting && array != null && array.length > 0) {
             for (JLabel label : labelArray) {
                label.setOpaque(true);
                label.setBackground(Color.GREEN);
                label.setForeground(Color.BLACK);
            }
        }
        panelArray.revalidate();
        panelArray.repaint();
    }


    private void setArrayFromInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = text.split(",");
        if (parts.length == 0 || (parts.length == 1 && parts[0].trim().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Masukkan setidaknya satu angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            array = new int[parts.length];
            for (int k = 0; k < parts.length; k++) {
                array[k] = Integer.parseInt(parts[k].trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan hanya angka yang dipisahkan dengan koma!", "Error", JOptionPane.ERROR_MESSAGE);
            array = null; // Reset array jika error
            return;
        }

        // Inisialisasi state untuk Selection Sort
        i = 0; // Pass luar dimulai dari indeks 0
        if (array.length > 0) { // Hanya jika array tidak kosong
            minIndex = i; // Awalnya, elemen pertama dianggap minimum untuk pass pertama
            j = i + 1;    // Mulai memindai dari elemen setelah i
        } else { // Jika array kosong, set j agar tidak error
            minIndex = -1; // atau nilai invalid lainnya
            j = 0;
        }


        stepCount = 1;
        sorting = true;
        stepButton.setEnabled(true);
        stepArea.setText("Array awal: " + arrayToString(array) + "\n");
        if (array.length > 0) {
             stepArea.append("Memulai Pass ke-1. Kandidat minimum awal: array["+minIndex+"] ("+array[minIndex]+").\n\n");
        }


        panelArray.removeAll();
        labelArray = new JLabel[array.length];
        for (int k = 0; k < array.length; k++) {
            labelArray[k] = new JLabel(String.valueOf(array[k]));
            labelArray[k].setFont(new Font("Arial", Font.BOLD, 20)); // Ukuran font disesuaikan
            labelArray[k].setOpaque(true); // Penting agar background terlihat
            labelArray[k].setBackground(Color.WHITE);
            labelArray[k].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            labelArray[k].setPreferredSize(new Dimension(55, 55)); // Ukuran label
            labelArray[k].setHorizontalAlignment(SwingConstants.CENTER);
            panelArray.add(labelArray[k]);
        }

        if (array.length > 0) {
            visualizeStep(-1, minIndex, false); // Visualisasi awal, highlight minIndex pertama
        }

        panelArray.revalidate();
        panelArray.repaint();
    }

    private void reset() {
        inputField.setText("");
        panelArray.removeAll();
        panelArray.revalidate();
        panelArray.repaint();
        stepArea.setText("");
        stepButton.setEnabled(false);
        sorting = false;
        i = 0; // Reset i ke nilai awal yang benar
        // j dan minIndex akan di-set ulang saat setArrayFromInput dipanggil lagi
        stepCount = 1;
        array = null;
        labelArray = null;
    }

    private String arrayToString(int[] arr) {
        if (arr == null) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int k = 0; k < arr.length; k++) {
            sb.append(arr[k]);
            if (k < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
