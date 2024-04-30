package Implementacion;

import Algoritmos.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

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
    private JTable table;
    private DefaultTableModel tableModel;

    public SchedulerGUI() {
        super("Process Scheduler GUI");
        initializeComponents();
        layoutComponents();
        addListeners();
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

        // Initialising the table model
        String[] columnNames = { "Process Name", "Arrival Time", "Burst Time", "Priority" }; // Asumiendo que
                                                                                             // ProcessBlock
                                                                                             // tiene estos campos
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
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

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(500, 200)); // Ajusta según necesites
        this.add(tableScrollPane, BorderLayout.AFTER_LAST_LINE); // Asegúrate de agregarlo en la posición correcta del

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

                        // Limpiar la tabla antes de agregar nuevos datos
                        tableModel.setRowCount(0);

                        // Agregar procesos a la tabla
                        for (ProcessBlock process : processList) {
                            Object[] rowData = new Object[] {
                                    process.getName(),
                                    process.getArrivalTime(),
                                    process.getBurstsToExecute(),
                                    process.getPriority()
                            };
                            tableModel.addRow(rowData);
                        }
                    } catch (Exception ex) {
                        resultArea.setText("Failed to load processes: " + ex.getMessage());
                    }
                }
            }
        });

        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (cbFCFS.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "FCFS");
                    frame.setVisible(true);
                    FCFS fcfs = new FCFS(frame.getTabla(), copiedProcessList);
                    fcfs.execute();
                }

                if (cbSJFNonPreemptive.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "SJF Non-Preemptive");
                    frame.setVisible(true);
                    SJFNonPreemptive sjfNonPreemptive = new SJFNonPreemptive(frame.getTabla(), copiedProcessList);
                    sjfNonPreemptive.execute();
                }

                if (cbSJFPreemptive.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "SJF Preemptive");
                    frame.setVisible(true);
                    SJFPreemptive sjfPreemptive = new SJFPreemptive(frame.getTabla(), copiedProcessList);
                    sjfPreemptive.execute();
                }

                if (cbPriority.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "Priority");
                    frame.setVisible(true);
                    Priority priority = new Priority(frame.getTabla(), copiedProcessList);
                    priority.execute();
                }

                if (cbRR.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    int quantum;
                    try {
                        quantum = Integer.parseInt(quantumField.getText());
                    } catch (NumberFormatException ex) {
                        quantum = 4; // Valor predeterminado si la entrada no es válida
                    }
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "RR");
                    frame.setVisible(true);
                    RR rr = new RR(frame.getTabla(), copiedProcessList, quantum);
                    rr.execute();
                }

                if (cbHRRN.isSelected()) {
                    ArrayList<ProcessBlock> copiedProcessList = deepCopyList(processList);
                    ResultsSheet frame = new ResultsSheet(copiedProcessList, "HRRN");
                    frame.setVisible(true);
                    HRRN hrrn = new HRRN(frame.getTabla(), copiedProcessList);
                    hrrn.execute();
                }
            }
        });
    }

    private ArrayList<ProcessBlock> deepCopyList(ArrayList<ProcessBlock> originalList) {
        ArrayList<ProcessBlock> copiedList = new ArrayList<>();
        for (ProcessBlock pb : originalList) {
            copiedList.add(pb.clone());
        }
        return copiedList;
    }

}
