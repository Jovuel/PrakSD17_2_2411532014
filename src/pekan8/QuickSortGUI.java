package pekan8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Stack; // <-- IMPORT DITAMBAHKAN

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

public class QuickSortGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private int[] array;
	private JLabel[] labelArray;
	private JButton stepButton, resetButton, setButton;
	private JTextField inputField;
	private JPanel panelArray;
	private JTextArea stepArea;

	private int i = 0, j = 0;
	private boolean sorting = false;
	private int stepCount = 1;

	private Stack<int[]> stack;
	private int low, high, pivot;
	private boolean partitioning = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuickSortGUI frame = new QuickSortGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public QuickSortGUI() {
		setTitle("Quick Sort Langkah per Langkah");
		setSize(850, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		stack = new Stack<>();

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
		
		if (stack.isEmpty() && !partitioning) {
			if(sorting){ 
				sorting = false;
				stepButton.setEnabled(false);
				resetHighlights(true); // Warnai semua jadi abu-abu
				stepArea.append("\nQuick Sort selesai.\n");
				JOptionPane.showMessageDialog(this, "Quick Sort selesai!");
			}
			return;
		}

		resetHighlights(false);

		if (!partitioning) {
			int[] range = stack.pop();
			low = range[0];
			high = range[1];

			if(low >= high) {
				performStep(); 
				return;
			}
			
			pivot = array[high];
			i = low - 1;
			j = low;
			partitioning = true;
			stepArea.append("--- Mulai Partisi ---\n");
			logStep("Range: [" + low + ".." + high + "], Pivot: " + pivot + " (di indeks " + high +")");
			highlightPivot(high);
			return;
		}
		
		if (j < high) {
			highlightCompare(j, high);
			if (array[j] <= pivot) {
				i++;
				logStep("Cek " + array[j] + " <= " + pivot + ". Benar. Pindahkan ke bagian kiri.");
				if (i != j) {
					logStep(" -> Tukar arr[" + i + "]=" + array[i] + " dengan arr[" + j + "]=" + array[j]);
					swap(i, j);
					updateLabels();
				}
			} else {
				logStep("Cek " + array[j] + " <= " + pivot + ". Salah. Lewatkan.");
			}
			j++;
			return;
		}
		
		int pivotFinalIndex = i + 1;
		logStep("Partisi selesai. Pindahkan pivot " + pivot + " ke posisi finalnya di indeks " + pivotFinalIndex);
		swap(pivotFinalIndex, high);
		updateLabels();

		partitioning = false; 
		
		labelArray[pivotFinalIndex].setBackground(Color.LIGHT_GRAY);

		stepArea.append("--- Selesai Partisi ---\n\n");
		
		stack.push(new int[] {low, pivotFinalIndex - 1});
		stack.push(new int[] {pivotFinalIndex + 1, high});
	}

	private void highlightPivot(int index) {
		labelArray[index].setBackground(Color.ORANGE);
	}

	private void highlightCompare(int jIndex, int pivotIndex) {
		labelArray[jIndex].setBackground(Color.CYAN);
		if(labelArray[pivotIndex].getBackground() != Color.LIGHT_GRAY) {
			labelArray[pivotIndex].setBackground(Color.ORANGE);
		}
	}
	
	private void setArrayFromInput() {
		String text = inputField.getText().trim();
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String[] parts = text.split(",");
		if (parts.length < 2) {
			JOptionPane.showMessageDialog(this, "Masukkan setidaknya dua angka!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		array = new int[parts.length];

		try {
			for (int k = 0; k < parts.length; k++) {
				array[k] = Integer.parseInt(parts[k].trim());
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Masukkan hanya angka yang dipisahkan dengan koma!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		labelArray = new JLabel[array.length];
		panelArray.removeAll();

		for (int k = 0; k < array.length; k++) {
			labelArray[k] = new JLabel(String.valueOf(array[k]));
			labelArray[k].setFont(new Font("Arial", Font.BOLD, 24));
			labelArray[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			labelArray[k].setPreferredSize(new Dimension(50, 50));
			labelArray[k].setHorizontalAlignment(SwingConstants.CENTER);
			labelArray[k].setOpaque(true);
			labelArray[k].setBackground(Color.WHITE);
			panelArray.add(labelArray[k]);
		}

		stack.clear();
		stack.push(new int[] {0, array.length - 1});
		sorting = true;
		partitioning = false;
		stepCount = 1;
		stepArea.setText("Array Awal: " + java.util.Arrays.toString(array) + "\n\n");
		stepButton.setEnabled(true);

		panelArray.revalidate();
		panelArray.repaint();
	}

	private void updateLabels() {
		for (int k = 0; k < array.length; k++) {
			labelArray[k].setText(String.valueOf(array[k]));
		}
	}

	private void resetHighlights(boolean markAsSorted) {
		if (labelArray == null) return;
		for (JLabel label : labelArray) {
			if (markAsSorted || label.getBackground() == Color.LIGHT_GRAY) {
				label.setBackground(Color.LIGHT_GRAY);
			} else {
				label.setBackground(Color.WHITE);
			}
		}
	}

	private void swap(int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	private void reset() {
		inputField.setText("");
		panelArray.removeAll();
		panelArray.revalidate();
		panelArray.repaint();
		stepArea.setText("");
		stepButton.setEnabled(false);
		if(stack != null) stack.clear();
		sorting = false;
		partitioning = false;
		stepCount = 1;
		array = null;
		labelArray = null;
	}
	
	private void logStep(String message) {
        stepArea.append("Langkah " + stepCount + ": " + message + "\n");
        stepArea.setCaretPosition(stepArea.getDocument().getLength());
        stepCount++;
    }
}
