package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RR extends Algorithm {

    private int quantum; // Valor del quantum

    public RR(JTable table, ArrayList<ProcessBlock> processList, int quantum) {
        super(table, processList);
        this.quantum = quantum;
    }

    @Override
    public void execute() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int currentTime = 0; // Momento de llegada de procesos
        Queue<ProcessBlock> readyQueue = new LinkedBlockingQueue<>(); // Cola de procesos listos

        // Agregar procesos a la cola de listos
        for (ProcessBlock process : processList) {
            readyQueue.add(process);
        }

        while (!readyQueue.isEmpty()) {
            ProcessBlock currentProcess = readyQueue.poll();
            int processRow = getRow(currentProcess.getName());

            // Ejecutar el proceso actual por un quantum
            int quantumToExecute = Math.min(currentProcess.getBurstsToExecute(), quantum);
            for (int i = 0; i < quantumToExecute; i++) {
                modelo.setValueAt("X", processRow, currentTime + 1);
                currentProcess.executeBurst();
                currentTime++;
            }

            // Si el proceso no ha terminado, regresarlo a la cola
            if (currentProcess.getBurstsToExecute() > 0) {
                readyQueue.add(currentProcess);
            }
        }
    }
}
