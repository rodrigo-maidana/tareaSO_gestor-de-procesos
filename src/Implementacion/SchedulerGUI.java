package Implementacion;

import Algoritmos.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SchedulerGUI extends JFrame {
    private JButton loadButton, executeButton;
    private JTextArea resultArea;

    private ArrayList<ProcessBlock> processList;

    private JCheckBox cbFCFS;
    private JCheckBox cbSJFNonPreemptive;
    private JCheckBox cbSJFPreemptive;
    private JCheckBox cbPriority;
    private JCheckBox cbRR;
    private JTextField quantumField;
    private JCheckBox cbHRRN;

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

        // Crear checkboxes para los algoritmos
        cbFCFS = new JCheckBox("FCFS");
        cbSJFNonPreemptive = new JCheckBox("SJF Non-Preemptive");
        cbSJFPreemptive = new JCheckBox("SJF Preemptive");
        cbPriority = new JCheckBox("Priority");
        cbRR = new JCheckBox("RR");
        cbHRRN = new JCheckBox("HRRN");

        // Crear campo de texto para el quantum del algoritmo RR
        // JLabel quantumLabel = new JLabel("Quantum:");
        quantumField = new JTextField("4", 5); // 5 es el tamaño del campo
        quantumField.setEnabled(false); // Deshabilitar hasta que RR esté seleccionado

        // Añadir listener al checkbox de RR para habilitar/deshabilitar el campo de
        // texto
        cbRR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quantumField.setEnabled(cbRR.isSelected());
            }
        });

        // Configurar área de texto para resultados
        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
    }

    private void layoutComponents() {
        // Panel para la parte superior con botones y checkboxes
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(loadButton);
        topPanel.add(executeButton);

        // Panel para los algoritmos con checkboxes
        JPanel algorithmsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        algorithmsPanel.add(cbFCFS);
        algorithmsPanel.add(cbSJFNonPreemptive);
        algorithmsPanel.add(cbSJFPreemptive);
        algorithmsPanel.add(cbPriority);
        algorithmsPanel.add(cbHRRN);

        // Panel específico para RR y su Quantum
        JPanel rrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rrPanel.add(cbRR);
        rrPanel.add(new JLabel("Quantum:"));
        rrPanel.add(quantumField);

        // Añadir el panel de RR con el Quantum al panel de algoritmos
        algorithmsPanel.add(rrPanel);

        // Área de desplazamiento para la salida de texto
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Configurar el layout del contenedor principal de JFrame
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH); // Añadir el panel de la parte superior en la parte norte
        this.add(algorithmsPanel, BorderLayout.CENTER); // Añadir el panel de algoritmos en el centro
        this.add(scrollPane, BorderLayout.SOUTH); // Añadir el área de desplazamiento en el sur
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
            public void actionPerformed(ActionEvent e) {

                if (cbFCFS.isSelected()) {
                    ResultsSheet frame = new ResultsSheet(processList, "FCFS");
                    frame.setVisible(true);
                    FCFS fcfs = new FCFS(frame.getTabla(), processList);
                    fcfs.execute();
                }

                if (cbSJFNonPreemptive.isSelected()) {
                    ResultsSheet frame = new ResultsSheet(processList, "SJF Non-Preemptive");
                    frame.setVisible(true);
                    SJFNonPreemptive sjfNonPreemptive = new SJFNonPreemptive(frame.getTabla(),
                            processList);
                    sjfNonPreemptive.execute();
                }

                if (cbSJFPreemptive.isSelected()) {
                    ResultsSheet frame = new ResultsSheet(processList, "SJF Preemptive");
                    frame.setVisible(true);
                    SJFPreemptive sjfPreemptive = new SJFPreemptive(frame.getTabla(), processList);
                    sjfPreemptive.execute();
                }

                if (cbPriority.isSelected()) {
                    ResultsSheet frame = new ResultsSheet(processList, "Priority");
                    frame.setVisible(true);
                    Priority priority = new Priority(frame.getTabla(), processList);
                    priority.execute();
                }

                if (cbRR.isSelected()) {
                    int quantum;
                    try {
                        quantum = Integer.parseInt(quantumField.getText());
                    } catch (NumberFormatException ex) {
                        quantum = 4; // Valor predeterminado si la entrada no es válida
                    }
                    ResultsSheet frame = new ResultsSheet(processList, "Priority");
                    frame.setVisible(true);
                    RR rr = new RR(frame.getTabla(), processList, quantum);
                    rr.execute();
                }
                /*
                 * if (cbHRRN.isSelected()) {
                 * HRRN hrrn = new HRRN(processList);
                 * hrrn.execute();
                 * }
                 */
            }

        });
    }
}
