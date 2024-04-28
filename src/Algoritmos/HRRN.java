package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HRRN extends Algorithm {

    public HRRN(JTable table, ArrayList<ProcessBlock> processList) {
        super(table, processList);
    }

    @Override
    public void execute() {/*
                            * DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                            * int currentTime = 0; // Momento de llegada de procesos
                            * PriorityQueue<ProcessBlock> readyQueue = new PriorityQueue<>(new
                            * HRRNComparator()); // Cola de procesos listos con comparador HRRN
                            * 
                            * // Agregar procesos a la cola de listos
                            * for (ProcessBlock process : processList) {
                            * process.setResponseRatio(0); // Inicializar relación de respuesta
                            * readyQueue.add(process);
                            * }
                            * 
                            * while (!readyQueue.isEmpty()) {
                            * ProcessBlock currentProcess = readyQueue.poll();
                            * int processRow = getRow(currentProcess.getName());
                            * 
                            * // Calcular nuevo tiempo de espera
                            * int waitingTime = currentTime - currentProcess.getArrivalTime();
                            * 
                            * // Calcular nuevo ratio de respuesta
                            * currentProcess.setResponseRatio((waitingTime +
                            * currentProcess.getBurstsToExecute()) / currentProcess.getBurstsToExecute());
                            * 
                            * // Ejecutar el proceso actual
                            * modelo.setValueAt("X", processRow, currentTime + 1);
                            * currentProcess.executeBurst();
                            * currentTime++;
                            * 
                            * // Actualizar ratio de respuesta en la cola
                            * for (ProcessBlock process : readyQueue) {
                            * process.setResponseRatio((process.getWaitingTime() +
                            * process.getBurstsToExecute()) / process.getBurstsToExecute());
                            * }
                            * 
                            * // Si el proceso no ha terminado, regresarlo a la cola
                            * if (currentProcess.getBurstsToExecute() > 0) {
                            * readyQueue.add(currentProcess);
                            * }
                            * }
                            * }
                            * 
                            * private class HRRNComparator implements Comparator<ProcessBlock> {
                            * 
                            * @Override
                            * public int compare(ProcessBlock p1, ProcessBlock p2) {
                            * return Double.compare(p2.getResponseRatio(), p1.getResponseRatio()); //
                            * Ordenar descendentemente por ratio de respuesta
                            * }
                            */
    }
}
