package pekan8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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

public class MergeSortGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private int[] array;
	private JLabel[] labelArray;
	private JButton stepButton, resetButton, setButton;
	private JTextField inputField;
	private JPanel panelArray;
	private JTextArea stepArea;

	private int stepCount = 1;
	private Queue<int[]> mergeQueue;
	private boolean isMerging = false;
	private boolean copying = false;

	private int left, mid, right;
	private int i, j, k;
	private int[] temp;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MergeSortGUI frame = new MergeSortGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public MergeSortGUI() {
		setTitle("Merge Sort Langkah per Langkah");
		setSize(850, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		mergeQueue = new LinkedList<>();

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
		resetHighlights();

		if (!isMerging && !mergeQueue.isEmpty()) {
			int[] range = mergeQueue.poll();
			left = range[0];
			mid = range[1];
			right = range[2];

			temp = new int[right - left + 1];
			i = left;
			j = mid + 1;
			k = 0;
			copying = false;
			isMerging = true;

			logStep("Mulai merge range [" + left + ".." + right + "]");
			highlightRange(left, right);
			return;
		}

		if (isMerging && !copying) {
			highlightRange(left, right);

			if (i <= mid && j <= right) {
				highlightCompare(i, j);
				if (array[i] <= array[j]) {
					logStep("Bandingkan " + array[i] + " <= " + array[j] + ". Salin " + array[i] + " ke temp.");
					temp[k++] = array[i++];
				} else {
					logStep("Bandingkan " + array[i] + " > " + array[j] + ". Salin " + array[j] + " ke temp.");
					temp[k++] = array[j++];
				}
				stepArea.append("  -> Temp: " + Arrays.toString(Arrays.copyOf(temp, k)) + "\n\n");
				return;
			}

			if (i <= mid) {
				logStep("Salin sisa dari kiri: " + array[i]);
				temp[k++] = array[i++];
				stepArea.append("  -> Temp: " + Arrays.toString(Arrays.copyOf(temp, k)) + "\n\n");
				return;
			}

			if (j <= right) {
				logStep("Salin sisa dari kanan: " + array[j]);
				temp[k++] = array[j++];
				stepArea.append("  -> Temp: " + Arrays.toString(Arrays.copyOf(temp, k)) + "\n\n");
				return;
			}

			copying = true;
			k = 0;
			logStep("Selesai mengisi temp. Mulai menyalin kembali ke array utama.");
			stepArea.append("  -> Temp Lengkap: " + Arrays.toString(temp) + "\n");
			return;
		}

		if (isMerging && copying) {
			if (k < temp.length) {
				logStep("Salin " + temp[k] + " dari temp ke posisi " + (left + k));
				array[left + k] = temp[k];
				updateLabels();
				highlightCopy(left + k);
				k++;
				return;
			} else {
				isMerging = false;
				copying = false;
				logStep("Selesai merge range [" + left + ".." + right + "]");

				if (mergeQueue.isEmpty() && !isMerging) {
					stepArea.append("\n--- MERGE SORT SELESAI ---\n");
					stepArea.append("Hasil Akhir: " + Arrays.toString(array) + "\n");
					stepButton.setEnabled(false);
					JOptionPane.showMessageDialog(this, "Merge Sort selesai!");
					for (JLabel label : labelArray)
						label.setBackground(Color.LIGHT_GRAY);
				}
			}
		}
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
			for (int i = 0; i < parts.length; i++) {
				array[i] = Integer.parseInt(parts[i].trim());
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Masukkan hanya angka!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		stepCount = 1;
		isMerging = false;
		copying = false;
		if (mergeQueue != null) {
			mergeQueue.clear();
		}
		
		panelArray.removeAll();
		stepArea.setText("");

		labelArray = new JLabel[array.length];
		for (int i = 0; i < array.length; i++) {
			labelArray[i] = new JLabel(String.valueOf(array[i]));
			labelArray[i].setFont(new Font("Arial", Font.BOLD, 24));
			labelArray[i].setOpaque(true);
			labelArray[i].setBackground(Color.WHITE);
			labelArray[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			labelArray[i].setPreferredSize(new Dimension(50, 50));
			labelArray[i].setHorizontalAlignment(SwingConstants.CENTER);
			panelArray.add(labelArray[i]);
		}

		generateMergeSteps(0, array.length - 1);

		stepButton.setEnabled(true);
		stepArea.setText("Array Awal: " + Arrays.toString(array) + "\n\n");
		
		panelArray.revalidate();
		panelArray.repaint();
	}

	private void generateMergeSteps(int l, int r) {
		if (l < r) {
			int m = l + (r - l) / 2;
			generateMergeSteps(l, m);
			generateMergeSteps(m + 1, r);
			mergeQueue.add(new int[] { l, m, r });
		}
	}

	private void updateLabels() {
		for (int i = 0; i < array.length; i++) {
			labelArray[i].setText(String.valueOf(array[i]));
		}
	}

	private void resetHighlights() {
		if (labelArray == null) return;
		for (JLabel label : labelArray) {
			label.setBackground(Color.WHITE);
		}
	}

	private void highlightRange(int l, int r) {
		for (int i = l; i <= r; i++) {
			labelArray[i].setBackground(Color.ORANGE);
		}
	}

	private void highlightCompare(int i, int j) {
		labelArray[i].setBackground(Color.CYAN);
		labelArray[j].setBackground(Color.CYAN);
	}

	private void highlightCopy(int i) {
		labelArray[i].setBackground(Color.GREEN);
	}

	private void reset() {
		if (inputField != null) inputField.setText("");
		if (panelArray != null) {
			panelArray.removeAll();
			panelArray.revalidate();
			panelArray.repaint();
		}
		if (stepArea != null) stepArea.setText("");
		if (stepButton != null) stepButton.setEnabled(false);
		if (mergeQueue != null) mergeQueue.clear();
		isMerging = false;
		copying = false;
		stepCount = 1;
		array = null;
		labelArray = null;
	}

	private void logStep(String message) {
		stepArea.append("Langkah " + stepCount + ": " + message + "\n");
		if (array != null) {
			stepArea.append("  Array: " + Arrays.toString(array) + "\n\n");
		}
		stepArea.setCaretPosition(stepArea.getDocument().getLength());
		stepCount++;
	}
}
