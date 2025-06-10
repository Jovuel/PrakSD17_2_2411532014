package pekan8;

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

public class BubbleSortGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private int[] array;
	private JLabel[] labelArray;
	private JButton stepButton, resetButton, setButton;
	private JTextField inputField;
	private JPanel panelArray;
	private JTextArea stepArea;

	// i: menandakan jumlah pass yang sudah selesai (elemen yang sudah di posisi akhir)
	// j: menandakan indeks yang sedang dibandingkan
	private int i = 0, j = 0;
	private boolean sorting = false;
	private int stepCount = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BubbleSortGUI frame = new BubbleSortGUI();
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
	public BubbleSortGUI() {
		setTitle("Bubble Sort Langkah per Langkah");
		setSize(850, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
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
		if (i >= array.length - 1 || !sorting) {
			sorting = false;
			stepButton.setEnabled(false);
			JOptionPane.showMessageDialog(this, "Sorting selesai!");
			for (JLabel label : labelArray) {
				label.setBackground(Color.LIGHT_GRAY);
			}
			return;
		}

		resetHighlights();

		StringBuilder stepLog = new StringBuilder();

		labelArray[j].setBackground(Color.CYAN);
		labelArray[j + 1].setBackground(Color.CYAN);

		stepLog.append("Langkah ").append(stepCount).append(": Membandingkan arr[").append(j).append("] dan arr[").append(j + 1).append("].\n");
		
		if (array[j] > array[j + 1]) {
			int val1 = array[j];
			int val2 = array[j + 1];

			int temp = array[j];
			array[j] = array[j + 1];
			array[j + 1] = temp;
			
			labelArray[j].setBackground(Color.RED);
			labelArray[j + 1].setBackground(Color.RED);
			stepLog.append(" -> Tukar! Karena ").append(val1).append(" > ").append(val2).append(".\n");

		} else {
			stepLog.append(" -> Tidak ada pertukaran.\n");
		}
		
		updateLabels();
		stepLog.append("   Hasil: ").append(arrayToString(array)).append("\n\n");
		
		stepArea.append(stepLog.toString());
		stepArea.setCaretPosition(stepArea.getDocument().getLength());
		j++;

		if (j >= array.length - i - 1) {
			labelArray[array.length - i - 1].setBackground(Color.LIGHT_GRAY);
			j = 0; 
			i++; 
		}
		
		stepCount++;

		if (i >= array.length - 1) {
			sorting = false;
			stepButton.setEnabled(false);
			JOptionPane.showMessageDialog(this, "Sorting selesai!");
			for (JLabel label : labelArray) {
				label.setBackground(Color.LIGHT_GRAY);
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
		if (parts.length == 0 || (parts.length == 1 && parts[0].trim().isEmpty())) {
			JOptionPane.showMessageDialog(this, "Masukkan setidaknya satu angka!", "Error", JOptionPane.ERROR_MESSAGE);
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

		i = 0;
		j = 0;
		stepCount = 1;
		sorting = true;
		stepButton.setEnabled(true);
		stepArea.setText("Array Awal: " + arrayToString(array) + "\n\n"); 
		
		panelArray.removeAll();
		labelArray = new JLabel[array.length];
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
		
		panelArray.revalidate();
		panelArray.repaint();
	}

	private void updateLabels() {
		for (int k = 0; k < array.length; k++) {
			labelArray[k].setText(String.valueOf(array[k]));
		}
	}

	private void resetHighlights() {
		for (int k = 0; k < labelArray.length; k++) {
			if (labelArray[k].getBackground() != Color.LIGHT_GRAY) {
				labelArray[k].setBackground(Color.WHITE);
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
		i = 0;
		j = 0;
		stepCount = 1;
		array = null;
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
