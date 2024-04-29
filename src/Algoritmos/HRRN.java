package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HRRN extends Algorithm {

    public HRRN(JTable table, ArrayList<ProcessBlock> processList) {
        super(table, processList);
    }

    @Override
    public void execute() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int currentTime = 0;

        while (existenRafagasPorEjecutar()) {
            actualizarListaProcesos(currentTime);
            if (!localProcessList.isEmpty()) {
                // Encontrar el proceso con el mayor ratio de respuesta
                ProcessBlock currentProcess = null;
                double maxResponseRatio = Double.MIN_VALUE;
                for (ProcessBlock process : localProcessList) {
                    if (process.getResponseRatio() > maxResponseRatio && !process.isRunningAt(currentTime)) {
                        currentProcess = process;
                        maxResponseRatio = process.getResponseRatio();
                    }
                }

                if (currentProcess != null) {
                    int processRow = getRow(currentProcess.getName());

                    // Ejecutar el proceso actual hasta que termine
                    while (currentProcess.getBurstsToExecute() > 0) {
                        modelo.setValueAt("X", processRow, currentTime + 1);
                        currentProcess.executeBurst();
                        currentTime++;

                        for (ProcessBlock process : localProcessList) {
                            if (process != currentProcess && !process.isRunningAt(currentTime)) {
                                process.incrementWaitTime();
                            }
                        }
                    }

                    // Remover el proceso una vez terminado
                    localProcessList.remove(currentProcess);
                }
            }
        }
    }
}
