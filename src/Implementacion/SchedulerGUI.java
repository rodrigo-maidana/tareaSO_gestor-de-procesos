package Implementacion;

import Algoritmos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SchedulerGUI extends JFrame {
    private JButton loadButton, executeButton;
    private JTextArea resultArea;
    private List<ProcessBlock> processList;
    private JTextField quantumField;
    private JCheckBox cbFCFS;
    private JCheckBox cbSJFNonPreemptive;
    private JCheckBox cbSJFPreemptive;
    private JCheckBox cbPriority;
    private JCheckBox cbRR;
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
        JLabel quantumLabel = new JLabel("Quantum:");
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
                    FCFS fcfs = new FCFS(processList);
                    fcfs.execute();
                    showExecutionGraph(fcfs.getProcessList());
                }
                if (cbSJFNonPreemptive.isSelected()) {
                    SJFNonPreemptive sjfNonPreemptive = new SJFNonPreemptive(processList);
                    sjfNonPreemptive.execute();
                    showExecutionGraph(sjfNonPreemptive.getProcessList());
                }
                if (cbSJFPreemptive.isSelected()) {
                    SJFPreemptive sjfPreemptive = new SJFPreemptive(processList);
                    sjfPreemptive.execute();
                    showExecutionGraph(sjfPreemptive.getProcessList());
                }
                if (cbPriority.isSelected()) {
                    Priority priority = new Priority(processList);
                    priority.execute();
                    showExecutionGraph(priority.getProcessList());
                }
                if (cbRR.isSelected()) {
                    int quantum;
                    try {
                        quantum = Integer.parseInt(quantumField.getText());
                    } catch (NumberFormatException ex) {
                        quantum = 4; // Valor predeterminado si la entrada no es válida
                    }
                    RR rr = new RR(processList, quantum);
                    rr.execute();
                    showExecutionGraph(rr.getProcessList());
                }
                if (cbHRRN.isSelected()) {
                    HRRN hrrn = new HRRN(processList);
                    hrrn.execute();
                    showExecutionGraph(hrrn.getProcessList());
                }
            }
        });
    }

    // Método que puede ser sobreescrito o utilizado en subclases para preparar la
    // visualización
    // Método para mostrar la visualización de un algoritmo
    public void showExecutionGraph(List<ProcessBlock> processList) {
        // Calcula la cantidad de tiempo máximo en la línea de tiempo
        int maxTime = processList.stream().mapToInt(ProcessBlock::getEndTime).max().orElse(0);

        // Preparar los datos para la tabla
        String[] columnNames = new String[maxTime + 2]; // +2 para incluir la columna de proceso y una columna extra al
                                                        // final si el endTime es el último segundo
        columnNames[0] = "Process";
        for (int i = 1; i <= maxTime + 1; i++) {
            columnNames[i] = String.valueOf(i - 1); // Puedes ajustar esto si quieres que el tiempo comience en 1 en
                                                    // lugar de 0
        }
        Object[][] data = new Object[processList.size()][maxTime + 2];

        Collections.sort(processList, Comparator.comparing(ProcessBlock::getName)); // Ordena la lista de procesos por
                                                                                    // nombre

        for (int i = 0; i < processList.size(); i++) {
            ProcessBlock block = processList.get(i);
            data[i][0] = block.getName();
            if (block.getStartTime() != -1 && block.getEndTime() != -1) {
                for (int j = block.getStartTime(); j < block.getEndTime(); j++) {
                    data[i][j + 1] = "X"; // O algún otro marcador para indicar ejecución
                }
            }
        }

        // Crear la tabla y añadirla a una ventana emergente
        JTable table = new JTable(data, columnNames) {
            @Override // Sobreescribe este método para deshabilitar la edición de celdas
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setCellSelectionEnabled(false); // Opcional: deshabilita la selección de celdas
        table.getTableHeader().setReorderingAllowed(false); // Evita que las columnas se reordenen
        JScrollPane scrollPane = new JScrollPane(table); // Agrega la tabla a un JScrollPane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Habilita la barra de
                                                                                             // desplazamiento
                                                                                             // horizontal si es
                                                                                             // necesario

        // Configurar diálogo para mostrar la tabla
        JDialog executionDialog = new JDialog(this, "Execution Timeline", true); // true para modal
        executionDialog.setLayout(new BorderLayout());
        executionDialog.add(scrollPane, BorderLayout.CENTER); // Agrega el JScrollPane al centro del diálogo

        // Configurar el tamaño y la ubicación de la ventana emergente
        executionDialog.setSize(new Dimension(1280, 720)); // Tamaño predeterminado, ajusta según sea necesario
        executionDialog.setLocationRelativeTo(this); // Centra la ventana emergente en relación con la ventana principal

        // Mostrar la ventana emergente
        executionDialog.setVisible(true);
    }

}
