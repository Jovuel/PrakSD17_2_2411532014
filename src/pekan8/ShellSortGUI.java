package pekan8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Arrays;

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

public class ShellSortGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private int[] array;
    private JLabel[] labelArray;
    private JButton stepButton, resetButton, setButton;
    private JTextField inputField;
    private JPanel panelArray;
    private JTextArea stepArea;

    // Variabel state untuk visualisasi langkah per langkah
    private int gap;
    private int i; // Indeks luar (elemen yang akan disisipkan)
    private int j; // Indeks dalam (posisi untuk penyisipan)
    private int temp; // Nilai elemen yang sedang diproses
    private boolean sorting = false;
    private boolean isSwapping = false; // Flag penanda sedang dalam fase geser/sisip
    private int stepCount = 1;

    public ShellSortGUI() {
        setTitle("Shell Sort Langkah per Langkah");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // FIX: Menghapus karakter spasi aneh
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputField = new JTextField(30);
        setButton = new JButton("Set Array");
        inputPanel.add(new JLabel("Masukkan angka (pisahkan dengan koma):"));
        inputPanel.add(inputField);
        inputPanel.add(setButton);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        panelArray = new JPanel();
        panelArray.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JPanel controlPanel = new JPanel();
        stepButton = new JButton("Langkah Selanjutnya");
        resetButton = new JButton("Reset");
        stepButton.setEnabled(false);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Langkah"));
        // FIX: Menghapus karakter spasi aneh
        stepArea = new JTextArea(8, 60);
        stepArea.setEditable(false);
        stepArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(stepArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(panelArray, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(logPanel, BorderLayout.EAST);

        setButton.addActionListener(e -> setArrayFromInput());
        stepButton.addActionListener(e -> performStep());
        resetButton.addActionListener(e -> reset());
    }

    private void performStep() {
        if (!sorting) return;
        
        // FIX: Logika reset highlight dipindahkan agar tidak menghapus highlight langkah sebelumnya terlalu cepat
        resetHighlights();

        // FASE 1: Sedang dalam proses menggeser dan mencari posisi sisip
        if (isSwapping) {
            // Cek apakah masih bisa geser ke kiri
            if (j >= gap && array[j - gap] > temp) {
                // Visualisasi perbandingan
                labelArray[j - gap].setBackground(Color.CYAN); // Elemen pembanding
                labelArray[j].setBackground(Color.YELLOW);     // Posisi saat ini

                // Lakukan pergeseran
                array[j] = array[j - gap];
                logStep("Geser " + array[j - gap] + " ke kanan (posisi " + j + ")");
                updateLabels();
                
                j -= gap; // Mundur sejauh gap untuk perbandingan selanjutnya
            } else {
                // Posisi sudah ditemukan, sisipkan elemen
                array[j] = temp;
                logStep("Sisipkan " + temp + " di posisi " + j);
                labelArray[j].setBackground(Color.RED); // Tandai posisi penyisipan
                updateLabels();
                
                // Siap untuk elemen berikutnya di iterasi i
                i++;
                isSwapping = false; // Keluar dari fase penyisipan
            }
        } 
        // FASE 2: Memulai proses untuk elemen baru (i) atau mengganti gap
        else {
            // Jika iterasi i untuk gap saat ini sudah selesai
            if (i >= array.length) {
                gap /= 2; // Perkecil gap
                if (gap > 0) {
                    logStep("PASS SELESAI. Ganti gap menjadi " + gap);
                    i = gap; // Mulai iterasi i dari gap yang baru
                } else {
                    // Sorting selesai total
                    logStep("SORTING SELESAI!");
                    sorting = false;
                    stepButton.setEnabled(false);
                    for (JLabel label : labelArray) {
                        label.setBackground(Color.LIGHT_GRAY);
                    }
                    JOptionPane.showMessageDialog(this, "Shell Sort selesai!");
                    return;
                }
            }

            // Memulai fase penyisipan untuk elemen di posisi i
            temp = array[i];
            j = i;
            logStep("Ambil elemen ke-" + i + " (nilai: " + temp + ") untuk disisipkan");
            labelArray[i].setBackground(Color.YELLOW); // Tandai elemen yang akan disisipkan
            isSwapping = true;
        }
    }

    private void logStep(String message) {
        stepArea.append("Langkah " + stepCount + ": " + message + "\n");
        stepArea.append("  Array: " + Arrays.toString(array) + "\n\n");
        stepArea.setCaretPosition(stepArea.getDocument().getLength());
        stepCount++;
    }

    private void setArrayFromInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = text.split(",");
        array = new int[parts.length];

        try {
            for (int k = 0; k < parts.length; k++) {
                array[k] = Integer.parseInt(parts[k].trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan hanya angka yang dipisahkan dengan koma!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Reset state
        gap = array.length / 2;
        i = gap;
        j = i;
        stepCount = 1;
        isSwapping = false;

        stepArea.setText("Array Awal: " + Arrays.toString(array) + "\n");
        logStep("Mulai dengan gap = " + gap);

        sorting = true;
        stepButton.setEnabled(true);
        panelArray.removeAll();
        labelArray = new JLabel[array.length];

        for (int k = 0; k < array.length; k++) {
            labelArray[k] = new JLabel(String.valueOf(array[k]));
            labelArray[k].setFont(new Font("Arial", Font.BOLD, 24));
            labelArray[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labelArray[k].setPreferredSize(new Dimension(50, 50));
            labelArray[k].setHorizontalAlignment(SwingConstants.CENTER);
            // FIX: Menghapus karakter spasi aneh
            labelArray[k].setOpaque(true);
            labelArray[k].setBackground(Color.WHITE);
            panelArray.add(labelArray[k]);
        }
        panelArray.revalidate();
        panelArray.repaint();
    }

    private void updateLabels() {
        for (int k = 0; k < array.length; k++) {
            labelArray[k].setText(String.valueOf(array[k]));
        }
    }

    private void resetHighlights() {
        if (labelArray == null) return;
        for (JLabel label : labelArray) {
            label.setBackground(Color.WHITE);
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
        array = null;
        labelArray = null;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShellSortGUI gui = new ShellSortGUI();
            gui.setVisible(true);
        });
    }
}
