package Implementacion;

import Algoritmos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SchedulerGUI extends JFrame {
    private JButton loadButton, executeButton;
    private JComboBox<String> algorithmComboBox;
    private JTextArea resultArea;
    private List<ProcessBlock> processList;
    private Algorithm currentAlgorithm;

    public SchedulerGUI() {
        super("Process Scheduler GUI");
        initializeComponents();
        layoutComponents();
        addListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initializeComponents() {
        loadButton = new JButton("Load Processes");
        executeButton = new JButton("Execute");
        executeButton.setEnabled(false); // Disabled until processes are loaded

        String[] algorithms = {"FCFS", "SJF Non-Preemptive", "SJF Preemptive", "Priority", "RR", "HRRN"};
        algorithmComboBox = new JComboBox<>(algorithms);

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
    }

    private void layoutComponents() {
        JPanel topPanel = new JPanel();
        topPanel.add(loadButton);
        topPanel.add(algorithmComboBox);
        topPanel.add(executeButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void addListeners() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(SchedulerGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        processList = CSVReader.leerArchivo(fileChooser.getSelectedFile().getAbsolutePath());
                        executeButton.setEnabled(true);
                        resultArea.setText("Processes loaded successfully!\n");
                    } catch (Exception ex) {
                        resultArea.setText("Failed to load processes: " + ex.getMessage());
                    }
                }
            }
        });

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                switch (selectedAlgorithm) {
                    case "FCFS":
                        currentAlgorithm = new FCFS(processList);
                        break;
                    case "SJF Non-Preemptive":
                        currentAlgorithm = new SJFNonPreemptive(processList);
                        break;
                    case "SJF Preemptive":
                        currentAlgorithm = new SJFPreemptive(processList);
                        break;
                    case "Priority":
                        currentAlgorithm = new Priority(processList);
                        break;
                    case "RR":
                        currentAlgorithm = new RR(processList, 4); // Quantum assumed as 4
                        break;
                    case "HRRN":
                        currentAlgorithm = new HRRN(processList);
                        break;
                }
                currentAlgorithm.execute();
                resultArea.append("Results of " + selectedAlgorithm + ":\n");
                currentAlgorithm.printResults(resultArea);
            }
        });        
    }
}
